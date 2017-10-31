# AggregateRecyclerAdapter
Adapter with header, footer, multiple views, click and long click, managed with delegate methods.

## Installation
Download repository and copy AggregateRecyclerAdapter.kt class in your project.

## Usage
Adapter is organized in sections and rows. Each section can contains rows and has an optional section header and footer.
Headers, rows and footers can have different views, based on section and rows numbers. They can be configured and respond to click and long click.

First create your model objects:
``` kotlin
val sections = listOf("Section 1", "Section 2", "Section 3")
val rows = listOf("Row 1", "Row 2", "Row 3")
```
Set adapter to your recyclerView and assign a delegate to the adapter:
``` kotlin
recyclerView.adapter = AggregateRecyclerAdapter(this)
```
Delegate has different members:
``` kotlin

// Number of sections
override fun numberOfSections(): Int {
        return sections.size
    }
    
// Number of rows for each section
override fun numberOfItems(section: Int): Int {
        return rows.size
    }

// Layout id of each row in a section
    override fun inflateRowID(section: Int, row: Int): Int {
        if (section == 0) {
            return R.layout.row_start
        } else if (section == 1) {
            return R.layout.row
        } else {
            return R.layout.row_end
        }
    }

// Configuration of each row in section (here Kotlin extension are used)
    override fun configureRow(itemType: Int, itemView: View, section: Int, row: Int) {
        itemView.textView.text = rows[row]
    }

// Row click
    override fun onRowClick(section: Int, row: Int) {
        super.onRowClick(section, row)
        Log.i("Row", "Click ${rows[row]}")
    }

// Row long click
    override fun onRowLongClick(section: Int, row: Int) {
        super.onRowLongClick(section, row)
        Log.i("Row", "Long click ${rows[row]}")
    }
    
    // Layout id of each header in a section
    override fun inflateHeaderID(section: Int): Int? {
        if (section == 0) {
            return R.layout.header_start
        } else if (section == 1) {
            return R.layout.header
        } else {
            return R.layout.header_end
        }
    }

// Configuration of each header for section (here Kotlin extension are used)
    override fun configureHeader(itemType: Int, itemView: View, section: Int) {
        super.configureHeader(itemType, itemView, section)
        itemView.textView.text = "Header ${sections[section]}"
    }

// Header click
    override fun onHeaderClick(section: Int) {
        super.onHeaderClick(section)
        Log.i("Header", "Click ${sections[section]}")
    }

// Header long click
    override fun onHeaderLongClick(section: Int) {
        super.onHeaderLongClick(section)
        Log.i("Header", "Long click ${sections[section]}")
    }

// Layout id of each footer in a section
    override fun inflateFooterID(section: Int): Int? {
        if (section == 0) {
            return R.layout.footer_start
        } else if (section == 1) {
            return R.layout.footer
        } else {
            return R.layout.footer_end
        }
    }
    
    // Configuration of each footer for section (here Kotlin extension are used)
    override fun configureFooter(itemType: Int, itemView: View, section: Int) {
        super.configureFooter(itemType, itemView, section)
        itemView.textView.text = "Footer ${sections[section]}"
    }

// Footer click
    override fun onFooterClick(section: Int) {
        super.onFooterClick(section)
        Log.i("Footer", "Click ${sections[section]}")
    }

// Footer long click
    override fun onFooterLongClick(section: Int) {
        super.onFooterLongClick(section)
        Log.i("Footer", "Long click ${sections[section]}")
    }
```
