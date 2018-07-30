package yanbin.com.textpicker

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.beust.klaxon.JsonReader
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import org.json.JSONException
import yanbin.com.textpicker.domain.FontFamily
import yanbin.com.textpicker.network.ApiService
import java.io.IOException
import java.io.StringReader

private const val URL_GOOGLE_FONTS = "https://www.googleapis.com/webfonts/v1/webfonts?key="
private const val PAGE_SIZE = 50

class GoogleFontRepo(private val context: Context, private val apiService: ApiService) : FontRepo {

    private val mutableFonts = MutableLiveData<List<FontFamily>>()
    private val _fonts = mutableListOf<FontFamily>()
    private val _errorMessage = MutableLiveData<String>()

    override fun getErrorMessage(): MutableLiveData<String> {
        return _errorMessage
    }

    override fun getAllFonts(): MutableLiveData<List<FontFamily>> {
        launch(CommonPool) { startGetFonts() }
        return mutableFonts
    }

    private fun startGetFonts(){
        val url = URL_GOOGLE_FONTS + context.getString(R.string.KEY_FONT)
        try {
            val fontResponse = apiService.call(url)
            parseResponse(fontResponse)
        } catch (e: NetworkFailException) {
            _errorMessage.postValue(e.message)
        } catch (e: IOException){
            _errorMessage.postValue(context.getString(R.string.error_no_network))
        } catch (e: Exception){
            _errorMessage.postValue(context.getString(R.string.error_unknown))
        }

    }

    private fun parseResponse(fontResponse: String) {
        JsonReader(StringReader(fontResponse)).use { reader ->
            reader.beginObject {

                while (reader.hasNext()) {
                    val readName = reader.nextName()

                    when (readName) {
                        "items" -> parseItems(reader)
                        else -> {
                            reader.skip(1)
                        }
                    }
                }
            }
        }
    }


    private fun parseItems(reader: JsonReader) {
        reader.beginArray {
            while (reader.hasNext()) {
                reader.beginObject {
                    var family = ""
                    var lastModified = ""
                    while (reader.hasNext()){
                        val readName = reader.nextName()
                        when(readName){
                            "family" -> family = reader.nextString()
                            "kind" -> reader.nextString()
                            "category" -> reader.nextString()
                            "variants" -> reader.nextArray()
                            "subsets" -> reader.nextArray()
                            "version" -> reader.nextString()
                            "lastModified" -> lastModified = reader.nextString()
                            "files" -> reader.nextObject()
                            else -> JSONException("Unknown name: $readName")
                        }
                    }
                    _fonts.add(FontFamily(family, lastModified))
                }
                if(_fonts.size == PAGE_SIZE || _fonts.size % PAGE_SIZE == 0){
                    Log.i("testt", "postValue")
                    mutableFonts.postValue(_fonts)
                }
            }

            mutableFonts.postValue(_fonts)
        }
    }

}