package com.example.child_emotion_app.service.regist

import com.example.child_emotion_app.data.regist.Regist
import com.example.child_emotion_app.data.regist.RegistResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://10.0.2.2:8000"

private val mHttpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY) // BASIC) // check constants

//private val mOkHttpClient = OkHttpClient
//    .Builder()
//    .addInterceptor(mHttpLoggingInterceptor)
//    .build()

private val moshi = Moshi.Builder()//더 편하게 하기 위해서 사용
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
//    .client(mOkHttpClient)    //logger(디버깅용으로 쓰는 것이고 없애도 지장이 없음)
    .build()

interface RegistService {
    @Headers("Content-Type: application/json")

    @POST("/signUp")
    suspend fun sendsMessage(@Body message: Regist): Response<RegistResponse>

}

object RegistApi {
    val retrofitService: RegistService by lazy { retrofit.create(RegistService::class.java) }
}