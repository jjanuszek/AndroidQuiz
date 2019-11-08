package com.januszek.androidquizz2

import com.google.firebase.auth.FirebaseUser
import com.januszek.androidquizz2.profile.UserItem

fun FirebaseUser.toUserItem(): UserItem {
    return UserItem().apply {
        uid = this@toUserItem.uid
        url = this@toUserItem.photoUrl.toString()
        name = this@toUserItem.displayName!!
        System.out.println(uid+"  "+ url + "  " + name + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
    }
}