package com.keunyoung.hybridapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.keunyoung.hybridapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityMainBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		setupViewPager()
	}
	
	private fun setupViewPager() {
		val adapter = ViewPagerAdapter(this)
		binding.viewPager.adapter = adapter
		
		TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
			when (position) {
				0 -> tab.text = "WebView"
				1 -> tab.text = "Native"
			}
		}.attach()
	}
	
}