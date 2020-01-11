package com.fib.agenda

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_contato.*
import java.text.SimpleDateFormat
import java.util.*

class ContatoActivity : AppCompatActivity() {

    var cal = Calendar.getInstance()
    var datanascimento: Button? = null

    private var imgContato: ImageView? = null
    private var txtNome: EditText? = null
    private var txtEndereco: EditText? = null
    private var txtTelefone: EditText? = null
    private var txtSite: EditText? = null
    private var btnCadastro: Button? = null
    private var txtEmail: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)

        val myChildToolbar = toolbar_child
        setSupportActionBar(myChildToolbar)
        // Get a support ActionBar corresponding to this toolbar
        val ab = supportActionBar
        // Enable the Up button
        ab!!.setDisplayHomeAsUpEnabled(true)

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        datanascimento = txtDatanascimento
        datanascimento!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@ContatoActivity,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

        imgContato = imgContato
        txtNome = txtNome
        txtEndereco = txtEndereco
        txtTelefone = txtTelefone
        txtSite = txtSite
        txtEmail = txtEmail
        btnCadastro = btnCadastro
    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        datanascimento!!.text = sdf.format(cal.getTime())
    }
}
