package com.example.addepar

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainViewModel(private val context: Context) : ViewModel() {
    private var _investments = MutableLiveData<Array<Investment>>()
    val investments: LiveData<Array<Investment>>
        get() = _investments

    init {
        _investments.value = loadInvestmentsFromJson()
    }

    private fun loadInvestmentsFromJson(): Array<Investment> {
        val inputStream = context.assets.open("investments.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val jsonString = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        return gson.fromJson(jsonString, Array<Investment>::class.java)
    }
}