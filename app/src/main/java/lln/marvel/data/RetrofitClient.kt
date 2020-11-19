package lln.marvel.data

import lln.marvel.util.MarvelConstants
import lln.marvel.util.MarvelConstants.API.API_BASE_URL
import lln.marvel.util.md5
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    private var servicesApiInterface: API? = null

    fun build(): API? {
        var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(getOkHttpClient())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

        var retrofit: Retrofit = builder.client(getOkHttpClient()).build()

        servicesApiInterface = retrofit.create(
            API::class.java
        )

        return servicesApiInterface as API
    }

    private fun getOkHttpClient() = OkHttpClient().newBuilder()
        .addInterceptor { chain ->
            val currentTimestamp = System.currentTimeMillis()
            val newUrl = chain.request().url
                .newBuilder()
                .addQueryParameter(MarvelConstants.PARAMS.TS, currentTimestamp.toString())
                .addQueryParameter(MarvelConstants.PARAMS.API_KEY, MarvelConstants.KEYS.PUBLIC)
                .addQueryParameter(
                    MarvelConstants.PARAMS.HASH,
                    (currentTimestamp.toString()
                            + MarvelConstants.KEYS.PRIVATE
                            + MarvelConstants.KEYS.PUBLIC).md5
                )
                .build()

            val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(newRequest)
        }
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .writeTimeout(60L, TimeUnit.SECONDS)
        .build()
}