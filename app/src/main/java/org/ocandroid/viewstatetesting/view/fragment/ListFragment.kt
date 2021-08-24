package org.ocandroid.viewstatetesting.view.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import org.ocandroid.viewstatetesting.R
import org.ocandroid.viewstatetesting.ViewModelFactory
import org.ocandroid.viewstatetesting.databinding.FragmentListBinding
import org.ocandroid.viewstatetesting.presentation.ListViewModel
import org.ocandroid.viewstatetesting.view.adapter.ItemAdapter

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var binding: FragmentListBinding
    private val adapter = ItemAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, ViewModelFactory()).get(ListViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            itemList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            itemList.adapter = adapter
            submitButton.setOnClickListener {
                viewModel.loadItems(numberField.text.toString())

                //hide keyboard
                val inputMethodManager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }

            viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
                when (viewState) {
                    is ListViewModel.ViewState.Loading -> {
                        loadingBar.visibility = View.VISIBLE
                    }
                    is ListViewModel.ViewState.Empty -> {
                        loadingBar.visibility = View.GONE
                        message.text = getString(R.string.empty_state_message)
                    }
                    is ListViewModel.ViewState.Error -> {
                        loadingBar.visibility = View.GONE

                        message.text = getString(R.string.error_state_message)
                    }
                    is ListViewModel.ViewState.Success -> {
                        itemList.visibility = View.VISIBLE
                        emptyContainer.visibility = View.GONE
                        loadingBar.visibility = View.GONE

                        adapter.submitList(viewState.items)
                    }
                }
            })
        }
    }
}