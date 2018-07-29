package yanbin.com.textpicker

import android.content.Context
import android.graphics.Typeface
import android.os.Handler
import android.os.HandlerThread
import android.support.v4.provider.FontRequest
import android.support.v4.provider.FontsContractCompat
import android.widget.TextView

class TypeFaceHelper(private val context: Context){

    private val requestThread = HandlerThread("fontRequestThread")
    private val handler: Handler

    init {
        requestThread.start()
        handler = Handler(requestThread.looper)
    }

    fun setTypeFace(textView: TextView, font: String){
        val fontRequest = FontRequest("com.google.android.gms.fonts",
                "com.google.android.gms",
                "name=$font",
                R.array.com_google_android_gms_fonts_certs)

        //TODO lifecycle!!
        val fontCallback = object : FontsContractCompat.FontRequestCallback() {
            override fun onTypefaceRetrieved(typeface: Typeface?) {
                textView.typeface = typeface
            }
        }

        FontsContractCompat.requestFont(context, fontRequest, fontCallback, handler)
    }
}