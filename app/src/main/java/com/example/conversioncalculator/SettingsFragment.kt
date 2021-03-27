package com.example.conversioncalculator


import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.conversioncalculator.MainActivity
import com.example.conversioncalculator.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class SettingsFragment : Fragment() {

    private lateinit var callback: OnUnitsChangeListener

    fun setOnSetChangeListener(callback: OnUnitsChangeListener) {
        this.callback = callback
    }

    interface OnUnitsChangeListener {
        fun onUnitsChange(fromUnit: String, toUnit: String)
    }

    private lateinit var conversionType: String
    private var fromUnit = "Yards"
    private var toUnit = "Meters"
    private var fromSpinnerPosition = 0
    private var toSpinnerPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getString("key", "Length")?.let {
            conversionType = it
        }
        arguments?.getString("from", "Yards")?.let {
            fromUnit = it
        }
        arguments?.getString("to", "Meters")?.let {
            toUnit = it
        }

        val mainActivity = this.activity as MainActivity

        conversionType = mainActivity.getConversionType()
        mainActivity.invalidateOptionsMenu()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        requireActivity().title = "Settings"

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateSpinners(conversionType)
        updateDefaultSpinnerUnits()
        updateUnits()

        view.findViewById<FloatingActionButton>(R.id.fabIcon).setOnClickListener {
            callback.onUnitsChange(fromUnit, toUnit)
        }
    }

    private fun populateSpinners(conversion: String) {

        if (conversion == "Length") {

            val conversionUnits = arrayOf("Meters", "Yards", "Miles")

            if (requireView().findViewById<Spinner>(R.id.settingsFromSpinner) != null) {
                val arrayAdapter = ArrayAdapter(
                    requireContext(),
                    R.layout.support_simple_spinner_dropdown_item,
                    conversionUnits
                )
                requireView().findViewById<Spinner>(R.id.settingsFromSpinner).adapter = arrayAdapter
            }

            if (requireView().findViewById<Spinner>(R.id.settingsToSpinner) != null) {
                val arrayAdapter = ArrayAdapter(
                    requireContext(),
                    R.layout.support_simple_spinner_dropdown_item,
                    conversionUnits
                )
                requireView().findViewById<Spinner>(R.id.settingsToSpinner).adapter = arrayAdapter
            }
        }

        if (conversion == "Volume") {

            val conversionUnits = arrayOf("Liters", "Gallons", "Quarts")

            if (requireView().findViewById<Spinner>(R.id.settingsFromSpinner) != null) {
                val arrayAdapter = ArrayAdapter(
                    requireContext(),
                    R.layout.support_simple_spinner_dropdown_item,
                    conversionUnits
                )
                requireView().findViewById<Spinner>(R.id.settingsFromSpinner).adapter = arrayAdapter
            }

            if (requireView().findViewById<Spinner>(R.id.settingsToSpinner) != null) {
                val arrayAdapter = ArrayAdapter(
                    requireContext(),
                    R.layout.support_simple_spinner_dropdown_item,
                    conversionUnits
                )
                requireView().findViewById<Spinner>(R.id.settingsToSpinner).adapter = arrayAdapter
            }
        }
    }

    private fun updateDefaultSpinnerUnits() {
        if (conversionType == "Length") {
            when (fromUnit) {
                "Meters" -> fromSpinnerPosition = 0
                "Yards" -> fromSpinnerPosition = 1
                "Miles" -> fromSpinnerPosition = 2
            }

            when (toUnit) {
                "Meters" -> toSpinnerPosition = 0
                "Yards" -> toSpinnerPosition = 1
                "Miles" -> toSpinnerPosition = 2
            }
        }
        if (conversionType == "Volume") {
            when (fromUnit) {
                "Liters" -> fromSpinnerPosition = 0
                "Gallons" -> fromSpinnerPosition = 1
                "Quarts" -> fromSpinnerPosition = 2
            }

            when (toUnit) {
                "Liters" -> toSpinnerPosition = 0
                "Gallons" -> toSpinnerPosition = 1
                "Quarts" -> toSpinnerPosition = 2
            }
        }

        requireView().findViewById<Spinner>(R.id.settingsFromSpinner).setSelection(fromSpinnerPosition)
        requireView().findViewById<Spinner>(R.id.settingsToSpinner).setSelection(toSpinnerPosition)
    }

    private fun updateUnits() {

        requireView().findViewById<Spinner>(R.id.settingsFromSpinner).onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                fromUnit = requireView().findViewById<Spinner>(R.id.settingsToSpinner).selectedItem.toString()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                fromUnit = requireView().findViewById<Spinner>(R.id.settingsFromSpinner).selectedItem.toString()
            }
        }

        requireView().findViewById<Spinner>(R.id.settingsToSpinner).onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                toUnit = requireView().findViewById<Spinner>(R.id.settingsToSpinner).selectedItem.toString()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                toUnit = requireView().findViewById<Spinner>(R.id.settingsToSpinner).selectedItem.toString()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.setGroupVisible(R.id.menu_settings, false)
    }
}
