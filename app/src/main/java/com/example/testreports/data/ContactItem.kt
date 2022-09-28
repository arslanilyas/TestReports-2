package com.example.testreports.data

import java.io.Serializable

open class ContactItem(

    var customerID:String?="",
    var companyName : String?="",
    var contactName : String?="",
    var contactTitle : String?="",
    var contactAddress : String?="",
    var contactCity : String?="",
    var contactEmail : String?="",
    var contactRegion: String?="",
    var contactPostalCode: String?="",
    var contactCountry: String?="",
    var contactPhone : String?="",
    var contactFax : String?=""


):Serializable{


}