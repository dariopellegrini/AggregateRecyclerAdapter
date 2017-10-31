package it.comixtime.comixtime.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by dariopellegrini on 27/10/17.
 */
open class AggregateRecyclerAdapter(private val delegate: Listener) : RecyclerView.Adapter<AggregateRecyclerAdapter.Holder>() {
    enum class CellType {
        header, row, footer
    }

    var list  = listOf<CellType>()
    var positionMap = mutableMapOf<Int, Pair<Int, Int>>()

    interface Listener {

        // Cell
        fun numberOfItems(section: Int): Int

        fun inflateRowID(section: Int, row: Int): Int

        fun onRowClick(section: Int, row: Int) {}

        fun onRowLongClick(section: Int, row: Int) {}

        fun configure(itemType: Int, itemView: View, section: Int, row: Int)

        // Header
        fun numberOfSections(): Int

        fun inflateHeaderID(section: Int): Int? {
            return null
        }

        fun configureHeader(itemType: Int, itemView: View, section: Int) {}

        fun onHeaderClick(section: Int) {}

        fun onHeaderLongClick(section: Int) {}

        // Footer
        fun inflateFooterID(section: Int): Int? {
            return null
        }

        fun configureFooter(itemType: Int, itemView: View, section: Int) {}

        fun onFooterClick(section: Int) {}

        fun onFooterLongClick(section: Int) {}
    }

    init {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return Holder(view, delegate, this)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val pair = getSectionAndRowFromPosition(position)
        when(list[position]) {
            CellType.header -> delegate.configureHeader(holder.itemViewType, holder.itemView, pair.first)
            CellType.row -> delegate.configure(holder.itemViewType, holder.itemView, pair.first, pair.second)
            CellType.footer -> delegate.configureFooter(holder.itemViewType, holder.itemView, pair.first)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val pair = getSectionAndRowFromPosition(position)

        if (delegate.inflateHeaderID(pair.first) == null) {
            print("Null")
        }

        return when(list[position]) {
            CellType.header -> delegate.inflateHeaderID(pair.first)!!
            CellType.row -> delegate.inflateRowID(pair.first, pair.second)
            CellType.footer -> delegate.inflateFooterID(pair.first)!!
        }
    }

    override fun getItemCount(): Int {
        list = configureTypeList()
        return list.size
    }

    // Utilities
    fun configureTypeList(): List<CellType> {
        var typeList = mutableListOf<CellType>()
        var position = -1
        for (sectionIndex in 0..(delegate.numberOfSections() - 1)) {
            if (delegate.inflateHeaderID(sectionIndex) != null) {
                typeList.add(CellType.header)
                position += 1
                addOrUpdate(position, sectionIndex, -1)
            }
            for (rowIndex in 0..(delegate.numberOfItems(sectionIndex) - 1)){
                typeList.add(CellType.row)
                position += 1
                addOrUpdate(position, sectionIndex, rowIndex)
            }
            if (delegate.inflateFooterID(sectionIndex) != null) {
                typeList.add(CellType.footer)
                position += 1
                addOrUpdate(position, sectionIndex, -1)
            }
        }
        return typeList
    }

    fun getSectionAndRowFromPosition(position: Int): Pair<Int, Int> {
        return positionMap[position]!!
    }

    // Holders
    class Holder(itemView: View, private val callback: Listener?, val adapter: AggregateRecyclerAdapter) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {
        init {
            this.itemView.setOnClickListener(this)
            this.itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View) {
            val indexPath = adapter.getSectionAndRowFromPosition(adapterPosition)

            when(adapter.list[adapterPosition]) {
                CellType.header -> callback?.onHeaderClick(indexPath.first)
                CellType.row -> callback?.onRowClick(indexPath.first, indexPath.second)
                CellType.footer -> callback?.onFooterClick(indexPath.first)
            }
        }

        override fun onLongClick(v: View): Boolean {
            val indexPath = adapter.getSectionAndRowFromPosition(adapterPosition)
            when(adapter.list[adapterPosition]) {
                CellType.header -> callback?.onHeaderLongClick(indexPath.first)
                CellType.row -> callback?.onRowLongClick(indexPath.first, indexPath.second)
                CellType.footer -> callback?.onFooterLongClick(indexPath.first)
            }
            return true
        }
    }

    // Map
    fun addOrUpdate(position: Int, section: Int, row: Int) {
        val pair = Pair(section, row)
        positionMap[position] = pair
    }
}