package com.dariopellegrini.aggregaterecycleradapter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.dariopellegrini.aggregaterecycleradapter.adapter.AggregateRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row.view.*


class MainActivity : AppCompatActivity(), AggregateRecyclerAdapter.Listener {

    override fun numberOfSections(): Int {
        return sections.size
    }

    override fun numberOfItems(section: Int): Int {
        return rows.size
    }

    override fun inflateRowID(section: Int, row: Int): Int {
        if (section == 0) {
            return R.layout.row_start
        } else if (section == 1) {
            return R.layout.row
        } else {
            return R.layout.row_end
        }
    }

    override fun configureRow(itemType: Int, itemView: View, section: Int, row: Int) {
        itemView.textView.text = rows[row]
    }

    override fun onRowClick(section: Int, row: Int) {
        super.onRowClick(section, row)
        Log.i("Row", "Click ${rows[row]}")
    }

    override fun onRowLongClick(section: Int, row: Int) {
        super.onRowLongClick(section, row)
        Log.i("Row", "Long click ${rows[row]}")
    }

    override fun inflateHeaderID(section: Int): Int? {
        if (section == 0) {
            return R.layout.header_start
        } else if (section == 1) {
            return R.layout.header
        } else {
            return R.layout.header_end
        }
    }

    override fun configureHeader(itemType: Int, itemView: View, section: Int) {
        super.configureHeader(itemType, itemView, section)
        itemView.textView.text = "Header ${sections[section]}"
    }

    override fun onHeaderClick(section: Int) {
        super.onHeaderClick(section)
        Log.i("Header", "Click ${sections[section]}")
    }

    override fun onHeaderLongClick(section: Int) {
        super.onHeaderLongClick(section)
        Log.i("Header", "Long click ${sections[section]}")
    }

    override fun inflateFooterID(section: Int): Int? {
        if (section == 0) {
            return R.layout.footer_start
        } else if (section == 1) {
            return R.layout.footer
        } else {
            return R.layout.footer_end
        }
    }

    override fun configureFooter(itemType: Int, itemView: View, section: Int) {
        super.configureFooter(itemType, itemView, section)
        itemView.textView.text = "Footer ${sections[section]}"
    }

    override fun onFooterClick(section: Int) {
        super.onFooterClick(section)
        Log.i("Footer", "Click ${sections[section]}")
    }

    override fun onFooterLongClick(section: Int) {
        super.onFooterLongClick(section)
        Log.i("Footer", "Long click")
    }

    val sections = listOf("Section 1", "Section 2", "Section 3")
    val rows = listOf("Row 1", "Row 2", "Row 3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AggregateRecyclerAdapter(this)
    }
}
