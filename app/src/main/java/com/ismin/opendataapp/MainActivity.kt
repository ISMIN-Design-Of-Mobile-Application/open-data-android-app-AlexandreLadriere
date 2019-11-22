package com.ismin.opendataapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
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
import androidx.core.app.ActivityCompat
import androidx.viewpager.widget.ViewPager
import com.ismin.opendataapp.placesfragment.PlaceListFragment
import com.ismin.opendataapp.placesfragment.PlaceModel
import com.ismin.opendataapp.placesfragment.PlaceService
import com.ismin.opendataapp.placesfragment.database.PlaceEntity
import com.ismin.opendataapp.sportsfragment.SportsFragment
import com.ismin.opendataapp.sportsfragment.SportsService
import com.ismin.opendataapp.sportsfragment.database.SportDAO
import com.ismin.opendataapp.sportsfragment.database.SportDatabase
import com.ismin.opendataapp.sportsfragment.database.SportEntity
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.android.schedulers.AndroidSchedulers


class MainActivity : AppCompatActivity(), MapFragment.OnFragmentInteractionListener,
    SportsFragment.OnFragmentInteractionListener, PlaceListFragment.OnFragmentInteractionListener {

    private val SERVER_BASE_URL: String = "https://sportplaces-api.herokuapp.com/api/v1/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_BASE_URL)
        .build()

    private lateinit var mainViewPager: ViewPager
    private lateinit var sportDAO: SportDAO
    private lateinit var locationManager: LocationManager

    private val sportsFragment = SportsFragment()
    private val placesListFragment = PlaceListFragment()
    private val mapFragment = MapFragment()

    private val sportsService = retrofit.create<SportsService>(SportsService::class.java)
    private val sportsList: ArrayList<SportEntity> = ArrayList()
    private val placesList: ArrayList<PlaceEntity> = ArrayList()

    private var disposable: Disposable? = null
    private val PlaceServe by lazy {
        PlaceService.create()
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewPager = findViewById<ViewPager>(R.id.a_main_view_pager)
        setSupportActionBar(a_main_toolbar)

        val requestCode = 0
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            requestCode
        )

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(sportsFragment, "Sports")
        viewPagerAdapter.addFragment(placesListFragment, "Place List")
        viewPagerAdapter.addFragment(mapFragment, "Map")

        sportDAO = SportDatabase.getAppDatabase(this).getSportDAO()

        locationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            0,
            0f,
            locationListener
        )

        a_main_view_pager.adapter = viewPagerAdapter
        a_main_tabs.setupWithViewPager(a_main_view_pager)
        searchPlaces("-73.582", "45.511", "99", "175")
    }

    override fun onResume() {
        super.onResume()
        checkGpsStatus()
        checkConnectivity(this)
        initiateSportsList()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun searchPlaces(longitude: String, latitude: String, radius: String, sportCode: String) {
        disposable = PlaceServe.getPlaces("$longitude,$latitude", radius, sportCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> fillPlaceListFromResult(result) },
                { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
            )
    }

    private fun fillPlaceListFromResult(result: PlaceModel.Result) {
        placesList.clear()
        // TODO: boucle for pour result.count et pas juste pour data.features.size
        for(i in 0 until result.data.features.size) {
            var website = "https://www.jcchevalier.fr/"
            if(result.data.features[i].properties.contact_details.website != null) {
                website = result.data.features[i].properties.contact_details.website
            }

            placesList.add(
                PlaceEntity(
                    i,
                    result.data.features[i].properties.name,
                    "${result.data.features[i].properties.address_components.address}\n${result.data.features[i].properties.address_components.city}\n${result.data.features[i].properties.address_components.country}\n${result.data.features[i].properties.address_components.province}",
                    result.data.features[i].properties.proximity.toString(),
                    result.data.features[i].geometry.coordinates[0].toString(),
                    result.data.features[i].geometry.coordinates[1].toString(),
                    website,
                    image = R.drawable.stade_velodrome // TODO: Modifier image
                )
            )
        }
        placesListFragment.setPlacesList(placesList)
    }

    private fun initiateSportsList() {
        sportsService.getAllSports()
            .enqueue(object : Callback<List<SportEntity>> {
                override fun onResponse(
                    call: Call<List<SportEntity>>, response: Response<List<SportEntity>>
                ) {
                    val allSports = response.body()
                    if (allSports != null) {
                        allSports.forEach { sportEntity: SportEntity ->
                            sportDAO.insert(sportEntity)
                        }
                        sportsList.clear()
                        sportsList.addAll(allSports)
                        sportsList.sortBy { it.name }
                        sportsFragment.setSportsList(sportsList)
                    }
                }

                override fun onFailure(call: Call<List<SportEntity>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error: $t", Toast.LENGTH_LONG).show()
                }
            })
    }

    override fun sendPlaceObject(place: PlaceEntity) {
        val intent = Intent(this, PlaceDetailsActivity::class.java)
        intent.putExtra(Intent.EXTRA_TEXT, place)
        this.startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
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
        if (!isConnected) {
            alertDialog?.show()
        }
    }

    private fun checkGpsStatus() {
        val gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val alertDialog: AlertDialog? = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User clicked OK button
                    })
            }
            builder.setTitle(R.string.gps_off)
            builder.setMessage(R.string.gps_off_message)
            // Create the AlertDialog
            builder.create()
        }
        if(!gpsStatus) {
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

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_information -> {
                val intent = Intent(this, InfoActivity::class.java)
                this.startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onFragmentInteractionMap(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFragmentInteractionSports(list: ArrayList<SportEntity>, distance: Int) {
        mainViewPager.currentItem = 1
        placesListFragment.setSelectedSportsList(list, distance)
    }

    override fun onFragmentInteractionPlaceList(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            mapFragment.setActualLocation(location)
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
}