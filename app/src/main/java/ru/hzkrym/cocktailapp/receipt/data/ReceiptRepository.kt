package ru.hzkrym.cocktailapp.receipt.data

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

class ReceiptRepository {
    private val client = OkHttpClient()

    suspend fun searchReceipt(id: String): ReceiptModel? {
        val request = Request.Builder()
            .url(RECEIPT_URL + id)
            .build()
        try {
            val response = client.newCall(request).execute()
            if (response.body != null) {
                val json = JSONObject(response.body!!.string())
                val list = json.get("drinks")
                if (list is JSONArray) {
                    val item = list.getJSONObject(0)
                    val ingredients = parceIngredients(item)
                    return ReceiptModel(
                        item.getString("strDrink"),
                        item.getString("strInstructions"),
                        item.getString("strDrinkThumb"),
                        ingredients
                    )
                }
            }
        } catch (ex: Exception) {
            Log.e("OK_HTTP", ex.message ?: "")
            ex.printStackTrace()
        }
        return null
    }

    private fun parceIngredients(model: JSONObject): List<IngredientModel> {
        val result = mutableListOf<IngredientModel>()
        (1..15).forEach {
            if (model.isNull("strIngredient${it}"))
                return@forEach
            val name = model.getString("strIngredient${it}")
            val measure = if (model.isNull("strMeasure${it}")) "" else model.getString("strMeasure${it}")
            result.add(
                IngredientModel(
                    name,
                    measure
                )
            )
        }
        return result
    }

    companion object {
        private const val RECEIPT_URL = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i="
    }
}