package com.example.libraryhub.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.libraryhub.model.Book
import com.example.libraryhub.model.CartBook
import com.example.libraryhub.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object AppPreferences {
    private const val NAME = "SpinKotlin"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var JWT: String?
        get() = preferences.getString("jwt", "")
        set(value) = preferences.edit {
            it.putString("jwt", value)
        }

    var cart: ArrayList<CartBook>?
        get() {
            val gson = Gson()
            val json: String? = preferences.getString("cart", "");
            val type: Type = object : TypeToken<ArrayList<CartBook?>?>() {}.type
            return gson.fromJson(json, type);
        }
        set(value) = preferences.edit {
            val gson = Gson()
            val json = gson.toJson(value)
            it.putString("cart", json)
        }
}