package com.Android.appconversordemoedas

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.Android.appconversordemoedas.Model.Get
import com.Android.appconversordemoedas.Model.RetrofitInstance
import com.Android.appconversordemoedas.R
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EuroActivity :AppCompatActivity(), View.OnClickListener {
    private lateinit var resultado: TextView
    private lateinit var preco: TextView
    private lateinit var valor: EditText
    private lateinit var botao: Button
    private lateinit var voltar: ImageView
    private lateinit var avancar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_euro)

        getSupportActionBar()?.hide()

        resultado = findViewById(R.id.resultado)
        valor = findViewById(R.id.valor)
        botao = findViewById(R.id.button)
        voltar = findViewById(R.id.voltar)
        avancar = findViewById(R.id.avancar)
        preco = findViewById(R.id.textoValorAtual)

        setListeners()
        getValor()
    }

    override fun onClick(v: View?) {
        val id = v?.id
        if (id == R.id.button)
        {
            if(verificaNullo())
            {
                valor.setError("O valor não pode estar vazio")

            }
            else
            {
                converterEuro()
            }

        } else if (id == R.id.avancar)
        {
            val intente = Intent(this, BitcoinActivity::class.java)
            startActivity(intente)
            finish()
        } else if (id == R.id.voltar)
        {
            val intente = Intent(this, DollarActivity::class.java)
            startActivity(intente)
            finish()
        }
    }

    private fun setListeners()
    {
        botao.setOnClickListener(this)
        avancar.setOnClickListener(this)
        voltar.setOnClickListener(this)
    }

    private fun verificaNullo():Boolean
    {
        var valorVerifica: String = valor.text.toString()

        if(TextUtils.isEmpty(valorVerifica))
        {
            return true
        }
        return false
    }


    fun converterEuro()
    {

        val progressDialog = ProgressDialog(this@EuroActivity)
        progressDialog.setMessage("Carregando...")
        progressDialog.show()


        val Client = RetrofitInstance.getInstancia("https://cdn.jsdelivr.net/")
        val endpoint = Client.create(Get::class.java)

        val BRL = "brl"
        val EUR = "eur"


        endpoint.getValorDasMoedas(BRL, EUR).enqueue(object :
            Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                progressDialog.dismiss()

                var dados = response.body()?.entrySet()?.find { it.key == EUR }
                val rate: Double = dados?.value.toString().toDouble()
                val conversion = valor.text.toString().toDouble() * rate

                resultado.setText("%.4f".format(conversion))
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                println("Falhou")
            }

        })

    }

    private fun getValor()
    {

        val Client = RetrofitInstance.getInstancia("https://cdn.jsdelivr.net/")
        val endpoint = Client.create(Get::class.java)

        val BRL = "brl"

        endpoint.getValorEuro().enqueue(object :
            Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                var dados = response.body()?.entrySet()?.find { it.key == BRL }
                val rate: Double = dados?.value.toString().toDouble()

                preco.text = "O valor atual é de : R$ ${"%.4f".format(rate)}"
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                println("Falhou")
            }

        })

    }
}