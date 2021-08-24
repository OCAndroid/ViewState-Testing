package org.ocandroid.viewstatetesting.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import org.ocandroid.viewstatetesting.model.data.FoodType
import org.ocandroid.viewstatetesting.model.data.Item
import org.ocandroid.viewstatetesting.model.repository.ItemRepo
import org.ocandroid.viewstatetesting.model.repository.ItemRepository
import org.ocandroid.viewstatetesting.model.repository.data.ItemModel

class ListViewModel(
    private val itemRepository: ItemRepo
) : ViewModel() {

    private val mutableViewState: MutableLiveData<ViewState> =
        MutableLiveData(ViewState.Empty)
    val viewState: LiveData<ViewState> = mutableViewState

    fun loadItems(count: String) {
        val num = count.toIntOrNull()
        if (num == null) {
            mutableViewState.postValue(ViewState.Error)
            return
        }
        itemRepository.loadItems(num)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { mutableViewState.postValue(ViewState.Loading) }
            .subscribe(
                { items ->
                    if (items.isEmpty()) {
                        mutableViewState.postValue(ViewState.Empty)
                        return@subscribe
                    }
                    mutableViewState.postValue(ViewState.Success(
                        items.map { it.toItemModel() }
                    ))
                },
                {
                    mutableViewState.postValue(ViewState.Error)
                }
            )
    }

    private fun Item.toItemModel() = ItemModel(
        id = id,
        name = name,
        isNew = daysOld < 10,
        price = "$%.2f".format(price),
        isFastFood = when (foodType) {
            FoodType.CHEESEBURGER -> true
            FoodType.FRENCH_FRIES -> true
            FoodType.FRENCH_TOAST -> false
            FoodType.PIZZA -> true
            FoodType.STEAK -> false
        }
    )

    sealed class ViewState {
        object Empty: ViewState()
        object Error: ViewState()
        object Loading: ViewState()
        data class Success(
            val items: List<ItemModel>
        ): ViewState()
    }
}