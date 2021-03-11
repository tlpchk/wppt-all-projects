package com.appteam.catchup

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.appteam.catchup.MapFragment.Companion.EVENT_POSITION
import com.appteam.catchup.notification.NotificationService
import com.appteam.catchup.ui.GlideApp
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_navigation_drawer.*
import kotlinx.android.synthetic.main.nav_header_navigation_drawer.view.*
import java.security.MessageDigest

const val TAG = "am2019"

const val LOGIN_INTENT_CODE = 2137

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var navigation: NavController
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var centerOnDevicePosition = true
    var cameraPosition: LatLng? = null

    private val mAuthStateListener = FirebaseAuth.AuthStateListener {
        val user = it.currentUser
        if (user != null) {
            val welcome = getString(R.string.welcome)
            val username = user.displayName
            updateUserUI(user)
        } else {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.FacebookBuilder().build()
            )

            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setIsSmartLockEnabled(false)
                    .setAvailableProviders(providers)
                    .setTheme(R.style.subtleBackground)
                    .setLogo(R.drawable.logo_catchup_transparent)
                    .build(),
                LOGIN_INTENT_CODE
            )
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOGIN_INTENT_CODE) {
            when (resultCode) {
                Activity.RESULT_CANCELED -> {
                    // user back pressed without logging in
                    // close application
                    finish()
                }
            }
        }
    }

    private fun updateUserUI(user: FirebaseUser?) {
        user?.let {
            val navigationView = findViewById<NavigationView>(R.id.navView)
            val navHeader = navigationView.getHeaderView(0)
            navHeader.apply {
                GlideApp.with(this@MainActivity)
                    .load(it.photoUrl)
                    .circleCrop()
                    .into(userImage)
                userDisplayName.text = it.displayName
                userEmail.text = it.email
            }
        }
    }

    private fun logKeyHashForFacebook() {
        // Add code to print out the key hash
        try {
            val info = packageManager.getPackageInfo(
                "com.appteam.catchup",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        }
        catch (e: Exception) {e.printStackTrace()}
    }

    override fun onResume() {
        firebaseAuth.addAuthStateListener(mAuthStateListener)
        super.onResume()
    }

    override fun onPause() {
        firebaseAuth.removeAuthStateListener(mAuthStateListener)
        super.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)
        Log.i(TAG, "onCreate:START")

        logKeyHashForFacebook()

        navigation = (supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment).navController

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        navigation.let {
            when (item.itemId) {
                R.id.nav_home -> {
                    val bundle = Bundle()
                    bundle.putParcelable(EVENT_POSITION,cameraPosition)
                    it.navigate(R.id.mapScreen,bundle)
                }
                R.id.nav_event_details -> {
                    it.navigate(R.id.eventListScreen)
                }
                R.id.nav_logout -> {
                    logout()
                }
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun logout() {
        firebaseAuth.signOut()
    }
}
