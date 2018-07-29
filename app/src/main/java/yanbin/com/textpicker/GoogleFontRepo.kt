package yanbin.com.textpicker

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.beust.klaxon.JsonReader
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import yanbin.com.textpicker.network.ApiService
import java.io.StringReader

private const val URL_GOOGLE_FONTS = "https://www.googleapis.com/webfonts/v1/webfonts?key="

class GoogleFontRepo(private val context: Context, private val apiService: ApiService) : FontRepo {

    private val mutableFonts = MutableLiveData<List<String>>()
    private val _fonts = mutableListOf<String>()

    override fun getAllFonts(): MutableLiveData<List<String>> {
        launch(CommonPool) { startGetFonts() }
        return mutableFonts
    }

    private fun startGetFonts(){
        val url = URL_GOOGLE_FONTS + context.getString(R.string.KEY_FONT)
        val fontResponse = apiService.call(url)

        //TODO needs to handle null
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
                    while (reader.hasNext()){
                        val readName = reader.nextName()
                        when(readName){
                            "family" -> _fonts.add(reader.nextString())
                            "kind" -> reader.nextString()
                            "category" -> reader.nextString()
                            "variants" -> reader.nextArray()
                            "subsets" -> reader.nextArray()
                            "version" -> reader.nextString()
                            "lastModified" -> reader.nextString()
                            "files" -> reader.nextObject()
                            //TODO
                            else -> RuntimeException()
                        }
                    }
                }
                if(_fonts.size == 50 || _fonts.size % 100 == 0){
                    Log.i("testt", "postValue")
                    mutableFonts.postValue(_fonts)
                }
            }

            mutableFonts.postValue(_fonts)
        }
    }

}