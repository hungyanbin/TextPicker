package yanbin.com.textpicker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_font.view.*

class FontAdapter: RecyclerView.Adapter<FontViewHolder>(){

    var fonts: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FontViewHolder {
        return FontViewHolder(inflateView(R.layout.item_font, parent))
    }

    override fun getItemCount(): Int {
        return fonts.size
    }

    override fun onBindViewHolder(holder: FontViewHolder, position: Int) {
        val item = fonts[position]
        with(holder){
            holder.txtName.text = item
        }
    }
}

class FontViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    val txtName = itemView.txtName!!
}

fun inflateView(resource: Int, parent: ViewGroup): View{
    val inflater = LayoutInflater.from(parent.context)
    return inflater.inflate(resource, parent, false)
}
