package com.example.addepar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.addepar.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding
    private var investment: Investment? = null

    companion object {
        private const val ARG_INVESTMENT_KEY = "investment"

        fun newInstance(investment: Investment): BottomSheetFragment {
            val fragment = BottomSheetFragment()
            val args = Bundle()
            args.putSerializable(ARG_INVESTMENT_KEY, investment)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        investment = arguments?.getSerializable(ARG_INVESTMENT_KEY) as Investment?
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(LayoutInflater.from(inflater.context), container, false)

        bindInvestmentData()

        return binding.root
    }

    private fun bindInvestmentData() {
        investment?.let {
            binding.toolbar.title = it.name
            binding.tvValue.text = it.details
            it.ticker?.let { ticker -> binding.tvValue1.text = ticker }
            binding.tvValue2.text = it.value
            binding.tvValue3.text = it.principal
        }
    }

}
