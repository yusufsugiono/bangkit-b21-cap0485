package com.capstone.e_bansos

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.e_bansos.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.loopj.android.http.AsyncHttpClient.log
import kotlin.concurrent.schedule
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ItemRate2Adapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    lateinit var auth: FirebaseAuth


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showLoading(true)
        adapter = ItemRate2Adapter()
        adapter.notifyDataSetChanged()
        binding.rvRate2.layoutManager = LinearLayoutManager(this)
        binding.rvRate2.adapter = adapter
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.setData()
        mainViewModel.getDatas().observe(this, Observer{ Data ->
            if (Data != null) {
                adapter.setData(Data)
                showLoading(false)
            }
        })
        auth = FirebaseAuth.getInstance()

        binding.webview.webViewClient = WebViewClient()
        binding.webview.apply {
            loadUrl("https://www.google.co.id/maps/place/Jakarta+Timur,+Kota+Jakarta+Timur,+Daerah+Khusus+Ibukota+Jakarta/@-6.2609728,106.8349793,12z/data=!3m1!4b1!4m5!3m4!1s0x2e69f2d148fbe713:0x6e667d52ebedf5a9!8m2!3d-6.2250138!4d106.9004472")
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
        }


    }
    private fun showLoading(state: Boolean){
        if(state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvRate2.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvRate2.visibility = View.VISIBLE
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout->{
                auth.signOut()
                Intent(this@MainActivity , LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(it)
                }
            }
        }
        return true
    }

}