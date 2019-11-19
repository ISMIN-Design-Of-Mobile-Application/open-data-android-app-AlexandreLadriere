package com.ismin.opendataapp

import android.content.Context
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var gMap: GoogleMap
    private var locationsList: ArrayList<LatLng> = ArrayList()
    private var isMapReady = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteractionMap(uri)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        displayOnMap()
        isMapReady = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteractionMap(uri: Uri)
    }

    override fun onStart() {
        super.onStart()
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun displayOnMap() {
        locationsList.forEach {
            gMap.clear()
            gMap.addMarker(MarkerOptions().position(it).title("Mines St Etienne"))
            gMap.moveCamera(CameraUpdateFactory.newLatLng(it))
        }
    }

    fun addLocation(location: Location) {
        locationsList.add(LatLng(location.latitude, location.longitude))
        if (isMapReady) {
            displayOnMap()
        }
    }

    fun setActualLocation(location: Location) {
        if (locationsList.size != 0) {
            locationsList[0] = LatLng(location.latitude, location.longitude)
            if (isMapReady) {
                displayOnMap()
            }
        } else {
            addLocation(location)
        }
    }
}