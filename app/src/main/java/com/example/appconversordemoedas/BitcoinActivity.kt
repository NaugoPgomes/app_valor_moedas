package com.example.appconversordemoedas

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.appconversordemoedas.Model.Get
import com.example.appconversordemoedas.Model.RetrofitInstance
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BitcoinActivity : AppCompatActivity(), View.OnClickListener
{
    private lateinit var resultado: TextView
    private lateinit var preco: TextView
    private lateinit var valor: EditText
    private lateinit var botao: Button
    private lateinit var voltar: ImageView
    private lateinit var avancar: ImageView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bitcoin)

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

    override fun onClick(v: View?)
    {
        val id = v?.id
        if (id == R.id.button)
        {
            converterBitcoin()
        }
        else if(id == R.id.avancar)
        {
            val intente = Intent(this, DollarActivity::class.java)
            startActivity(intente)
            finish()
        }
        else if(id == R.id.voltar)
        {
            val intente = Intent(this, EuroActivity::class.java)
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


    fun converterBitcoin()
    {

        val progressDialog = ProgressDialog(this@BitcoinActivity)
        progressDialog.setMessage("Carregando...")
        progressDialog.show()

        val Client = RetrofitInstance.getInstancia("https://cdn.jsdelivr.net/")
        val endpoint = Client.create(Get::class.java)

        val BRL = "brl"
        val BTC = "btc"


        endpoint.getValorDasMoedas(BRL, BTC ).enqueue(object :
            Callback<JsonObject>
        {

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>)
            {
                progressDialog.dismiss()

                var dados = response.body()?.entrySet()?.find { it.key == BTC  }
                val rate: Double = dados?.value.toString().toDouble()
                val conversion = valor.text.toString().toDouble() * rate

                resultado.setText("%.4f".format(conversion))// "%.2f".format(conversion) faz exibir somente 2 casas decimais
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable)
            {
                println("Falhou")
            }

        })

    }

    private fun getValor()
    {

        val Client = RetrofitInstance.getInstancia("https://cdn.jsdelivr.net/")
        val endpoint = Client.create(Get::class.java)

        val BRL = "brl"
        val BTC = "btc"

        endpoint.getValorBitcoin(BRL, BTC).enqueue(object :
            Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                var dados = response.body()?.entrySet()?.find { it.key == BTC }
                val rate: Double = dados?.value.toString().toDouble()

                preco.text = "R$ 1 = ฿ ${"%.6f".format(rate)}"
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                println("Falhou")
            }

        })

    }
}