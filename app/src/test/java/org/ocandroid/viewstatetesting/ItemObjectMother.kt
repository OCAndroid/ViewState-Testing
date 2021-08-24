package org.ocandroid.viewstatetesting

import org.ocandroid.viewstatetesting.model.data.FoodType
import org.ocandroid.viewstatetesting.model.data.Item
import org.ocandroid.viewstatetesting.model.repository.data.ItemModel

object ItemObjectMother {

    val successfulItemList = listOf(
        Item(
            id = 1,
            name = "Steak",
            price = 10.2334f,
            daysOld = 20,
            foodType = FoodType.STEAK
        ),
        Item(
            id = 2,
            name = "Pizza Pie",
            price = 3.987f,
            daysOld = 10,
            foodType = FoodType.PIZZA
        ),
        Item(
            id = 3,
            name = "Burger",
            price = 1f,
            daysOld = 1,
            foodType = FoodType.CHEESEBURGER
        ),
        Item(
            id = 4,
            name = "French Toast",
            price = 4.54f,
            daysOld = 4,
            foodType = FoodType.FRENCH_TOAST
        ),
        Item(
            id = 5,
            name = "Fries",
            price = 0.564f,
            daysOld = 0,
            foodType = FoodType.FRENCH_FRIES
        )
    )

    val successfulItemModelList = listOf(
        ItemModel(
            id = 1,
            name = "Steak",
            price = "$10.23",
            isNew = false,
            isFastFood = false
        ),
        ItemModel(
            id = 2,
            name = "Pizza Pie",
            price = "$3.99",
            isNew = false,
            isFastFood = true
        ),
        ItemModel(
            id = 3,
            name = "Burger",
            price = "$1.00",
            isNew = true,
            isFastFood = true
        ),
        ItemModel(
            id = 4,
            name = "French Toast",
            price = "$4.54",
            isNew = true,
            isFastFood = false
        ),
        ItemModel(
            id = 5,
            name = "Fries",
            price = "$0.56",
            isNew = true,
            isFastFood = true
        )
    )
}