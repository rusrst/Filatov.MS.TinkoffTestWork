package com.example.tinkofftestwork

import ViewPagerAdapter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tinkofftestwork.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.util.concurrent.TimeUnit

val tabsArray = listOf("Последние", "Горячие", "Лучшие")
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        updateUi()


        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabsArray[position]
        }.attach()
    }
    private fun updateUi() {
        supportActionBar?.title = getString(R.string.app_bar_header)
    }
}