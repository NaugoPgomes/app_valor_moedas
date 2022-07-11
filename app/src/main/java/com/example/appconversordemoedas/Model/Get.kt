package com.example.appconversordemoedas.Model



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

    @GET("/gh/fawazahmed0/currency-api@1/latest/currencies/{from}/{to}.json")
    fun getValorDollar(@Path(value = "from") from : String,
                          @Path(value = "to") to : String) : Call<JsonObject>

    @GET("/gh/fawazahmed0/currency-api@1/latest/currencies/{from}/{to}.json")
    fun getValorEuro(@Path(value = "from") from : String,
                       @Path(value = "to") to : String) : Call<JsonObject>

    @GET("/gh/fawazahmed0/currency-api@1/latest/currencies/{from}/{to}.json")
    fun getValorBitcoin(@Path(value = "from") from : String,
                       @Path(value = "to") to : String) : Call<JsonObject>
}