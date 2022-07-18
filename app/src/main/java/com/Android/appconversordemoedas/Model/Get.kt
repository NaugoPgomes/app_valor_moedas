package com.Android.appconversordemoedas.Model



import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Get
{

    @GET("/gh/fawazahmed0/currency-api@1/latest/currencies.json")
    fun getTiposDeMoedas() : Call<JsonObject>

    @GET("/gh/fawazahmed0/currency-api@1/latest/currencies/{from}/{to}.json")
    fun getValorDasMoedas(@Path(value = "from") from : String,
                          @Path(value = "to") to : String) : Call<JsonObject>

    @GET("/gh/fawazahmed0/currency-api@1/latest/currencies/usd/brl.json")
    fun getValorDollar() : Call<JsonObject>

    @GET("/gh/fawazahmed0/currency-api@1/latest/currencies/eur/brl.json")
    fun getValorEuro() : Call<JsonObject>

    @GET("/gh/fawazahmed0/currency-api@1/latest/currencies/btc/brl.json")
    fun getValorBitcoin() : Call<JsonObject>
}