package com.example.addepar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.addepar.databinding.InvestmentItemViewBinding
import java.util.Locale

interface OnItemClickListener {
    fun onItemClicked(investment: Investment)
}

class InvestmentAdapter(
    private val investments: Array<Investment>,
    private val clickListener: OnItemClickListener
) : RecyclerView.Adapter<InvestmentAdapter.InvestmentViewHolder>() {

    private var filteredInvestments: Array<Investment> = investments

    inner class InvestmentViewHolder(private val binding: InvestmentItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, clickListener: OnItemClickListener) {
            val investment = filteredInvestments[position]
            binding.tvName.text = investment.name
            binding.tvValue.text = investment.value
            binding.root.setOnClickListener{
                clickListener.onItemClicked(investment)
            }
        }
    }

    fun filter(query: String) {
        filteredInvestments = if(query.isEmpty()) {
            investments
        } else {
            investments.filter {
                it.name.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))
            }.toTypedArray()
        }
        notifyDataSetChanged()
    }

    fun reOrder(order: Boolean, type: String): Boolean {
        when(type) {
            "NAME" -> {
                if(order) filteredInvestments.sortBy { it.name }
                else filteredInvestments.sortByDescending { it.name }
            }
            "VALUE" -> {
                if(order) filteredInvestments.sortBy { it.value.toLong() }
                else filteredInvestments.sortByDescending { it.value.toLong() }
            }
        }
        notifyDataSetChanged()
        return !order
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvestmentViewHolder {
        val binding = InvestmentItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InvestmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InvestmentViewHolder, position: Int) {
        holder.bind(position, clickListener)
    }

    override fun getItemCount(): Int {
        return filteredInvestments.size
    }
}