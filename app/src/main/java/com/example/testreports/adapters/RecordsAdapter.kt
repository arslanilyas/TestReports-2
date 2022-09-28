package com.example.testreports.adapters

import android.graphics.Color
import android.view.View
import com.example.testreports.R
import com.example.testreports.data.ContactItem
import com.example.testreports.utils.AppConstants
import kotlinx.android.synthetic.main.recycler_item_record.view.*


class RecordsAdapter(
    var itemClicker: OnItemClicker,
    var items: ArrayList<ContactItem>
) : BaseAdapter(itemClicker, items, R.layout.recycler_item_record, AppConstants.RECORDS_ADAPTER) {
    override fun View.bind(item: Any, position: Int) {
        val data = item as ContactItem
        this.contact_name.text = data.contactName
        //this.type.text = data.type
        this.contact_number.text = data.contactPhone

        if(position %2 == 1)
        {
            layout_record_item.setBackgroundColor(Color.LTGRAY);
        }
        else
        {
            layout_record_item.setBackgroundColor(Color.WHITE);
        }
    }
}