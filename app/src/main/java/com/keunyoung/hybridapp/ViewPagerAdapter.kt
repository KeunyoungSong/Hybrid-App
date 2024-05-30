package com.keunyoung.hybridapp

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
	override fun getItemCount(): Int = 2
	
	override fun createFragment(position: Int): Fragment {
		return when (position) {
			0 -> WebViewFragment()
			1 -> NativeFragment()
			else -> throw IllegalStateException("Unexpected position: $position")
		}
	}
}