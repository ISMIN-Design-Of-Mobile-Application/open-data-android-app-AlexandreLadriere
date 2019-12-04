package com.ismin.opendataapp

import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.ismin.opendataapp.placesfragment.database.PlaceEntity

class MapFragment : Fragment(), GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var gMap: GoogleMap
    private var locationsList: MutableMap<String, LatLng> = mutableMapOf()
    private var placeEntityList: MutableMap<String, PlaceEntity> = mutableMapOf()
    private var isMapReady = false
    private lateinit var searchAreaButton: Button
    var centerLocation: LatLng = LatLng(48.5421, 2.6554)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        searchAreaButton = view.findViewById<Button>(R.id.f_map_button_search_in_this_area)
        searchAreaButton.setOnClickListener {
            it.visibility = View.INVISIBLE
            locationsList.clear()
            placeEntityList.clear()
            gMap.clear()
            listener?.searchInThisArea(centerLocation.longitude, centerLocation.latitude)
        }
        return view
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteractionMap(uri)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        isMapReady = true
        gMap.setOnCameraIdleListener(this)
        gMap.setOnInfoWindowClickListener(this)
    }

    override fun onInfoWindowClick(p0: Marker?) {
        val intent = Intent(context, PlaceDetailsActivity::class.java)
        if(p0?.tag != null) {
            intent.putExtra(Intent.EXTRA_TEXT, p0?.tag as PlaceEntity)
            this.startActivity(intent)
        }
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
        fun searchInThisArea(longitude: Double, latitude: Double)
    }

    override fun onStart() {
        super.onStart()
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onCameraIdle() {
        centerLocation = gMap.cameraPosition.target
        searchAreaButton.visibility = View.VISIBLE
    }

    fun addLocation(location: Location, name: String, placeEntity: PlaceEntity) {
        val tmpLoc = LatLng(location.latitude, location.longitude)
        locationsList[name] = tmpLoc
        placeEntityList[name] = placeEntity
        if (isMapReady) {
            gMap.addMarker(MarkerOptions().position(tmpLoc).title(name)).tag = placeEntity
        }
    }

    fun setActualLocation(location: Location) {
        val key = "Current Location"
        val tmpLoc = LatLng(location.latitude, location.longitude)
        locationsList[key] = tmpLoc
        placeEntityList[key] = PlaceEntity(0, key, "Here", "0", location.latitude.toString(), location.longitude.toString())
        if (isMapReady) {
            gMap.addMarker(MarkerOptions().position(tmpLoc).title(key)).tag = placeEntityList[key]
        }
    }
}