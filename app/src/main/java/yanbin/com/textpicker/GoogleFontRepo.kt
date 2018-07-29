package yanbin.com.textpicker

import android.content.Context
import com.beust.klaxon.Klaxon
import okhttp3.OkHttpClient
import okhttp3.Request

private const val URL_GOOGLE_FONTS = "https://www.googleapis.com/webfonts/v1/webfonts?key="

class GoogleFontRepo(private val context: Context) : FontRepo {

    private val client = OkHttpClient()

    private fun executeRequest(url: String): String {
        //TODO needs to handle error
        val request = Request.Builder()
                .url(url)
                .build()

        val response = client.newCall(request)
                .execute()
        return response.body()!!.string()
    }

    override suspend fun getAllFonts(): List<String> {
        val url = URL_GOOGLE_FONTS + context.getString(R.string.KEY_FONT)
        val fontResponse = executeRequest(url)

        //TODO needs to handle null
        return Klaxon().parse<GoogleFontResponse>(fontResponse)
                .let {
                    it!!.items.map { fontItem ->
                        fontItem.family
                    }
                }
    }
}

class GoogleFontResponse(val items: List<FontItem>)

class FontItem(val family: String)
