package yanbin.com.textpicker.presentation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_font.view.*
import yanbin.com.textpicker.R
import yanbin.com.textpicker.TypeFaceHelper
import yanbin.com.textpicker.domain.FontFamily

class FontAdapter(private val typeFaceHelper: TypeFaceHelper) : RecyclerView.Adapter<FontViewHolder>() {

    var items = listOf<FontFamily>()
    var onItemClicked: (String) -> Unit = {}
    private var selectedFont: String = ""
    private var lastSelectView: View? = null

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FontViewHolder {
        return FontViewHolder(inflateView(R.layout.item_font, parent))
    }

    override fun onBindViewHolder(holder: FontViewHolder, position: Int) {
        val item = items[position]
        with(holder) {
            progressFont.visibility = View.VISIBLE
            txtName.visibility = View.INVISIBLE

            txtName.text = item.family
            if (selectedFont == item.family) {
                imageCheck.visibility = View.VISIBLE
                lastSelectView = imageCheck
            } else {
                imageCheck.visibility = View.INVISIBLE
            }

            itemView.setOnClickListener {
                onItemClicked(item.family)
                lastSelectView?.visibility = View.INVISIBLE
                selectedFont = item.family
                imageCheck.visibility = View.VISIBLE
                lastSelectView = imageCheck
            }

            typeFaceHelper.setTypeFace(txtName, item.family, {
                txtName.visibility = View.VISIBLE
                progressFont.visibility = View.INVISIBLE
            })
        }
    }
}

class FontViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txtName = itemView.txtName!!
    val imageCheck = itemView.imageCheck!!
    val progressFont = itemView.progressFont!!
}

fun inflateView(resource: Int, parent: ViewGroup): View {
    val inflater = LayoutInflater.from(parent.context)
    return inflater.inflate(resource, parent, false)
}
