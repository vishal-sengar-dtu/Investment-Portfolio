package com.example.addepar

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.addepar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var investmentAdapter: InvestmentAdapter
    private var investmentNameOrder = true
    private var investmentValueOrder = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, ViewModelFactory(applicationContext))[MainViewModel::class.java]

        setStatusBarColor()
        setInvestmentsObservers()
        setSearchListener()

        setContentView(binding.root)
    }

    private fun setSearchListener() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { investmentAdapter.filter(query) }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let { investmentAdapter.filter(query) }
                return true
            }

        })
    }

    private fun setInvestmentsObservers() {
        viewModel.investments.observe(this){
            investmentAdapter = InvestmentAdapter(it, this)
            binding.rvContainer.adapter = investmentAdapter
        }
    }

    private fun setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        }

    }

    fun sortByName(view: View) {
        investmentNameOrder = investmentAdapter.reOrder(investmentNameOrder, "NAME")
    }

    fun sortByValue(view: View) {
         investmentValueOrder = investmentAdapter.reOrder(investmentValueOrder, "VALUE")
    }

    override fun onItemClicked(investment: Investment) {
        val bottomSheet = BottomSheetFragment.newInstance(investment)
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }
}

