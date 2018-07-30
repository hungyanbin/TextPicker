package yanbin.com.textpicker

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Handler
import android.os.HandlerThread
import android.support.v4.provider.FontRequest
import android.support.v4.provider.FontsContractCompat
import android.util.LruCache
import android.widget.TextView

private const val CACHE_SIZE = 20

class TypeFaceHelper(private val context: Context){

    private val requestThread = HandlerThread("fontRequestThread")
    private val typeFaceCache = LruCache<String, Typeface?>(CACHE_SIZE)
    private val handler: Handler

    init {
        requestThread.start()
        handler = Handler(requestThread.looper)
    }

    fun setTypeFace(textView: TextView, font: String, onRetrived: () -> Unit = {}){
        if(typeFaceCache[font] != null){
            textView.typeface = typeFaceCache[font]
            textView.setTextColor(Color.BLACK)
            onRetrived()
            return
        }

        val fontRequest = FontRequest("com.google.android.gms.fonts",
                "com.google.android.gms",
                "name=$font",
                R.array.com_google_android_gms_fonts_certs)

        //TODO lifecycle!!
        val fontCallback = object : FontsContractCompat.FontRequestCallback() {
            override fun onTypefaceRetrieved(typeface: Typeface?) {
                if(textView.text == font){
                    textView.typeface = typeface
                    textView.setTextColor(Color.BLACK)
                    onRetrived()
                }
                typeFaceCache.put(font, typeface)
            }

            override fun onTypefaceRequestFailed(reason: Int) {
                super.onTypefaceRequestFailed(reason)
                if(textView.text == font){
                    textView.typeface = Typeface.DEFAULT
                    textView.setText(R.string.error_font_not_found)
                    textView.setTextColor(Color.RED)
                    onRetrived()
                }
            }
        }

        FontsContractCompat.requestFont(context, fontRequest, fontCallback, handler)
    }
}