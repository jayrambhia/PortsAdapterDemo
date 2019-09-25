package com.fenchtose.portsadapterdemo.commons_android.widgets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KClass

class SimpleAdapter(private val binders: List<ItemViewBinder>) :
    RecyclerView.Adapter<SimpleViewHolder>() {

    private var items: List<Any> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val binder = binders[viewType]
        return SimpleViewHolder(
            LayoutInflater.from(parent.context).inflate(binder.layoutId, parent, false),
            binder
        )
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        binders.forEachIndexed { index, binder ->
            if (item::class == binder.itemType) {
                return index
            }
        }

        throw RuntimeException("No binder provided for item type: ${item::class}")
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.bind(items, position)
    }

    fun update(items: List<Any>) {
        this.items = items
        notifyDataSetChanged()
    }

}

typealias Binder = (view: View, items: List<Any>, position: Int) -> Unit

class ItemViewBinder(@LayoutRes val layoutId: Int, val itemType: KClass<*>, val onBind: Binder)

fun itemViewBinder(@LayoutRes layoutId: Int, itemType: KClass<*>, onBind: Binder) =
    ItemViewBinder(layoutId, itemType, onBind)

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> itemViewBinder(
    @LayoutRes layoutId: Int,
    crossinline onBind: (View, T) -> Unit
) = itemViewBinder(layoutId, T::class) { view, items, position ->
    onBind(
        view,
        items[position] as T
    )
}

class SimpleViewHolder(itemView: View, private val binder: ItemViewBinder) :
    RecyclerView.ViewHolder(itemView) {
    fun bind(items: List<Any>, position: Int) {
        binder.onBind(itemView, items, position)
    }
}