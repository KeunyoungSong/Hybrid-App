package com.keunyoung.hybridapp

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.keunyoung.hybridapp.databinding.FragmentNativeBinding
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.Date

class NativeFragment : Fragment() {
	
	private var _binding: FragmentNativeBinding? = null
	val binding get() = _binding!!
	private lateinit var fusedLocationClient: FusedLocationProviderClient
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View {
		_binding = FragmentNativeBinding.inflate(inflater, container, false)
		fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
		return binding.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		binding.buttonSendData.setOnClickListener {
			val data = binding.editTextData.text.toString()
			sendDataToWebView(data)
		}
		
		binding.buttonSendLocation.setOnClickListener {
			getLastLocation()
		}
	}
	
	private fun sendDataToWebView(data: String) {
		(activity as? MainActivity)?.let { activity ->
			val webViewFragment = activity.getFragmentAt(0) as? WebViewFragment
			webViewFragment?.binding?.webView?.evaluateJavascript(
				"javascript:receiveDataFromNative('$data')",
			){response ->
				// WebView에서 처리한 결과를 네이티브에서 처리
				handleWebViewResponse(response)
			}
		}
	}
	
	@SuppressLint("MissingPermission")
	fun getLastLocation() {
		if (ContextCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_FINE_LOCATION
			) != PackageManager.PERMISSION_GRANTED
		) {
			ActivityCompat.requestPermissions(
				requireActivity(),
				arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
				1
			)
		} else {
			fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
					location?.let {
						val data = "Lat: ${it.latitude}, Lng: ${it.longitude}, date: ${Date()}"
						sendLocationToWebView(data)
					}
				}
		}
	}
	
	private fun sendLocationToWebView(location: String) {
		(activity as? MainActivity)?.let { activity ->
			val webViewFragment = activity.getFragmentAt(0) as? WebViewFragment
			webViewFragment?.binding?.webView?.evaluateJavascript(
				"javascript:receiveLocationFromNative('$location')",
			) {
				Toast.makeText(activity, "성공적으로 위치를 가져옴", Toast.LENGTH_SHORT).show()
			}
		}
	}
	
	private fun handleWebViewResponse(response: String) {
		binding.textViewResponse.text = response
	}
	
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}