package com.android.binapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "bin_info")
data class BinInfoEntity(
    var number:Int=0,
    var number_length:Int,
    var number_luhn : Boolean,
    var scheme:String,
    var type:String,
    var brand:String,
    var prepaid:Boolean,
    var country_numeric:String,
    var country_alpha2:String,
    var country_name:String,
    var country_emoji:String,
    var country_currency:String,
    var country_latitude:Int,
    var country_longitude:Int,
    var bank_name:String,
    var bank_url:String,
    var bank_phone:String,
    var bank_city:String,
) {
    @PrimaryKey(autoGenerate = true)
    var id :Int =0
}