package com.example.conversioncalculator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), ConversionHomeScreen.OnModeChangeListener,
    SettingsFragment.OnUnitsChangeListener {

    private var bundle = Bundle()
    private var conversionType = "Length"
    private var fromUnit = "Meters"
    private var toUnit = "Yards"
    private var inSettingsFragment = false
    private lateinit var mainMenu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            this.bundle.putString("key", conversionType)
            this.bundle.putString("fromUnit", fromUnit)
            this.bundle.putString("toUnit", toUnit)
            val conversionHomeScreen = ConversionHomeScreen()
            conversionHomeScreen.arguments = this.bundle

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, conversionHomeScreen)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

        if (fragment is ConversionHomeScreen) {
            fragment.setOnModeChangeListener(this)
        }

        if (fragment is SettingsFragment) {
            fragment.setOnSetChangeListener(this)
            inSettingsFragment = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        mainMenu = menu!!
        mainMenu.setGroupVisible(R.id.menu_settings, true)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        this.bundle.putString("key", conversionType)
        this.bundle.putString("from", fromUnit)
        this.bundle.putString("to", toUnit)
        val settingsFragment = SettingsFragment()

        if (item.toString() == "Settings") {
            settingsFragment.arguments = bundle

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, settingsFragment)
                .addToBackStack(null)
                .commit()

            inSettingsFragment = true

            return super.onOptionsItemSelected(item)

        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateConversionType(conversionType: String, fromUnit: String, toUnit: String) {
        this.conversionType = conversionType
        this.fromUnit = fromUnit
        this.toUnit = toUnit
    }

    fun getConversionType(): String {
        return conversionType
    }


    override fun onUnitsChange(fromUnit: String, toUnit: String) {
        this.fromUnit = fromUnit
        this.toUnit = toUnit
        this.bundle.putString("key", conversionType)
        this.bundle.putString("fromUnit", fromUnit)
        this.bundle.putString("toUnit", toUnit)
        val conversionHomeScreen = ConversionHomeScreen()
        conversionHomeScreen.arguments = this.bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, conversionHomeScreen)
            .addToBackStack(null)
            .commit()
    }

    override fun onModeChange(conversionType: String, fromUnit: String, toUnit: String) {
        updateConversionType(conversionType, fromUnit, toUnit)
    }
}
