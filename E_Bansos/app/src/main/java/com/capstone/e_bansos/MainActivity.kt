package com.capstone.e_bansos

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.e_bansos.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var auth: FirebaseAuth
    private val list = ArrayList<ListRate2>()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.rvRate2.setHasFixedSize(true)


        auth = FirebaseAuth.getInstance()

        list.addAll(getListHeroes())
        showRecyclerList()


        binding.webview.webViewClient = WebViewClient()
        binding.webview.apply {
            loadUrl("https://www.google.co.id/maps/place/Jakarta+Timur,+Kota+Jakarta+Timur,+Daerah+Khusus+Ibukota+Jakarta/@-6.2609728,106.8349793,12z/data=!3m1!4b1!4m5!3m4!1s0x2e69f2d148fbe713:0x6e667d52ebedf5a9!8m2!3d-6.2250138!4d106.9004472")
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
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

    fun showRecyclerList() {
        binding.rvRate2.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ItemRate2Adapter(list)
        binding.rvRate2.adapter = listHeroAdapter
    }

    fun getListHeroes(): ArrayList<ListRate2> {

        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.getStringArray(R.array.data_photo)
        val listHero = ArrayList<ListRate2>()
        for (position in dataName.indices) {
            val hero = ListRate2(
                dataName[position],
                dataDescription[position],
                dataPhoto[position]
            )
            listHero.add(hero)
        }
        return listHero

    }
}