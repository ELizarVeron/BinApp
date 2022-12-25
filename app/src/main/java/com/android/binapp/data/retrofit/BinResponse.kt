package com.android.binapp.data.retrofit






class BinResponse {
    var number: Number? = null
    var scheme: String? = null
    var type: String? = null
    var brand: String? = null
    var prepaid = false
    var country: Country? = null
    var bank: Bank? = null
}

class Number {
    var length = 0
    var luhn = false
}

class Country {
    var numeric: String? = null
    var alpha2: String? = null
    var name: String? = null
    var emoji: String? = null
    var currency: String? = null
    var latitude = 0
    var longitude = 0
}

class Bank {
    var name: String? = null
    var url: String? = null
    var phone: String? = null
    var city: String? = null
}

class Root {

}