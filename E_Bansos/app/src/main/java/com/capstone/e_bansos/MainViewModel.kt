package com.capstone.e_bansos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel:ViewModel() {
    val listDatas = MutableLiveData<ArrayList<Data>>()

    fun setData(){
        val datas = arrayListOf<Data>()
        val client = AsyncHttpClient()
        val url = "http://35.197.130.190/"
        client.addHeader("User-Agent","request")
        client.get(url, object: AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                val result = String(responseBody)
                try{
                    val obj = JSONObject(result)
                    val data = obj.getJSONArray("data")
                    for(i in 0 until data.length()) {
                        val item = data.getJSONObject(i)
                        val kecamatan = item.getString("kecamatan")
                        val tanggal = item.getString("tanggal")
                        val prediksi = item.getString("prediksi")
                        datas.add(
                            Data(kecamatan = kecamatan,
                                tanggal = tanggal,
                                prediksi = prediksi
                            )
                        )
                    }
                    listDatas.postValue(datas)
                }
                catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }
    fun getDatas(): LiveData<ArrayList<Data>> {
        return listDatas
    }
}