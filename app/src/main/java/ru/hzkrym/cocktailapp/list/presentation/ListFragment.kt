package ru.hzkrym.cocktailapp.list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hzkrym.cocktailapp.R
import ru.hzkrym.cocktailapp.databinding.ListFragmentBinding
import ru.hzkrym.cocktailapp.list.data.CocktailRepository
import ru.hzkrym.cocktailapp.receipt.presentation.ReceiptFragment

class ListFragment : Fragment() {

    var binding: ListFragmentBinding? = null

    private val adapter = CocktailAdapter()
    private val repository = CocktailRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentBinding.inflate(inflater, container, false)
        binding?.let {
            it.cocktailList.adapter = adapter
            it.searchField.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchCocktail(v.text.toString())
                }
                true
            }
        }
        return binding?.root
    }

    private fun searchCocktail(searchString: String) {
        showProgress(true)
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val searchResult = repository.searchCocktail(searchString)
                withContext(Dispatchers.Main) {
                    if (searchResult != null)
                        adapter.updateList(searchResult.map {
                            CocktailViewModel(
                                it.title
                            ) {
                                showReceiptFragment(it.id)
                            }
                        })
                    showProgress(false)
                }
            }
        }
    }

    private fun showReceiptFragment(id: String) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, ReceiptFragment.newInstance(id))
            .addToBackStack(ReceiptFragment::class.java.canonicalName)
            .commit()
    }

    private fun showProgress(isShow: Boolean) {
        if (binding != null) {
            binding!!.searchProgress.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
            binding!!.cocktailList.visibility = if (isShow) View.INVISIBLE else View.VISIBLE
        }
    }
}