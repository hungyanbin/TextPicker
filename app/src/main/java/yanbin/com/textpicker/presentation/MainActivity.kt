package yanbin.com.textpicker.presentation

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.architecture.ext.getViewModel
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.setProperty
import yanbin.com.textpicker.INJECT_KEY_CONTEXT
import yanbin.com.textpicker.R
import yanbin.com.textpicker.TypeFaceHelper

class MainActivity : AppCompatActivity() {

    private lateinit var fontAdapter: FontAdapter
    private val typeFaceHelper: TypeFaceHelper by inject()

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
            fontAdapter.submitList(it!!)
            fontAdapter.notifyDataSetChanged()
        })
        viewModel.selectedFont.observe(this, Observer {
            typeFaceHelper.setTypeFace(txtSample, it!!)
        })
    }

    private fun setupRecycleView(){
        fontAdapter = FontAdapter(typeFaceHelper)
        recycleFonts.adapter = fontAdapter
        recycleFonts.layoutManager = LinearLayoutManager(this)
        recycleFonts.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
    }

    private fun injectProperties() {
        setProperty(INJECT_KEY_CONTEXT, this)
    }
}
