package com.example.testjazenet

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testjazenet.base.onlyLetters
import com.example.testjazenet.base.toArrayList
import com.example.testjazenet.network.ConnectionManager
import com.example.testjazenet.network.ResultCallback
import com.example.testjazenet.provider.CompanyContactList
import com.example.testjazenet.provider.ContactDetailsProvider
import com.example.testjazenet.provider.ContactListRequest
import com.example.testjazenet.provider.MainResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var itemList = MutableLiveData<ArrayList<CompanyContactList>>()
    private val clickOptions = MutableLiveData<Int>()
    private val errorMsg = MutableLiveData<String>()
    val showProgress = MutableLiveData<Boolean>()

    init {
        getItemsList()
    }

    private fun getItemsList() {

        coroutineScope.launch {
            showProgress.value = true
            ConnectionManager.getDataManager().contacts(
                ContactListRequest("bizcom", "1", "100", "181428"),

                object : ResultCallback<MainResponse<ContactDetailsProvider>> {
                    override fun onError(code: Int, errorMessage: String) {
                        showProgress.value = false
                        errorMsg.value = errorMessage
                    }

                    override fun <T> onSuccess(response: T) {
                        showProgress.value = false
                        if (response is MainResponse<*>) {
                            val resp = response.result as ContactDetailsProvider
                            var list = resp.company_contact_list?.toArrayList() ?: ArrayList()
                            list = list
                                .sortedBy { it.name?.trim() }
                                .sortedBy { it.name?.first().toString().onlyLetters() }
                                .toArrayList()
                            itemList.value = list
                        }
                    }
                }
            )
        }
    }


    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }

    fun getContactResponse(): LiveData<ArrayList<CompanyContactList>> {
        return itemList
    }


    fun getError(): LiveData<String> {
        return errorMsg

    }
}