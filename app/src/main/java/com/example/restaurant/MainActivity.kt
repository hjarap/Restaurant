package com.example.restaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.Switch
import android.text.TextWatcher
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var etCant1: EditText? = null
    private var etCant2: EditText? = null
    private var etTotal1: EditText? = null
    private var etTotal2: EditText? = null
    private var etPrecioComida: EditText? = null
    private var etPropina: EditText? = null
    private var etTotalComida: EditText? = null
    private var switchPropinaTotal: Switch? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCant1 = findViewById<EditText>(R.id.etCant1)
        etCant2 = findViewById<EditText>(R.id.etCant2)
        etTotal1 = findViewById<EditText>(R.id.etTotal1)
        etTotal2 = findViewById<EditText>(R.id.etTotal2)
        etPrecioComida = findViewById<EditText>(R.id.etPrecioComida)
        etPropina = findViewById<EditText>(R.id.etPropina)
        etTotalComida = findViewById<EditText>(R.id.etTotalComida)
        switchPropinaTotal = findViewById<Switch>(R.id.switchPropinaTotal)

        etCant1?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                calcularTotal()
                calcularPropina()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        etCant2?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                calcularTotal()
                calcularPropina()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        switchPropinaTotal?.setOnCheckedChangeListener { _, isChecked ->
            calcularPropina()
        }
    }

    private fun calcularTotal() {
        val cant1 = etCant1?.text.toString().takeIf { it.isNotEmpty() }?.toIntOrNull()
        val cant2 = etCant2?.text.toString().takeIf { it.isNotEmpty() }?.toIntOrNull()

        if (cant1 != null && cant2 != null) {
            val total1 = cant1 * 12000
            val total2 = cant2 * 10000

            etTotal1?.setText(formatoMoneda(total1))
            etTotal2?.setText(formatoMoneda(total2))
            val precioTotal = total1 + total2
            etPrecioComida?.setText(formatoMoneda(precioTotal))

            calcularPropina()
        }
    }

    private fun calcularPropina() {
        val precioComida = etPrecioComida?.text.toString().replace("$", "").replace(".", "").toIntOrNull()

        if (switchPropinaTotal?.isChecked == true && precioComida != null) {
            val propina = precioComida * 0.1
            etPropina?.setText(formatoMoneda(propina.toInt()))

            val total = precioComida + propina.toInt()
            etTotalComida?.setText(formatoMoneda(total))
        } else {
            etPropina?.setText(formatoMoneda(0))
            etTotalComida?.text = etPrecioComida?.text
        }
    }
    private fun formatoMoneda(valor: Int): String {
        val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
        format.currency = Currency.getInstance("CLP")
        return format.format(valor).replace("CLP", "$")
    }
}

