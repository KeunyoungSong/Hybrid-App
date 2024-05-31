package com.keunyoung.hybridapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.keunyoung.hybridapp.databinding.FragmentWebviewBinding

class WebViewFragment : Fragment() {
	
	private var _binding: FragmentWebviewBinding? = null
	val binding get() = _binding!!
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentWebviewBinding.inflate(inflater, container, false)
		return binding.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupWebView()
	}
	
	
	private fun setupWebView() {
		binding.webView.apply {
			webViewClient = WebViewClient()
			webChromeClient = WebChromeClient()
			settings.javaScriptEnabled = true
			addJavascriptInterface(WebAppInterface(), "Android")
			loadUrl("https://hybridappwebview-soeaddpmlq-uc.a.run.app")
		}
	}
	
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
	
	private inner class WebAppInterface {
		@JavascriptInterface
		fun showToast(message: String) {
			activity?.runOnUiThread {
				Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
			}
		}
		
		@JavascriptInterface
		fun requestLocation() {
			(activity as? MainActivity)?.let { activity ->
				val nativeFragment = activity.getFragmentAt(1) as? NativeFragment
				nativeFragment?.getLastLocation()
			}
		}
	}
}

