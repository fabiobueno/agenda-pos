package com.fib.agenda
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import kotlinx.android.synthetic.main.activity_contato.*
import java.text.SimpleDateFormat
import java.util.*
import android.provider.MediaStore
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import com.fib.agenda.db.Contato
import com.fib.agenda.db.ContatoRepository
import java.io.*

class ContatoActivity : AppCompatActivity() {

    private var cal = Calendar.getInstance()
    private var datanascimento: Button? = null
    private var contato: Contato? = null

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

        btnCadastro?.setOnClickListener {
            contato?.nome = txtNome?.text.toString()
            contato?.endereco = txtEndereco?.text.toString()
            contato?.telefone = txtTelefone?.text.toString().toLong()
            contato?.dataNascimento = cal.timeInMillis
            contato?.email = txtEmail?.text.toString()
            contato?.site = txtSite?.text.toString()

            if(contato?.id == 0.toLong()){
                ContatoRepository(this).create(contato!!)
            }else{
                ContatoRepository(this).update(contato!!)
            }
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val dateFormatter = SimpleDateFormat(myFormat, Locale.US)

        val intent = intent
        if(intent != null){
            if(intent.getSerializableExtra("contato") != null){
                contato = intent.getSerializableExtra("contato") as Contato
                txtNome?.setText(contato?.nome)
                txtEndereco?.setText(contato?.endereco)
                txtTelefone.setText(contato?.telefone.toString())

                if (contato?.dataNascimento != null) {
                    datanascimento?.setText(dateFormatter?.format(Date(contato?.dataNascimento!!)))
                }else{
                    datanascimento?.setText(dateFormatter?.format(Date()))
                }

                txtEmail.setText(contato?.email)
                txtSite?.setText(contato?.site)
            }else{
                contato = Contato()
            }
        }
    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        datanascimento!!.text = sdf.format(cal.getTime())
    }

}