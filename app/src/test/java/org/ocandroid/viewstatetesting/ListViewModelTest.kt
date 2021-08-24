package org.ocandroid.viewstatetesting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.ocandroid.viewstatetesting.model.data.Item
import org.ocandroid.viewstatetesting.model.repository.ItemRepo
import org.ocandroid.viewstatetesting.presentation.ListViewModel
import java.lang.RuntimeException

class ListViewModelTest {

    private val itemRepository = object: ItemRepo {
        override fun loadItems(count: Int): Single<List<Item>> =
            expectedException?.let {
                Single.error(expectedException)
            } ?: Single.just(expectedResponse)
    }

    private val viewModelUnderTest = ListViewModel(itemRepository)
    private val expectedResponse: MutableList<Item> = mutableListOf()
    private val observedViewStates: MutableList<ListViewModel.ViewState> = mutableListOf()
    private var expectedException: Throwable? = null

    // JvmField needed due to difference in how Java/Kotlin handle public
    @Rule @JvmField val instantExecutor = InstantTaskExecutorRule()

    @Before
    fun setup() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        viewModelUnderTest.viewState.observeForever {
            observedViewStates.add(it)
        }

        observedViewStates.clear()
        expectedResponse.clear()
        expectedException = null
    }

    @Test
    fun `Test Error Response`() {
        expectedException = RuntimeException()

        viewModelUnderTest.loadItems("0")
        assertEquals(
            listOf(ListViewModel.ViewState.Loading, ListViewModel.ViewState.Error),
            observedViewStates
        )
    }

    @Test
    fun `Test Error Due to Invalid Count`() {
        viewModelUnderTest.loadItems("a")
        assertEquals(
            listOf(ListViewModel.ViewState.Error),
            observedViewStates
        )
    }

    @Test
    fun `Test Empty List`() {
        viewModelUnderTest.loadItems("0")
        assertEquals(
            listOf(ListViewModel.ViewState.Loading, ListViewModel.ViewState.Empty),
            observedViewStates
        )
    }

    @Test
    fun `Test Success`() {
        expectedResponse.addAll(ItemObjectMother.successfulItemList)

        viewModelUnderTest.loadItems("5")
        assertEquals(
            listOf(
                ListViewModel.ViewState.Loading,
                ListViewModel.ViewState.Success(ItemObjectMother.successfulItemModelList)
            ),
            observedViewStates
        )
    }
}