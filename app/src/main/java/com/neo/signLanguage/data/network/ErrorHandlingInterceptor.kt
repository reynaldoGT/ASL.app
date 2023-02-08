package com.neo.signLanguage.data.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException

class ErrorHandlingInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val response = try {
      chain.proceed(request)
    } catch (e: IOException) {
      throw e
    }
    if (!response.isSuccessful) {
      val code = response.code()
      when (code) {
        504 -> {
          // handle the timeout error
          throw SocketTimeoutException("Read timed out")
        }
        else -> {
          // handle other errors
        }
      }
    }
    return response
  }
}
