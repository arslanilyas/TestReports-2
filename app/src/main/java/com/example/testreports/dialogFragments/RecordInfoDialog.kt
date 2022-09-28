package com.example.testreports.dialogFragments

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.testreports.R
import com.example.testreports.data.ContactItem
import kotlinx.android.synthetic.main.dialog_fragment_record_info.view.*
import kotlinx.android.synthetic.main.dialog_fragment_record_info.view.company_name
import kotlinx.android.synthetic.main.layout_user_actions.view.*
import kotlinx.android.synthetic.main.layout_user_info.view.info_des
import kotlinx.android.synthetic.main.layout_user_info.view.info_title
import android.content.Intent
import android.net.Uri
import com.example.testreports.MainActivity

import android.content.DialogInterface





class RecordInfoDialog : BaseDialog(), View.OnClickListener {
    var layout: View? = null
    var contactItem: ContactItem? = null
    var setOnContactUpdate: SetOnContactUpdate? = null
    var isUpdateAble: Boolean = false;


    override fun initViews(view: View) {

        arguments?.let {
            contactItem = it.getSerializable("recordItem") as ContactItem?
        }

        layout = view

        if (contactItem != null) {

            view.user_name.info_title.text = getString(R.string.contact_name);
            view.user_name.info_des.setText(contactItem?.contactName)

            view.user_number.info_title.text = getString(R.string.contact_number);
            view.user_number.info_des.setText(contactItem?.contactPhone)

            view.company_name.info_title.text = getString(R.string.company_name);
            view.company_name.info_des.setText(contactItem?.companyName)

            view.contact_title.info_title.text = getString(R.string.contact_title);
            view.contact_title.info_des.setText(contactItem?.contactTitle)

            view.contact_address.info_title.text = getString(R.string.contact_address);
            view.contact_address.info_des.setText(contactItem?.contactAddress)

            view.contact_city.info_title.text = getString(R.string.contact_city);
            view.contact_city.info_des.setText(contactItem?.contactCity)

            view.contact_email.info_title.text = getString(R.string.contact_email);
            view.contact_email.info_des.setText(contactItem?.contactEmail)

            view.contact_region.info_title.text = getString(R.string.contact_region);
            view.contact_region.info_des.setText(contactItem?.contactRegion)

            view.contact_postal_code.info_title.text = getString(R.string.contact_postal_code);
            view.contact_postal_code.info_des.setText(contactItem?.contactPostalCode)

            view.contact_country.info_title.text = getString(R.string.contact_country);
            view.contact_country.info_des.setText(contactItem?.contactCountry)

            view.contact_fax.info_title.text = getString(R.string.contact_fax);
            view.contact_fax.info_des.setText(contactItem?.contactFax)

        }

        view.edit_btn.setOnClickListener(this)
        view.delete_btn.setOnClickListener(this)
        view.close_btn.setOnClickListener(this)
        view.users_action.action_call.setOnClickListener(this)
        view.users_action.action_email.setOnClickListener(this)
        view.users_action.action_message.setOnClickListener(this)
        view.users_action.action_share.setOnClickListener(this)

    }


    fun deleteContact() {
        var contactID = contactItem?.customerID
        setOnContactUpdate?.onContactDelete(contactID)
        dismiss();
    }

