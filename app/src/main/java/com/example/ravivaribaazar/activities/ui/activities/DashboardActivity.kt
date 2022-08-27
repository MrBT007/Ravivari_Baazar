package com.example.ravivaribaazar.activities.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.bumptech.glide.Glide
import com.example.ravivaribaazar.R
import com.example.ravivaribaazar.databinding.ActivityDashboardBinding
import com.example.ravivaribaazar.firestore.FirestoreClass
import com.example.ravivaribaazar.models.User
import com.example.ravivaribaazar.utils.GlideLoader
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.sliding_drawer.*

// TODO: Toolbar disappears by Fragments whenever implement recycler view (or maybe another view also) into Fragments

class DashboardActivity : BaseActivity(){

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // for drawer image, name and id
        showProgressDialog(resources.getString(R.string.please_wait))
        getUserDetails()

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,R.drawable.app_gradient_color_background
            )
        )
        supportActionBar!!.hide()
        val navView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_dashboard)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard, R.id.navigation_products, R.id.navigation_orders
            )
        )

        toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar_dashboard_fragment,R.string.open,R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        drawer_nav_view.setNavigationItemSelectedListener {item->
            when(item.itemId)
            {
                R.id.about_us_drawer -> Toast.makeText(this, "Clicked About Us", Toast.LENGTH_SHORT).show()
                R.id.share_drawer-> Toast.makeText(this, "Clicked Share", Toast.LENGTH_SHORT).show()
                R.id.rate_drawer -> Toast.makeText(this, "Clicked Rate", Toast.LENGTH_SHORT).show()
                R.id.settings_drawer -> startActivity(Intent(this,SettingsActivity::class.java))
            }
            true
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    override fun onBackPressed() {
        doubleBackToExit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.navigation_dashboard)
        {
            tv_title_fragment.text = "Dashboard"
        }
        else if(item.itemId == R.id.navigation_products)
        {
            tv_title_fragment.text = "Products"
        }
        else if(item.itemId == R.id.navigation_orders)
        {
            tv_title_fragment.text = "Orders"
        }
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment_activity_dashboard)) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_activity_dashboard).navigateUp()
    }

    private fun getUserDetails()
    {
        FirestoreClass().getUserDetails(this)
    }

    fun userDetailsSuccess(user: User)
    {
        if(user.image != null)
        {
            Glide.with(this)
                .load(user.image)
                .into(profile_image!!)
        }
        else
        {
            Glide.with(this)
                .load(R.drawable.user_profile_default)
                .into(profile_image!!)
        }

        user_name_drawer.text = "${user.firstName} ${user.lastName}"
        user_id_drawer.text = user.email
        hideProgressDialog()
    }
}