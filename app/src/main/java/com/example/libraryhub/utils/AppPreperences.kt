package com.example.libraryhub.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.libraryhub.model.User
import com.google.gson.Gson

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

    var user: User?
        get() {
            val gson = Gson()
            val json: String? = preferences.getString("user", "");
            return gson.fromJson(json, User::class.java);
        }
        set(value) = preferences.edit {
            val gson = Gson()
            val json = gson.toJson(value)
            it.putString("user", json)
        }
}