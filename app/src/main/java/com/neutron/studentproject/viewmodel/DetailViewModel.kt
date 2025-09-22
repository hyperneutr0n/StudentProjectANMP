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

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val studentLD = MutableLiveData<Student>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun fetch(id: String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://jsonkeeper.com/b/LLMW"

        val stringReq = StringRequest(
            Request.Method.GET, url, {
                val sType = object : TypeToken<List<Student>>() {}.type
                val result = Gson().fromJson<List<Student>>(it, sType)
                val data = result as ArrayList<Student>
                studentLD.value = data.find { it.id == id } as Student
                Log.d("Show Volley", result.toString())
            },
            {
                Log.d("Show Volley", it.toString())
            }
        )

        stringReq.tag = TAG
        queue?.add(stringReq)
    }
}