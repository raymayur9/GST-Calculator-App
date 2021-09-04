package com.example.gstcalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doAfterTextChanged
import com.example.gstcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val items = listOf("INR", "USD")
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, items)
        (binding.currencyMenu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.currencyMenuAutoComplete.setText(items[1], false)
//        binding.currencyMenuAutoComplete.doAfterTextChanged {  }
//        binding.amountTextField.setOnClickListener()
//        {
//            if (binding.amountTextField.hasFocus())
//            {
//                binding.amountEditText.hint = R.string.amount_default.toString()
//            }
//            else
//            {
//                binding.amountEditText.hint = ""
//            }
//        }
//        binding.rateTextField.setOnFocusChangeListener()
//        {
//            if (binding.rateEditText.isPressed)
//            {
//                binding.rateEditText.hint = R.string.rate_default.toString()
//            }
//        }

        binding.addGST.setOnClickListener()
        {
            val stringAmount = binding.amountEditText.text.toString()
            val amount = stringAmount.toDoubleOrNull()
            val stringRate = binding.rateEditText.text.toString()
            val rate = stringRate.toDoubleOrNull()
            if (amount == null && rate==null)
            {
                errorMsg(2)
                return@setOnClickListener
            }
            else if (amount==null)
            {
                errorMsg(0)
                return@setOnClickListener
            }
            else if (rate==null)
            {
                errorMsg(1)
                return@setOnClickListener
            }
            binding.amountTextField.error = null
            binding.rateTextField.error = null
            val gst = (amount*rate)/100
            binding.netAmountEditText.setText(NumberFormat.getCurrencyInstance().format(amount))
            binding.netAmountTextField.isHintEnabled = false
            binding.GSTAmountEditText.setText(NumberFormat.getCurrencyInstance().format(gst))
            binding.GSTAmountTextField.isHintEnabled = false
            binding.totalAmountEditText.setText(NumberFormat.getCurrencyInstance().format(amount+gst))
            binding.totalAmountTextField.isHintEnabled = false
        }
        binding.removeGST.setOnClickListener()
        {
            val stringAmount = binding.amountEditText.text.toString()
            val amount = stringAmount.toDoubleOrNull()
            val stringRate = binding.rateEditText.text.toString()
            val rate = stringRate.toDoubleOrNull()
            if (amount == null && rate==null)
            {
                errorMsg(2)
                return@setOnClickListener
            }
            else if (amount==null)
            {
                errorMsg(0)
                return@setOnClickListener
            }
            else if (rate==null)
            {
                errorMsg(1)
                return@setOnClickListener
            }
            binding.amountTextField.error = null
            binding.rateTextField.error = null
            val total = (amount*100)/(rate+100)
            binding.netAmountEditText.setText(NumberFormat.getCurrencyInstance().format(amount))
            binding.netAmountTextField.isHintEnabled = false
            binding.GSTAmountEditText.setText(NumberFormat.getCurrencyInstance().format(amount-total))
            binding.GSTAmountTextField.isHintEnabled = false
            binding.totalAmountEditText.setText(NumberFormat.getCurrencyInstance().format(total))
            binding.totalAmountTextField.isHintEnabled = false
        }
        binding.amountEditText.setOnKeyListener{view, keyCode, _->handleKeyEvent(view, keyCode)}
        binding.rateEditText.setOnKeyListener{view, keyCode, _->handleKeyEvent(view, keyCode)}

    }

    private fun errorMsg(flag: Int) {
        binding.netAmountEditText.text = null
        binding.netAmountTextField.hint = getString(R.string.amount_default)
        binding.GSTAmountEditText.text = null
        binding.GSTAmountTextField.hint = getString(R.string.amount_default)
        binding.totalAmountEditText.text = null
        binding.totalAmountTextField.hint = getString(R.string.amount_default)
        if (flag == 2) {
            binding.amountTextField.error = getString(R.string.amount_error)
            binding.rateTextField.error = getString(R.string.rate_error)
        }
        else if (flag == 1)
        {
            binding.rateTextField.error = getString(R.string.rate_error)
            binding.amountTextField.error = null
        }
        else if(flag==0)
        {
            binding.amountTextField.error = getString(R.string.amount_error)
            binding.rateTextField.error = null
        }

    }

    private fun handleKeyEvent(view: View, keyCode: Int):Boolean
    {
        if (keyCode== KeyEvent.KEYCODE_ENTER)
        {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}
