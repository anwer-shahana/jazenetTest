package com.example.testjazenet.utils


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SessionManager {

    companion object {

        lateinit var sharedPref: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
        lateinit var context: Context

        private var PRIVATE_MODE = 0
        private val PREF_NAME = "textJazenet"


        @SuppressLint("CommitPrefEdits")
        fun initializeContext(context: Context) {
            this.context = context
            sharedPref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
            editor = sharedPref.edit()
        }

        fun clear() {
            editor.clear()
            editor.apply()
        }

































    }










}