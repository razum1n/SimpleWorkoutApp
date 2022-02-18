package com.laurirantala.simpleworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.laurirantala.simpleworkoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var binding: ActivityBmiBinding? = null

    private var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.tbBmiActivity)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }

        binding?.tbBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        makeMetricUnitsViewVisible()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->

            if (checkedId == R.id.rb_metric_units) {
                makeMetricUnitsViewVisible()
            } else {
                makeUsUnitsViewVisible()
            }
        }

        binding?.btnCalculateBmi?.setOnClickListener {
            calculateUnits()
        }

    }

    private fun makeMetricUnitsViewVisible() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilUsUnitWeight?.visibility = View.GONE
        binding?.llUsUnits?.visibility = View.GONE
        binding?.etWeightUnitText?.text!!.clear()
        binding?.etHeightUnitText?.text!!.clear()

        binding?.llBmiResultDisplay?.visibility = View.INVISIBLE
    }

    private fun makeUsUnitsViewVisible() {
        currentVisibleView = US_UNITS_VIEW
        binding?.tilMetricUnitHeight?.visibility = View.GONE
        binding?.tilMetricUnitWeight?.visibility = View.GONE
        binding?.tilUsUnitWeight?.visibility = View.VISIBLE
        binding?.llUsUnits?.visibility = View.VISIBLE
        binding?.etWeightUnitText?.text!!.clear()
        binding?.etFeetUnitText?.text!!.clear()
        binding?.etInchUnitText?.text!!.clear()
        binding?.etUsWeightUnitText?.text!!.clear()

        binding?.llBmiResultDisplay?.visibility = View.INVISIBLE
    }


    private fun displayBMIResults(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Workout more!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take better care of yourself! Workout more!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Class | (Severely obese)"
            bmiDescription = "Oops! You really need to take better care of yourself! Workout more!"
        } else {
            bmiLabel = "Obese Class | (Very severely obese)"
            bmiDescription = "Oops! You really need to take better care of yourself! Workout more!"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.llBmiResultDisplay?.visibility = View.VISIBLE
        binding?.tvBmiValue?.text = bmiValue
        binding?.tvBmiType?.text = bmiLabel
        binding?.tvBmiDescription?.text = bmiDescription

    }


    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (binding?.etWeightUnitText?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etHeightUnitText?.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }

    private fun calculateUnits() {
        if (currentVisibleView == METRIC_UNITS_VIEW) {
            if (validateMetricUnits()) {
                val heightValue: Float = binding?.etHeightUnitText?.text.toString().toFloat() / 100

                val weightValue: Float = binding?.etWeightUnitText?.text.toString().toFloat()

                val bmi = weightValue / (heightValue * heightValue)

                displayBMIResults(bmi)

            } else {
                Toast.makeText(this@BMIActivity, "Please enter valid values.", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            if (validateUsUnits()) {
                val usUnitHeightValueFeet: String = binding?.etFeetUnitText?.text.toString()
                val usUnitHeightValueInch: String = binding?.etInchUnitText?.text.toString()
                val usUnitWeightValue: Float =
                    binding?.etUsWeightUnitText?.text.toString().toFloat()

                val heightValue =
                    usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

                val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))

                displayBMIResults(bmi)
            } else {
                Toast.makeText(this@BMIActivity, "Please enter valid values.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun validateUsUnits(): Boolean {
        var isValid = true

        when {
            binding?.etUsWeightUnitText?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etFeetUnitText?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etInchUnitText?.text.toString().isEmpty() -> {
                isValid = false
            }
        }
        return isValid
    }
}