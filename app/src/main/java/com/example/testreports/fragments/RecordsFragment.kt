package com.example.testreports.fragments



import android.view.View
import androidx.lifecycle.Observer
import com.example.testreports.R
import com.example.testreports.adapters.BaseAdapter
import com.example.testreports.adapters.RecordsAdapter
import com.example.testreports.data.ContactItem
import com.example.testreports.dialogFragments.AddRecordInfoDialog
import com.example.testreports.dialogFragments.RecordInfoDialog
import com.example.testreports.utils.XmlParserUtil
import com.example.testreports.viewModels.RecordsViewModel
import com.smartserve.pos.Utils.Application.observe
import com.smartserve.pos.Utils.Application.require
import kotlinx.android.synthetic.main.fragment_records.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList


class RecordsFragment : BaseFragment(R.layout.fragment_records), BaseAdapter.OnItemClicker,
    View.OnClickListener {


    val viewModel: RecordsViewModel by sharedViewModel()
    var recordsAdapter: RecordsAdapter? = null
    var contactList: ArrayList<ContactItem> = ArrayList()
    var allRecordsList: ArrayList<ContactItem> = ArrayList()
    var isAscendingOrder = true;


    override fun initViews() {
        edit_text_search_tables.observe { query ->
            contactList.clear()
            contactList.addAll(allRecordsList.filter {
                it.contactName.require().toLowerCase().contains(query.toLowerCase())
            })
            recordsAdapter?.notifyDataSetChanged()
        }
        recordsAdapter = RecordsAdapter(this, contactList)
        recycler_view_tables.adapter = recordsAdapter

        add_new_record.setOnClickListener(this)
        action_filter.setOnClickListener(this)

    }

    override fun attachViewModel() {
        with(viewModel)
        {

            allRecordsLiveData.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {
                    allRecordsList.clear()
                    contactList.clear()
                    contactList.addAll(it)
                    allRecordsList.addAll(it)
                    recordsAdapter?.notifyDataSetChanged()
                    recycler_view_tables.requestFocus()
                    setAllStats()
                }
            })
            activity?.let { fetchAllRecords(it) }
        }


    }

    override fun onItemClick(position: Int, data: Any, adapterType: Int) {

        var infoDialog = RecordInfoDialog.newInstance(allRecordsList.get(position))
        infoDialog.setCancelable(false)

        infoDialog.setContactUpdateListiner(object : RecordInfoDialog.SetOnContactUpdate {
            override fun onContactUpdate(contactItem: ContactItem?) {
                if (contactItem != null) {
                    with(viewModel)
                    {
                        activity?.let { updateContact(it, contactItem) }
                    }
                }
            }

            override fun onContactDelete(contactID: String?) {
                if (contactID != null) {
                    with(viewModel)
                    {
                        activity?.let { deleteContact(it, contactID) }
                    }
                }
            }

        })

        infoDialog.show(childFragmentManager, "")

    }

    fun setAllStats() {


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.add_new_record -> {
                var dialog = AddRecordInfoDialog.newInstance()
                dialog.setNewRecordInsertListiner(object :
                    AddRecordInfoDialog.SetOnNewRecordInserted {
                    override fun onNewRecordInserted(contactItem: ContactItem?) {
                        if (contactItem != null) {
                            with(viewModel)
                            {
                                activity?.let { addContact(it, contactItem) }
                            }

                        }

                    }


                })
                dialog.show(childFragmentManager, "")
            }

            R.id.action_filter -> {

                activity?.let { XmlParserUtil.converXmlToJson(it) };

                if(!isAscendingOrder) {
                    sortByAscendingOrder()
                    isAscendingOrder = true;
                }else{
                    sortByDescendingOrder()
                    isAscendingOrder = false;
                }
                recordsAdapter?.notifyDataSetChanged()

            }

        }

    }


    fun sortByAscendingOrder(){

        Collections.sort(contactList,
            Comparator<ContactItem?> { lhs, rhs ->
                rhs?.contactName?.let {
                    lhs?.contactName?.compareTo(
                        it
                    )
                }!!
            })

        Collections.sort(allRecordsList,
            Comparator<ContactItem?> { lhs, rhs ->
                rhs?.contactName?.let {
                    lhs?.contactName?.compareTo(
                        it
                    )
                }!!
            })
    }

    fun sortByDescendingOrder(){

        Collections.sort(contactList,
            Comparator<ContactItem?> { lhs, rhs ->
                lhs?.contactName?.let {
                    rhs?.contactName?.compareTo(
                        it
                    )
                }!!
            })

        Collections.sort(allRecordsList,
            Comparator<ContactItem?> { lhs, rhs ->
                lhs?.contactName?.let {
                    rhs?.contactName?.compareTo(
                        it
                    )
                }!!
            })
    }

}