    fun updateContact() {

        var contactName = layout?.user_name?.info_des?.text.toString()
        var contactNumber = layout?.user_number?.info_des?.text.toString()
        var contactTitle = layout?.contact_title?.info_des?.text.toString()
        var companyName = layout?.company_name?.info_des?.text.toString()
        var contactAddress = layout?.contact_address?.info_des?.text.toString()
        var contactCity = layout?.contact_city?.info_des?.text.toString()
        var contactEmail = layout?.contact_email?.info_des?.text.toString()
        var contactRegion = layout?.contact_region?.info_des?.text.toString()
        var contactPostalCode = layout?.contact_postal_code?.info_des?.text.toString()
        var contactCountry = layout?.contact_country?.info_des?.text.toString()
        var contactFax = layout?.contact_fax?.info_des?.text.toString()
        var contactID = contactItem?.customerID


        if (contactName.isNotBlank() && contactNumber.isNotBlank()) {

            setOnContactUpdate?.onContactUpdate(
                ContactItem(
                    contactID,
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

                )
            )

            dismiss()

        } else {
            Toast.makeText(activity, "Please fill all the fields", Toast.LENGTH_LONG).show()
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.dialog_fragment_record_info
    }


    companion object {
        fun newInstance(contactItem: ContactItem): RecordInfoDialog {

            val recordInfoDialog = RecordInfoDialog()
            val bundle = Bundle()
            bundle.putSerializable("recordItem", contactItem)
            recordInfoDialog.arguments = bundle
            return recordInfoDialog
        }
    }

    override fun onClick(view: View?) {

        when (view?.id) {
            R.id.edit_btn -> {
                if (!isUpdateAble) {
                    makeFieldsEditable(true)
                    isUpdateAble = true;
                    view.edit_btn.text = "Update"
                } else {



                    AlertDialog.Builder(activity)
                        .setTitle("Update!")
                        .setMessage("You are about to update this contact!")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes,
                            DialogInterface.OnClickListener { dialog, whichButton ->
                                updateContact()
                            })
                        .setNegativeButton(android.R.string.no, null).show()
                }
            }
            R.id.delete_btn -> {

                AlertDialog.Builder(activity)
                    .setTitle("Delete!")
                    .setMessage("Are sure you want to delete this contact?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes,
                        DialogInterface.OnClickListener { dialog, whichButton ->
                            deleteContact();
                        })
                    .setNegativeButton(android.R.string.no, null).show()
            }
            R.id.close_btn -> {
                dismiss()
            }
            R.id.action_call -> {

                makeACall();
            }
            R.id.action_message -> {

                composeAMessage()
            }
            R.id.action_email -> {

                composeAEmail()
            }
            R.id.action_share -> {

                shareContact()
            }

        }
    }

    private fun makeACall() {

        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:" + contactItem?.contactPhone)
        startActivity(intent)

    }

    private fun composeAMessage() {

        val smsIntent = Intent(Intent.ACTION_VIEW)
        smsIntent.type = "vnd.android-dir/mms-sms"
        smsIntent.putExtra("address", contactItem?.contactPhone)
        smsIntent.putExtra("sms_body", "")
        startActivity(smsIntent)

    }

    private fun composeAEmail() {

        val emailIntent: Intent = Intent(
            Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", contactItem?.contactEmail, null
            )
        )
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "")
        startActivity(Intent.createChooser(emailIntent, "Send email..."))


    }

    private fun shareContact() {

        /*Create an ACTION_SEND Intent*/
        /*Create an ACTION_SEND Intent*/
        val intent = Intent(Intent.ACTION_SEND)
        /*This will be the actual content you wish you share.*/
        /*This will be the actual content you wish you share.*/
        val shareBody = "Here is the share contact "+ contactItem?.contactPhone
        /*The type of the content is text, obviously.*/
        /*The type of the content is text, obviously.*/intent.type = "text/plain"
        /*Applying information Subject and Body.*/
        /*Applying information Subject and Body.*/intent.putExtra(
            Intent.EXTRA_SUBJECT,
            contactItem?.contactPhone
        )
        intent.putExtra(Intent.EXTRA_TEXT, shareBody)
        /*Fire!*/
        /*Fire!*/startActivity(Intent.createChooser(intent, getString(R.string.contact_fax)))


    }

    private fun makeFieldsEditable(boolean: Boolean) {

        layout?.user_name?.info_des?.isEnabled = boolean
        layout?.company_name?.info_des?.isEnabled = boolean
        layout?.contact_title?.info_des?.isEnabled = boolean
        layout?.user_number?.info_des?.isEnabled = boolean
        layout?.contact_address?.info_des?.isEnabled = boolean
        layout?.contact_city?.info_des?.isEnabled = boolean
        layout?.contact_email?.info_des?.isEnabled = boolean
        layout?.contact_region?.info_des?.isEnabled = boolean
        layout?.contact_postal_code?.info_des?.isEnabled = boolean
        layout?.contact_country?.info_des?.isEnabled = boolean
        layout?.contact_fax?.info_des?.isEnabled = boolean

    }


    interface SetOnContactUpdate {
        fun onContactUpdate(contactItem: ContactItem?)
        fun onContactDelete(contactId: String?)
    }

    fun setContactUpdateListiner(recordUpdateListiner: SetOnContactUpdate) {

        setOnContactUpdate = recordUpdateListiner
    }


}
