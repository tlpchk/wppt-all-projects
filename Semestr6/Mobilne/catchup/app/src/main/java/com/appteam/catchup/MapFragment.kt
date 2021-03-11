package com.appteam.catchup

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.appteam.catchup.ui.eventDetails.EventDetailsFragment.Companion.EVENT_DETAILS_BUNDLE
import com.appteam.catchup.firebase.service.EventService
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback, LocationListener{
    private lateinit var mMap: GoogleMap
    private lateinit var navController: NavController
    lateinit var locationManager: LocationManager
    companion object {
        val EVENT_ID = "eventId"
        val EVENT_POSITION = "position"
        val NEW_EVENT_POSITION = "newEventPosition"
        val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLocationPermission()
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_maps, container, false)
        navController = findNavController()
        return view
    }

    override fun onStart() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        (activity!! as MainActivity).cameraPosition = mMap.cameraPosition.target
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        enableMyLocalisation()

        mMap.setOnMapLongClickListener {
            val bundle = Bundle()
            bundle.putParcelable(NEW_EVENT_POSITION, it)
            navController.navigate(R.id.createEventScreen, bundle)
            return@setOnMapLongClickListener
        }

        mMap.setOnMarkerClickListener {
            val bundle = Bundle()
            val event = EventService.events[it.tag.toString()]!!
            bundle.putParcelable(EVENT_DETAILS_BUNDLE, event)
            bundle.putString(EVENT_ID, it.tag.toString())
            navController.navigate(R.id.eventDetailsScreen, bundle)

            return@setOnMarkerClickListener true
        }
        if(seeOnMapPosition() != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seeOnMapPosition(), 15f))
            unsetOnMapPosition()
        }

        mMap.clear()
        setMarkers()
    }

    private fun seeOnMapPosition(): LatLng? {

        return arguments?.getParcelable(EVENT_POSITION) as LatLng?
    }

    private fun unsetOnMapPosition(){
        arguments?.putParcelable(EVENT_POSITION, null)
    }

    private fun enableMyLocalisation() {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            if ((activity!! as MainActivity).centerOnDevicePosition) {
                //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    400,
                    1000f,
                    this
                )
                (activity!! as MainActivity).centerOnDevicePosition = false
            }
        }
    }

    /**
     * Initialization of locationProvider and add async eventListeners
     */
    private fun setMarkers() {
        EventService.eventProvider()
            .addOnCompleteListener {
                for (id in EventService.events.keys) {
                    val marker = MarkerOptions().position(EventService.events[id]!!.location)
                    mMap.addMarker(marker).tag = id
                }
            }
    }

    /**
     * Require location permission
     */
    private fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(context)
                    .setTitle(R.string.title_location_permission)
                    .setMessage(R.string.text_location_permission)
                    .setPositiveButton("Allow", DialogInterface.OnClickListener { _, _ ->
                        //Prompt the user once explanation has been shown
                        ActivityCompat.requestPermissions(
                            activity!!,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            MY_PERMISSIONS_REQUEST_LOCATION
                        )
                    })
                    .create()
                    .show()


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
            return false
        } else {
            return true
        }
    }

    override fun onProviderEnabled(provider: String?) {}

    override fun onLocationChanged(location: Location?) {
        val latLng = LatLng(location!!.latitude, location.longitude)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
        mMap.moveCamera(cameraUpdate)
        locationManager.removeUpdates(this)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderDisabled(provider: String?) {}

}
