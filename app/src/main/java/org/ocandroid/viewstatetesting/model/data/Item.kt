package org.ocandroid.viewstatetesting.model.data

data class Item(
    val id: Int,
    val name: String,
    val price: Float,
    val daysOld: Int,
    val foodType: FoodType
)

enum class FoodType {
    CHEESEBURGER,
    FRENCH_FRIES,
    FRENCH_TOAST,
    PIZZA,
    STEAK
}