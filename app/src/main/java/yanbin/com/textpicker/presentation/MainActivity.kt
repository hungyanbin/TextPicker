package yanbin.com.textpicker.presentation

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.architecture.ext.getViewModel
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.setProperty
import yanbin.com.textpicker.INJECT_KEY_CONTEXT
import yanbin.com.textpicker.R
import yanbin.com.textpicker.TypeFaceHelper

class MainActivity : AppCompatActivity() {

    private lateinit var fontAdapter: FontAdapter
    private lateinit var viewModel: FontPickerViewModel
    private val typeFaceHelper: TypeFaceHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectProperties()
        setupRecycleView()
        bindViewModel()
    }

    private fun bindViewModel(){
        viewModel = getViewModel()
        fontAdapter.onItemClicked = {
            viewModel.onFontSelected(it)
        }
        viewModel.fonts.observe(this, Observer {
            fontAdapter.items = it!!
            fontAdapter.notifyDataSetChanged()
        })
        viewModel.selectedFont.observe(this, Observer {
            typeFaceHelper.setTypeFace(txtSample, it!!)
        })

        val rootView = findViewById<View>(android.R.id.content)
        viewModel.errorMessage.observe(this, Observer {
            Snackbar.make(rootView, it!!, Snackbar.LENGTH_LONG).show()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_rest_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sort_by_family -> viewModel.sortFontByFamily()
            R.id.menu_sort_by_lastModified -> viewModel.sortFontByLastModified()
        }
        return super.onOptionsItemSelected(item)
    }
}
