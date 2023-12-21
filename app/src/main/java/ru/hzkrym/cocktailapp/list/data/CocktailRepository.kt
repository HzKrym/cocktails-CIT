package ru.hzkrym.cocktailapp.list.data

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import ru.hzkrym.cocktailapp.list.presentation.CocktailViewModel

class CocktailRepository {
    private val client = OkHttpClient()

    suspend fun searchCocktail(searchString: String): List<CocktailModel>? {
        val request = Request.Builder()
            .url(SEARCH_URL + searchString)
            .build()
        try {
            val response = client.newCall(request).execute()
            if (response.body != null) {
                val json = JSONObject(response.body!!.string())
                val list = json.get("drinks")
                val resultList = mutableListOf<CocktailModel>()
                if (list is JSONArray) {
                    (0 until list.length()).forEach {
                        val item = list.getJSONObject(it)
                        resultList.add(
                            CocktailModel(
                                item.getString("idDrink"),
                                item.getString("strDrink")
                            )
                        )
                    }
                }
                return resultList
            }
        } catch (ex: Exception) {
            Log.e("OK_HTTP", ex.message ?: "")
            ex.printStackTrace()
            return null
        }
        return null
    }

    companion object {
        private const val SEARCH_URL = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s="
    }
}