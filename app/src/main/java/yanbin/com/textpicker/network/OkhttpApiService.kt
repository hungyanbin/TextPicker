package yanbin.com.textpicker.network

import okhttp3.OkHttpClient
import okhttp3.Request
import yanbin.com.textpicker.NetworkFailException

class OkhttpApiService: ApiService{

    private val client = OkHttpClient()

    override fun call(url: String): String {
        val request = Request.Builder()
                .url(url)
                .build()

        val response = client.newCall(request)
                .execute()
        if(response.isSuccessful){
            return response.body()!!.string()
        }else{
            throw NetworkFailException("error code : ${response.code()}")
        }
    }
}