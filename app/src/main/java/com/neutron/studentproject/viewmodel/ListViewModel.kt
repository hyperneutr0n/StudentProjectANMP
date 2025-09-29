package com.neutron.studentproject.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neutron.studentproject.model.Student
import com.neutron.studentproject.util.FileHelper

class ListViewModel(application: Application) : AndroidViewModel(application) {
    val studentsLD = MutableLiveData<ArrayList<Student>>()
    val errorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh() {
        loadingLD.value = true
        errorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://jsonkeeper.com/b/LLMW"

        val stringReq = StringRequest(
            Request.Method.GET, url, {
                val sType = object : TypeToken<List<Student>>() {}.type
                val result = Gson().fromJson<List<Student>>(it, sType)
                studentsLD.value = result as ArrayList<Student>?
                loadingLD.value = false

                val jsonString = Gson().toJson(result)
                val fileHelper = FileHelper(getApplication())
                fileHelper.writeToFile("Hello world")
                Log.d("print_file", jsonString)
            },
            {
                errorLD.value = true
                loadingLD.value = false
            }
        )

        stringReq.tag = TAG
        queue?.add(stringReq)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}