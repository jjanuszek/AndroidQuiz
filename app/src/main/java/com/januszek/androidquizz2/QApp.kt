package com.januszek.androidquizz2

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.beardedhen.androidbootstrap.TypefaceProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class QApp : Application() {

    override fun onCreate() {
        super.onCreate()
        TypefaceProvider.registerDefaultIconSets()

        res = resources
        ctx = applicationContext

        fData = FirebaseDatabase.getInstance()
        fAuth = FirebaseAuth.getInstance()

        fUser = fAuth.currentUser
    }

    companion object {
        lateinit var ctx: Context
        lateinit var res: Resources

        lateinit var fData: FirebaseDatabase
        lateinit var fAuth: FirebaseAuth

        var fUser: FirebaseUser? = null
    }
}