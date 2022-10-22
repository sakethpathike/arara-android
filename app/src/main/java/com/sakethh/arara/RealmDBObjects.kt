package com.sakethh.arara

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class BookMarksDB : RealmObject {
    var imgURL: String = ""
    var title: String = ""
    var author: String = ""
    var bookMarked: Boolean = false
    var permalink :String = ""

    @PrimaryKey
    var objectKey: String = ""
}