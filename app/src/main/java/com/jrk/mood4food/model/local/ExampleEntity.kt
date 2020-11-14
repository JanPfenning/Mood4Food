package com.jrk.mood4food.model.local

import android.content.Context
import java.util.*

// Fill out the super Constructor correctly
// Please set the Param 'hasEntitySet' to true if there may be more than one Entities of this class
class ExampleEntity(context: Context) : LocalEntity(context, ExampleEntity::class.java, true) {
    // Attributes have to be protected
    // There are just a few possible DataTypes (See: LocalEntity.validDataTypes)
    var exampleString: String? = null
    var exampleInt = 0
    var isExampleBoolean = false
    var exampleLong: Long = 0
    var exampleFloat = 0f
    var exampleStringSet: Set<String>? = null

    // Attributes which can't / must not be saved in LocalStorage are marked with the Annotation '@IgnoreInStorage'
    @IgnoreInStorage
    var exampleDate: Date? = null

    @IgnoreInStorage
    var exampleIntList: List<Int>? = null

}