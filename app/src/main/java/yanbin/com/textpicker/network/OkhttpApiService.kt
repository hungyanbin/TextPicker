package yanbin.com.textpicker.network

import okhttp3.OkHttpClient
import okhttp3.Request

class OkhttpApiService: ApiService{

    private val client = OkHttpClient()

    override fun call(url: String): String {
        //TODO needs to handle error
        val request = Request.Builder()
                .url(url)
                .build()

        val response = client.newCall(request)
                .execute()
        return response.body()!!.string()
    }
}