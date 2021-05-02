package com.twentytwo.notebox.Activities.SecurePages

class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val mobile: String = "",
    val password: String = "",
    val date_created : String ="",
    val gender: String = "",
    val termsncd: Boolean
)
class bdaydata(
    val id: String = "",
    val names: String = "",
    val date:String="",
    val remind:Boolean,
    val created:String=""
)

data class datacontacts(
    val id: String = "",
    val name: String = "",
    val email:String="",
    val mobile: String="",
    val address: String="",
    val created:String=""
)