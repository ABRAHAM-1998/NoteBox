package com.twentytwo.notebox.Activities.SecurePages

class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val mobile: String = "",
    val password: String = "",
    val date_created: String = "",
    val gender: String = "",
    val termsncd: Boolean,
    val lkeystatus: Boolean = false,
    val lkey: String = ""
)

class bdaydata(
    val id: String = "",
    val names: String = "",
    val date: String = "",
    val remind: Boolean,
    val created: String = ""

)

data class datacontacts(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val mobile: String = "",
    val address: String = "",
    val created: String = ""
)

data class data_locker(
    val id: String = "",
    val app_name: String = "",
    val usename: String = "",
    val email: String = "",
    val mobile: String = "",
    val password: String = "",
    val created: String = ""
)

data class notes(
    val id: String = "",
    val noteTitle: String = "",
    val noteContent: String = "",
    val created: String = ""
)

data class  certific(
    val id: String = "",
    val imgUrl: String = "",
    val descreption: String = "",
    val filename:String="",
    val created: String = ""
)
data class devUpate(
    val followers: String ="",
    val following: String = "",
    val instalink: String ="",
    val facebooklink: String = "",
    val weblink: String =""
)