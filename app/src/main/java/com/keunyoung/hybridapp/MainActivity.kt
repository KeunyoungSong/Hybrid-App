package com.keunyoung.hybridapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.keunyoung.hybridapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityMainBinding
	private var webViewFragment: WebViewFragment? = null
	private var nativeFragment: NativeFragment? = null
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		binding.tabs.addTab(binding.tabs.newTab().setText("WebView"))
		binding.tabs.addTab(binding.tabs.newTab().setText("Native"))
		
		if (savedInstanceState == null) {
			webViewFragment = WebViewFragment()
			nativeFragment = NativeFragment()
			supportFragmentManager.beginTransaction()
				.add(R.id.fragment_container, webViewFragment!!, "WebViewFragment")
				.add(R.id.fragment_container, nativeFragment!!, "NativeFragment")
				.hide(nativeFragment!!)
				.commit()
		} else {
			webViewFragment = supportFragmentManager.findFragmentByTag("WebViewFragment") as? WebViewFragment
			nativeFragment = supportFragmentManager.findFragmentByTag("NativeFragment") as? NativeFragment
		}
		
		binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
			override fun onTabSelected(tab: TabLayout.Tab?) {
				val fragmentTransaction = supportFragmentManager.beginTransaction()
				when (tab?.position) {
					0 -> {
						fragmentTransaction.hide(nativeFragment!!)
						fragmentTransaction.show(webViewFragment!!)
					}
					1 -> {
						fragmentTransaction.hide(webViewFragment!!)
						fragmentTransaction.show(nativeFragment!!)
					}
				}
				fragmentTransaction.commit()
			}
			
			override fun onTabUnselected(tab: TabLayout.Tab?) {}
			override fun onTabReselected(tab: TabLayout.Tab?) {}
		})
	}
	
	fun getFragmentAt(position: Int): Fragment? {
		return when (position) {
			0 -> webViewFragment
			1 -> nativeFragment
			else -> null
		}
	}
}