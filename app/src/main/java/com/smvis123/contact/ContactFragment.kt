package com.smvis123.contact


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.smvis123.R
import com.smvis123.databinding.FragmentContactBinding
import com.smvis123.helper.GOSPEL_WEBSITE


class ContactFragment : Fragment() {
    var mLatitude = 6.909502
    var mLongitude = 79.874306

    private lateinit var binding: FragmentContactBinding
    private var locationManager: LocationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        isPermissionGranted()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.contactNo.text = ("+94112681701")
        binding.locationAddress.text = HtmlCompat.fromHtml(
            "<b>Location</b>"
                    + "<br> 71A Gregory's Road,"
                    + "<br>Colombo 07"
                    + "<br>Sri Lanka", HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        binding.mailAddress.text = GOSPEL_WEBSITE
        binding.goToMap.visibility = View.GONE
        binding.goToMap.setOnClickListener {
            if (isPermissionGranted()) {
                requestLocationData()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationData() {
        val lastKnownLocation = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val mapUrl =
            "http://maps.google.com/maps?saddr=${lastKnownLocation?.latitude},${lastKnownLocation?.longitude}&daddr=$mLatitude,$mLongitude&mode=driving"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl))
        startActivity(intent)
    }

    private fun isPermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            return if (activity?.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.v("", "Permission is granted")
                true
            } else {

                Log.v("", "Permission is revoked")
                activity?.let {
                    ActivityCompat.requestPermissions(
                        it,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        1
                    )
                }
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("", "Permission is granted")
            return true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("", "Permission: " + permissions[0] + "was " + grantResults[0])
            //resume tasks needing this permission
            requestLocationData()
        }
    }


}
