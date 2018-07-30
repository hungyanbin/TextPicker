package yanbin.com.textpicker.presentation

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_font.view.*
import yanbin.com.textpicker.R
import yanbin.com.textpicker.TypeFaceHelper
import yanbin.com.textpicker.domain.FontFamily

class FontAdapter(private val typeFaceHelper: TypeFaceHelper) : ListAdapter<FontFamily, FontViewHolder>(DIFF_CALLBACK) {

    var onItemClicked: (String) -> Unit = {}
    private var selectedIndex: Int = 0
    private var lastSelectView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FontViewHolder {
        return FontViewHolder(inflateView(R.layout.item_font, parent))
    }

    override fun onBindViewHolder(holder: FontViewHolder, position: Int) {
        val item = getItem(position)
        with(holder) {
            holder.txtName.text = item.family
            if (selectedIndex == position) {
                holder.imageCheck.visibility = View.VISIBLE
                lastSelectView = holder.imageCheck
            } else {
                holder.imageCheck.visibility = View.INVISIBLE
            }

            holder.itemView.setOnClickListener {
                onItemClicked(item.family)
                lastSelectView?.visibility = View.INVISIBLE
                selectedIndex = holder.adapterPosition
                holder.imageCheck.visibility = View.VISIBLE
                lastSelectView = holder.imageCheck
            }

            typeFaceHelper.setTypeFace(holder.txtName, item.family)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FontFamily>(){
            override fun areItemsTheSame(oldItem: FontFamily, newItem: FontFamily): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FontFamily, newItem: FontFamily): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class FontViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txtName = itemView.txtName!!
    val imageCheck = itemView.imageCheck!!
}

fun inflateView(resource: Int, parent: ViewGroup): View {
    val inflater = LayoutInflater.from(parent.context)
    return inflater.inflate(resource, parent, false)
}
