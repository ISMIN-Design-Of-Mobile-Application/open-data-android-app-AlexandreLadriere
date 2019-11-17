package com.ismin.opendataapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ismin.opendataapp.placesfragment.Place
import com.ismin.opendataapp.placesfragment.PlaceListFragment
import com.ismin.opendataapp.sportsfragment.Sport
import com.ismin.opendataapp.sportsfragment.SportsFragment
import com.ismin.opendataapp.sportsfragment.SportsService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), MapFragment.OnFragmentInteractionListener,
    SportsFragment.OnFragmentInteractionListener, PlaceListFragment.OnFragmentInteractionListener {

    private val SERVER_BASE_URL: String = "https://sportplaces-api.herokuapp.com/api/v1/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_BASE_URL)
        .build()

    private val sportsFragment = SportsFragment()
    private val placesListFragment = PlaceListFragment()
    private val mapFragment = MapFragment()

    private val sportsService = retrofit.create<SportsService>(SportsService::class.java)
    private val sportsList: ArrayList<Sport> = ArrayList()
    private val placesList: ArrayList<Place> = ArrayList()

    private fun initiateSportsList() {
        sportsService.getAllSports()
            .enqueue(object : Callback<List<Sport>> {
                override fun onResponse(
                    call: Call<List<Sport>>, response: Response<List<Sport>>
                ) {
                    val allSports = response.body()
                    if (allSports != null) {
                        sportsList.clear()
                        sportsList.addAll(allSports)
                        sportsList.sortBy { it.name }
                        sportsFragment.setSportsList(sportsList)
                    }
                }

                override fun onFailure(call: Call<List<Sport>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error: $t", Toast.LENGTH_LONG).show()
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(a_main_toolbar)
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(sportsFragment, "Sports")
        viewPagerAdapter.addFragment(placesListFragment, "Place List")
        viewPagerAdapter.addFragment(mapFragment, "Map")

        a_main_view_pager.adapter = viewPagerAdapter
        a_main_tabs.setupWithViewPager(a_main_view_pager)

        // TEST
        placesList.add(Place("Orange Vélodrome", "24 Rue du Commandant Guilbaud\n75016 Paris\nFrance", "0.123", "45.123", "54.123"))
        placesList.add(Place("Stade Municipal de Melun", "2 Rue Dorée\n77000 Melun\nFrance", "0.077", "77.123", "12.123", image = R.drawable.stade_municipal_melun))
        placesList.add(Place("Stadio Olimpico", "Viale dei Gladiatori\n00135 Roma RM\nItaly", "1023.193", "77.123", "12.123", image = R.drawable.stadio_olimpico))
        placesListFragment.setPlacesList(placesList)
    }

    override fun onResume() {
        super.onResume()
        checkConnectivity(this)
        initiateSportsList()
    }

    override fun sendPlaceObject(place: Place) {
        val intent = Intent(this, PlaceDetailsActivity::class.java)
        intent.putExtra(Intent.EXTRA_TEXT, place)
        this.startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_information -> {
                val intent = Intent(this, InfoActivity::class.java)
                this.startActivity(intent)
                //Toast.makeText(this, "Information activity not implemented yet", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // implementation of fragment interface
    override fun onFragmentInteractionMap(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFragmentInteractionSports(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFragmentInteractionPlaceList(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun checkConnectivity(context: Context) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        //Alert Dialog box
        val alertDialog: AlertDialog? = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User clicked OK button
                    })
            }
            builder.setTitle(R.string.no_internet_connexion)
            builder.setMessage(R.string.offline_message)
            // Create the AlertDialog
            builder.create()
        }
        if(!isConnected) {
            alertDialog?.show()
        }
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

}
