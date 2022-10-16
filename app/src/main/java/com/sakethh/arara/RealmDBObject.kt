package com.sakethh.arara

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmDBObject : RealmObject {
    var imgURL: String = ""
    var title: String = ""
    var author: String = ""
    var bookMarked: Boolean = false

    @PrimaryKey
    var objectKey: String = ""
}