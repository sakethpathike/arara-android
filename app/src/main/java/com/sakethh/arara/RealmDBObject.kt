package com.sakethh.arara

import io.realm.kotlin.types.RealmObject

class RealmDBObject : RealmObject {
    var imgURL: String? = null
    var title: String? = null
    var author: String? = null
    var bookMarked : Boolean = false
}