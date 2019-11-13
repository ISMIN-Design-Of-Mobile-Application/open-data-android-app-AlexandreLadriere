package com.ismin.opendataapp.placesfragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ismin.opendataapp.R

class PlaceListFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private var placesList: ArrayList<Place> = ArrayList()
    private val adapter = PlacesAdapter(placesList, ::selectPlace)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_place_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.f_place_list_rcv_place_list)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteractionPlaceList(uri)
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

    fun setPlacesList(placesList: ArrayList<Place>) {
        this.placesList.clear()
        this.placesList.addAll(placesList)
        adapter.notifyDataSetChanged()
    }

    private fun selectPlace(position: Int) {
        // TODO: Implement that function after creating the third fragment
        val placeToDisplay = this.placesList[position]
        Toast.makeText(context, "$placeToDisplay", Toast.LENGTH_SHORT).show()
        listener?.sendPlaceObject(placeToDisplay)
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteractionPlaceList(uri: Uri)
        fun sendPlaceObject(place: Place)
    }

}
