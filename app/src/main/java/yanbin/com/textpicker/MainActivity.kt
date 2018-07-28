package yanbin.com.textpicker

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.provider.FontRequest
import android.support.v4.provider.FontsContractCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getFont()
        setupRecycleView()
    }

    private fun setupRecycleView(){
        val fontAdapter = FontAdapter()
        fontAdapter.fonts = listOf("Abc", "cde", "def", "ii", "fawjef", "cwe")
        recycleFonts.adapter = fontAdapter
        recycleFonts.layoutManager = LinearLayoutManager(this)
        recycleFonts.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
    }

    private fun getFont() {
        val handler = Handler()

        val fontRequest = FontRequest("com.google.android.gms.fonts",
                "com.google.android.gms",
                "name=Oswald",
                R.array.com_google_android_gms_fonts_certs)

        val fontCallback = object : FontsContractCompat.FontRequestCallback() {
            override fun onTypefaceRetrieved(typeface: Typeface?) {
                txtSample.typeface = typeface
            }
        }

        FontsContractCompat.requestFont(this, fontRequest, fontCallback, handler)
    }
}
