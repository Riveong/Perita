package com.riveong.storyapp.ui.Activities

import android.content.ContentValues.TAG
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.riveong.storyapp.Data.Model.StoriesViewModel
import com.riveong.storyapp.Data.Model.ViewModelFactory
import com.riveong.storyapp.Data.Repository.MapEntity
import com.riveong.storyapp.Data.Repository.Result
import com.riveong.storyapp.R
import com.riveong.storyapp.databinding.ActivityMapsBinding
import com.riveong.storyapp.ui.Adapter.StoryAdapter

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
        addMarkers()
        setMapStyle()
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }


    private fun addMarkers() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: StoriesViewModel by viewModels {
            factory
        }
        viewModel.getMap().observe(this@MapsActivity) { result ->

            if (result != null) {

                when (result) {
                    is Result.Loading -> {
                        //loading
                        Log.w("loading","loading")
                    }

                    is Result.Success -> {
                        //success
                        var listData = result.data
                        listData.forEach { coor ->
                            val latLng = LatLng(coor.lat, coor.lon)
                            mMap.addMarker(
                                MarkerOptions()
                                    .position(latLng)
                                    .title(coor.title)
                                    .snippet(coor.Description))
                        }

                    }

                    is Result.Error -> {
                        //error
                        Log.w("error","error")
                        Toast.makeText(
                            this,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT

                        ).show()
                    }
                }
            }
        }

    }


}