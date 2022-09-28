package com.example.testreports.dialogFragments

import android.view.View
import android.widget.Toast
import com.example.testreports.R
import com.example.testreports.data.ContactItem
import kotlinx.android.synthetic.main.dialog_fragment_add_record_info.view.*
import kotlinx.android.synthetic.main.layout_add_user_info.view.*


class AddRecordInfoDialog : BaseDialog(), View.OnClickListener {
    var layout: View? = null
    var contactItem: ContactItem? = null
    var setOnNewRecordInserted: SetOnNewRecordInserted? = null


    override fun initViews(view: View) {

        arguments?.let {
            contactItem = it.getSerializable("recordItem") as ContactItem?
        }

        layout = view

        view.contact_name.info_title.text = getString(R.string.contact_name)
        view.contact_number.info_title.text = getString(R.string.contact_number)
        view.contact_title.info_title.text= getString(R.string.contact_title)
        view.company_name.info_title.text= getString(R.string.company_name)
        view.contact_address.info_title.text= getString(R.string.contact_address)
        view.contact_city.info_title.text= getString(R.string.contact_city)
        view.contact_email.info_title.text= getString(R.string.contact_email)
        view.contact_region.info_title.text= getString(R.string.contact_region)
        view.contact_postal_code.info_title.text= getString(R.string.contact_postal_code)
        view.contact_country.info_title.text= getString(R.string.contact_country)
        view.contact_fax.info_title.text= getString(R.string.contact_fax)

        view.save_record.setOnClickListener(this)

    }


    override fun getLayoutId(): Int {
        return R.layout.dialog_fragment_add_record_info
    }


    companion object {
        fun newInstance(): AddRecordInfoDialog {

            val recordInfoDialog = AddRecordInfoDialog()
            return recordInfoDialog
        }
    }

    fun createItem(time:String,name:String,type:String,status:String): ContactItem {

        var recordItem = ContactItem(
            "",
            time,
            name,
            type,
            status
        )

        return recordItem
    }

    override fun onClick(v: View?) {

        var contactName =  layout?.contact_name?.info_des?.text.toString()
        var contactNumber =  layout?.contact_number?.info_des?.text.toString()
        var contactTitle =  layout?.contact_title?.info_des?.text.toString()
        var companyName =  layout?.company_name?.info_des?.text.toString()
        var contactAddress =  layout?.contact_address?.info_des?.text.toString()
        var contactCity =  layout?.contact_city?.info_des?.text.toString()
        var contactEmail =  layout?.contact_email?.info_des?.text.toString()
        var contactRegion = layout?.contact_region?.info_des?.text.toString()
        var contactPostalCode =  layout?.contact_postal_code?.info_des?.text.toString()
        var contactCountry =  layout?.contact_country?.info_des?.text.toString()
        var contactFax =  layout?.contact_fax?.info_des?.text.toString()


        if(contactName.isNotBlank() && contactNumber.isNotBlank() ) {

            setOnNewRecordInserted?.onNewRecordInserted(ContactItem(
                System.currentTimeMillis().toString(),
                companyName,
                contactName,
                contactTitle,
                contactAddress,
                contactCity,
                contactEmail,
                contactRegion,
                contactPostalCode,
                contactCountry,
                contactNumber,
                contactFax

            ))
            dismiss()

        }else{
            Toast.makeText(activity, "Please fill all the fields", Toast.LENGTH_LONG).show()
        }

    }

    interface SetOnNewRecordInserted {
        fun onNewRecordInserted(contactItem: ContactItem?)
    }

    fun setNewRecordInsertListiner(recordInsertListiner: SetOnNewRecordInserted) {

        setOnNewRecordInserted = recordInsertListiner
    }
}