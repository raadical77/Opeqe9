package ir.app.opeqe
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.util.*

class AnkoAdapter(itemFactory: () -> AbstractList<Any>, viewFactory: (index: Int, items: AbstractList<Any>, view: View?, viewGroup: ViewGroup?) -> View): BaseAdapter() {
    val viewFactory = viewFactory
    val items: AbstractList<Any> by lazy { itemFactory() }

    override fun getView(index: Int, view: View?, viewGroup: ViewGroup?): View {
        return viewFactory(index, items, view, viewGroup)
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(index: Int): Any {
        return items.get(index)
    }

    override fun getItemId(index: Int): Long {
        return items.get(index).hashCode().toLong() + (index.toLong() * Int.MAX_VALUE)
    }

}



