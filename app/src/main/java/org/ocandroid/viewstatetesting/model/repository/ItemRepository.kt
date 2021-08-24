package org.ocandroid.viewstatetesting.model.repository

import io.reactivex.rxjava3.core.Single
import org.ocandroid.viewstatetesting.model.data.FoodType
import org.ocandroid.viewstatetesting.model.data.Item
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class ItemRepository: ItemRepo {

    override fun loadItems(count: Int): Single<List<Item>> {
        val items: MutableList<Item> = ArrayList()
        for (position in 1..count) {
            items.add(generateItem(position))
        }
        return Single.just<List<Item>>(items)
            .delay(5, TimeUnit.SECONDS)
    }


    private fun generateItem(id: Int) = Item(
        id = id,
        name = "Item $id",
        price = Random.nextFloat() * 10,
        daysOld = Random.nextInt(20),
        foodType = FoodType.values().random()
    )
}

interface ItemRepo {
    fun loadItems(count: Int): Single<List<Item>>
}