package ru.hzkrym.cocktailapp.receipt.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hzkrym.cocktailapp.databinding.ReceiptFragmentBinding
import ru.hzkrym.cocktailapp.receipt.data.ReceiptModel
import ru.hzkrym.cocktailapp.receipt.data.ReceiptRepository

class ReceiptFragment private constructor() : Fragment() {

    private val id: String
        get() = arguments?.getString(ID_KEY) ?: ""

    private var binding: ReceiptFragmentBinding? = null
    private val repository = ReceiptRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ReceiptFragmentBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgress(true)
        loadReceipt()
    }

    private fun loadReceipt() {
        lifecycleScope.launch(Dispatchers.IO) {
            val resultModel = repository.searchReceipt(id)
            withContext(Dispatchers.Main) {
                if (resultModel != null)
                    initViews(resultModel)
            }
        }
    }

    private fun initViews(model: ReceiptModel) {
        binding?.let {
            it.cocktailTitle.text = model.title
            it.cocktailReceipt.text = model.receipt
            it.cocktailIngredients.adapter = IngredientsAdapter(model.ingredients)
            Picasso.get()
                .load(Uri.parse(model.imageUrl))
                .resize(requireContext().toPx(150), requireContext().toPx(150))
                .transform(RoundedCornersTransformation(20, 0))
                .into(it.cocktailImage)
            buttonEffect(it.backButton)
            it.backButton.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
        showProgress(false)
    }

    private fun showProgress(isShow: Boolean) {
        if (binding != null) {
            binding!!.searchProgress.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
            binding!!.receiptConstraint.visibility = if (isShow) View.INVISIBLE else View.VISIBLE
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun buttonEffect(button: View) {
        button.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background.alpha = 125
                    v.invalidate()
                }

                MotionEvent.ACTION_UP -> {
                    v.background.alpha = 255
                    v.invalidate()
                }
            }
            false
        }
    }

    private fun Context.toPx(dp: Int) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics
    ).toInt()

    companion object {

        const val ID_KEY = "ID_KEY"

        fun newInstance(id: String): Fragment = ReceiptFragment().apply {
            arguments = bundleOf(ID_KEY to id)
        }
    }
}