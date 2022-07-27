package com.example.testjazenet.provider

import java.io.Serializable

data class MainResponse<T>(
    var success: String?,
    var result: T?,
    var successMessage: String?,
    var errorMessage: Any?,
) : Serializable

data class ContactDetailsProvider(
    var company_contact_list: List<CompanyContactList>?,
) : Serializable


data class CompanyContactList(
    var account_id: String?,
    var account_type: String?,
    var name: String?,
    var logo: String?,
    var category: String?,
    var location: String?,
    var category_name: String?,
)


data class ContactListRequest(
    var api_token: String,
    var category: String?,
    var account_id: String?,
    var city: String?

)

data class ContactAdapterModel(
    var type: String,
    var letter: String? = null,
    var item: CompanyContactList? = null

)