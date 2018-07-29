package yanbin.com.textpicker.presentation

import android.arch.lifecycle.Observer
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
import org.koin.android.architecture.ext.getViewModel
import org.koin.android.ext.android.setProperty
import yanbin.com.textpicker.INJECT_KEY_CONTEXT
import yanbin.com.textpicker.R

class MainActivity : AppCompatActivity() {

    private lateinit var fontAdapter: FontAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectProperties()
        setupRecycleView()
        bindViewModel()
    }

    private fun bindViewModel(){
        val viewModel = getViewModel<FontPickerViewModel>()
        fontAdapter.onItemClicked = {
            viewModel.onFontSelected(it)
        }
        viewModel.fonts.observe(this, Observer {
            fontAdapter.fonts = it!!
            fontAdapter.notifyDataSetChanged()
        })
        viewModel.selectedFont.observe(this, Observer {
            setFont(it!!)
        })
    }

    private fun setupRecycleView(){
        fontAdapter = FontAdapter()
        recycleFonts.adapter = fontAdapter
        recycleFonts.layoutManager = LinearLayoutManager(this)
        recycleFonts.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
    }

    private fun setFont(name: String) {
        //TODO check document about handler
        val handler = Handler()

        val fontRequest = FontRequest("com.google.android.gms.fonts",
                "com.google.android.gms",
                "name=$name",
                R.array.com_google_android_gms_fonts_certs)

        val fontCallback = object : FontsContractCompat.FontRequestCallback() {
            override fun onTypefaceRetrieved(typeface: Typeface?) {
                txtSample.typeface = typeface
            }
        }

        FontsContractCompat.requestFont(this, fontRequest, fontCallback, handler)
    }

    private fun injectProperties() {
        setProperty(INJECT_KEY_CONTEXT, this)
    }
}
