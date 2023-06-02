package ir.sanags.android.image_cropper

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AspectRatioAdapter(
    aspectRatiosStr: String,
    val clickListener: ClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: MutableList<Pair<Int, Int>> = mutableListOf()

    private var lastSelectedItem = -1

    init {
        aspectRatiosStr.split(",").toTypedArray().forEach { pair ->
            val stringPair = pair.split(":").toTypedArray()
            if (stringPair.size == 2) {
                list.add(Pair(stringPair[0].trim().toInt(), stringPair[1].trim().toInt()))
            }
        }

        if (list.isNotEmpty()) {
            lastSelectedItem = 0
            clickListener.onItemClick(list[lastSelectedItem])
        }
    }

    interface ClickListener {
        fun onItemClick(pair: Pair<Int, Int>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.aspect_ratio_item, parent, false)
        return AspectRatioViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AspectRatioViewHolder).bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class AspectRatioViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val text: TextView = itemView.findViewById(R.id.tvRatio)

        init {
            itemView.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(list[bindingAdapterPosition])

                    val temp = lastSelectedItem
                    lastSelectedItem = bindingAdapterPosition

                    if (temp != -1) {
                        this@AspectRatioAdapter.notifyItemChanged(temp)
                    }

                    this@AspectRatioAdapter.notifyItemChanged(lastSelectedItem)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(pair: Pair<Int, Int>) {
            text.text = "${pair.first}:${pair.second}"

            if (bindingAdapterPosition == lastSelectedItem) {
                text.setTextColor(Color.parseColor("#00BFA5"))
            } else {
                text.setTextColor(Color.parseColor("#FF3D4166"))
            }
        }

    }
}