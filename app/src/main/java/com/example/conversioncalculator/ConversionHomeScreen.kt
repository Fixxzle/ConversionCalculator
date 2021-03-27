package com.example.conversioncalculator

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment


class ConversionHomeScreen : Fragment() {

    private lateinit var modeChangeCallback: OnModeChangeListener
    private lateinit var conversionType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        requireActivity().title = "Conversion Calculator"

        return inflater.inflate(R.layout.fragment_conversion_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            conversionType = (arguments?.get("key") as? String)!!
            if (conversionType == "Length") {
                view.findViewById<TextView>(R.id.titleLabel).text = getString(R.string.length_converter)
            } else {
                view.findViewById<TextView>(R.id.titleLabel).text = getString(R.string.volume_converter)
            }
            view.findViewById<TextView>(R.id.fromUnits).text = (arguments?.get("fromUnit") as? String)!!
            view.findViewById<TextView>(R.id.toUnits).text = (arguments?.get("toUnit") as? String)!!
        }

        initializeButtons()
    }


    private fun initializeButtons() {
        // Calculate Button
        requireView().findViewById<Button>(R.id.calculateBtn).setOnClickListener {

            removePhoneKeypad()

            when (conversionType) {
                "Length" -> {
                    if (requireView().findViewById<EditText>(R.id.fromTextField).text.isNullOrBlank()
                        && requireView().findViewById<EditText>(R.id.toTextField).text.isNullOrBlank()) {

                            fieldEmptyError()
                    }

                    else if (requireView().findViewById<EditText>(R.id.fromTextField).text.isNullOrBlank()
                        && !requireView().findViewById<EditText>(R.id.toTextField).text.isNullOrBlank()) {

                            convertLengthToField()
                    }

                    else if (!requireView().findViewById<EditText>(R.id.fromTextField).text.isNullOrBlank()
                        && requireView().findViewById<EditText>(R.id.toTextField).text.isNullOrBlank()) {

                            convertLengthFromField()
                    }

                    else {
                        convertLengthFromField()
                    }
                }
                "Volume" -> {
                    if (requireView().findViewById<EditText>(R.id.fromTextField).text.isNullOrBlank()
                        && requireView().findViewById<EditText>(R.id.toTextField).text.isNullOrBlank()) {

                            fieldEmptyError()
                    }

                    else if (requireView().findViewById<EditText>(R.id.fromTextField).text.isNullOrBlank()
                        && !requireView().findViewById<EditText>(R.id.toTextField).text.isNullOrBlank()) {

                            convertVolumeToField()
                    }

                    else if (!requireView().findViewById<EditText>(R.id.fromTextField).text.isNullOrBlank()
                        && requireView().findViewById<EditText>(R.id.toTextField).text.isNullOrBlank()) {
                        convertVolumeFromField()
                    }

                    else {
                        convertVolumeFromField()
                    }
                }
            }

        }

        // Clear Button
        requireView().findViewById<Button>(R.id.clearBtn).setOnClickListener {
            requireView().findViewById<EditText>(R.id.fromTextField).text.clear()
            requireView().findViewById<EditText>(R.id.toTextField).text.clear()
        }

        // Mode Button
        requireView().findViewById<Button>(R.id.modeBtn).setOnClickListener {

            if (conversionType == "Length") {
                requireView().findViewById<TextView>(R.id.titleLabel).text = getString(R.string.volume_converter)
                conversionType = "Volume"
                requireView().findViewById<TextView>(R.id.fromUnits).text = getString(R.string.gallons)
                requireView().findViewById<TextView>(R.id.toUnits).text = getString(R.string.liters)
            } else {
                requireView().findViewById<TextView>(R.id.titleLabel).text = getString(R.string.length_converter)
                conversionType = "Length"
                requireView().findViewById<TextView>(R.id.fromUnits).text = getString(R.string.yards)
                requireView().findViewById<TextView>(R.id.toUnits).text = getString(R.string.meters)
            }
            modeChangeCallback.onModeChange(
                conversionType,
                requireView().findViewById<TextView>(R.id.fromUnits).text.toString(),
                requireView().findViewById<TextView>(R.id.toUnits).text.toString()
            )
        }
    }

    private fun fieldEmptyError() {
        if (requireView().findViewById<EditText>(R.id.fromTextField).text.isNullOrBlank() && requireView().findViewById<EditText>(R.id.toTextField).text.isNullOrBlank()) {
            Toast.makeText(
                requireContext(),
                "Please enter a value to convert",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun convertLengthToField() {
        val fromLabel = requireView().findViewById<TextView>(R.id.fromUnits).text.toString()
        val toLabel = requireView().findViewById<TextView>(R.id.toUnits).text.toString()

        val convertedNumber = UnitsConverter.convert(
            requireView().findViewById<EditText>(R.id.toTextField).text.toString().toDouble(),
            UnitsConverter.LengthUnits.valueOf(toLabel),
            UnitsConverter.LengthUnits.valueOf(fromLabel)
        )

        requireView().findViewById<EditText>(R.id.fromTextField).setText(convertedNumber.toString())
    }

    private fun convertLengthFromField() {

        val fromLabel = requireView().findViewById<TextView>(R.id.fromUnits).text.toString()
        val toLabel = requireView().findViewById<TextView>(R.id.toUnits).text.toString()

        val convertedNumber = UnitsConverter.convert(
            requireView().findViewById<EditText>(R.id.fromTextField).text.toString().toDouble(),
            UnitsConverter.LengthUnits.valueOf(fromLabel),
            UnitsConverter.LengthUnits.valueOf(toLabel)
        )

        requireView().findViewById<EditText>(R.id.toTextField).setText(convertedNumber.toString())
    }

    private fun convertVolumeToField() {
        val fromLabel = requireView().findViewById<TextView>(R.id.fromUnits).text.toString()
        val toLabel = requireView().findViewById<TextView>(R.id.toUnits).text.toString()

        val convertedNumber = UnitsConverter.convert(
            requireView().findViewById<EditText>(R.id.toTextField).text.toString().toDouble(),
            UnitsConverter.VolumeUnits.valueOf(toLabel),
            UnitsConverter.VolumeUnits.valueOf(fromLabel)
        )

        requireView().findViewById<EditText>(R.id.fromTextField).setText(convertedNumber.toString())
    }

    private fun convertVolumeFromField() {

        val fromLabel = requireView().findViewById<TextView>(R.id.fromUnits).text.toString()
        val toLabel = requireView().findViewById<TextView>(R.id.toUnits).text.toString()

        val convertedNumber = UnitsConverter.convert(
            requireView().findViewById<EditText>(R.id.fromTextField).text.toString().toDouble(),
            UnitsConverter.VolumeUnits.valueOf(fromLabel),
            UnitsConverter.VolumeUnits.valueOf(toLabel)
        )

        requireView().findViewById<EditText>(R.id.toTextField).setText(convertedNumber.toString())
    }

    private fun removePhoneKeypad() {
        val inputManager = requireView()
            .context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val binder = requireView().windowToken
        inputManager.hideSoftInputFromWindow(
            binder,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }


    fun setOnModeChangeListener(callback: OnModeChangeListener) {
        this.modeChangeCallback = callback
    }

    interface OnModeChangeListener {
        fun onModeChange(conversionType: String, fromUnit: String, toUnit: String)
    }

}
