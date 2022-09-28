package com.example.testreports.viewModels


import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.example.testreports.Repository.LocalDataRepository
import com.example.testreports.data.ContactItem
import com.example.testreports.utils.OneShotEvent
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecordsViewModel(
    private val localDataRepository: LocalDataRepository
) :
    BaseAndroidViewModel() {


    var allRecordsLiveData: MutableLiveData<OneShotEvent<List<ContactItem>>> = MutableLiveData()



    // GETTING DATA
    fun fetchAllRecords(context:Activity) {
        viewModelScope.launch {
            localDataRepository.getAllRecords(context)?.let { response ->

                allRecordsLiveData.value = OneShotEvent(response)


            }
        }
    }

    fun addContact(context:Activity,contactItem: ContactItem ) {
        viewModelScope.launch {
            localDataRepository.addContact(context,contactItem)?.let { response ->

                allRecordsLiveData.value = OneShotEvent(response)

            }
        }
    }
    fun updateContact(context:Activity,contactItem: ContactItem ) {
        viewModelScope.launch {
            localDataRepository.updateContact(context,contactItem)?.let { response ->

                allRecordsLiveData.value = OneShotEvent(response)

            }
        }
    }

    fun deleteContact(context:Activity,contactId: String ) {
        viewModelScope.launch {
            localDataRepository.deleteContact(context,contactId)?.let { response ->

                allRecordsLiveData.value = OneShotEvent(response)

            }
        }
    }


}