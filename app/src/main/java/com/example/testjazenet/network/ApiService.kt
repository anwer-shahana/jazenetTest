package com.example.testjazenet.network

import com.example.testjazenet.provider.CompanyContactList
import com.example.testjazenet.provider.ContactDetailsProvider
import com.example.testjazenet.provider.ContactListRequest
import com.example.testjazenet.provider.MainResponse
import com.example.testjazenet.utils.Constants
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://beonchat.com:8080/bizcom/"

fun getClient(): Retrofit {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val httpClient = OkHttpClient.Builder()
    httpClient.connectTimeout(Constants.TIME_OUT.toLong(), TimeUnit.SECONDS)
    httpClient.callTimeout(Constants.TIME_OUT.toLong(), TimeUnit.SECONDS)
    httpClient.readTimeout(Constants.TIME_OUT.toLong(), TimeUnit.SECONDS)
    httpClient.addInterceptor(interceptor)
    httpClient.addInterceptor { chain ->
        // val token: String = SessionManager.getUserToken()
        // Timber.d("token--> $token")
        val original = chain.request()
        val request = original.newBuilder()
            .method(original.method, original.body)
            // .addHeader("Authorization", "Bearer $token")
//            .addHeader("Content-Type", "application/json")
            .addHeader("User-Agent", "android")
            .addHeader("Accept", "application/json")
            .build()
        val response = chain.proceed(request)
        val rawJson = response.body?.string()
        response.newBuilder().body(rawJson?.toResponseBody(response.body?.contentType())).build()
    }

    val gson = GsonBuilder()
        .setLenient()
        .create()
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(httpClient.build())
        .baseUrl(BASE_URL)
        .build()
}

interface ApiService {

    /**
     * Product Methods
     */

    //Get Contact list
    @POST("contact_list_demo")
    fun getContactListAsync(
        @Body contactListRequest: ContactListRequest
    ): Deferred<Response<MainResponse<ContactDetailsProvider>>>


}

object RetroApi {
    val retrofitService: ApiService by lazy {
        getClient().create(ApiService::class.java)
    }
}


