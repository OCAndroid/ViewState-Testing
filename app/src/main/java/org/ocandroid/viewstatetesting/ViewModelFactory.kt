package org.ocandroid.viewstatetesting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ocandroid.viewstatetesting.model.repository.ItemRepository
import org.ocandroid.viewstatetesting.presentation.ListViewModel

class ViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when(modelClass) {
            ListViewModel::class.java -> ListViewModel(ItemRepository()) as T
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
}