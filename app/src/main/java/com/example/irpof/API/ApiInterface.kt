package com.example.irpof.API

import com.example.irpof.DataClass.PdfDetail
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("getfile")
    fun getPdf(@Body folderPath: String): Call<PdfDetail>
}