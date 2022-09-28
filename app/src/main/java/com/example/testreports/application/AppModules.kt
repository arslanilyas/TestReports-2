package com.smartserve.pos.Utils.Application


import com.example.testreports.Repository.LocalDataRepository
import com.example.testreports.viewModels.RecordsViewModel

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single { LocalDataRepository() }

}


val viewModelModules = module {
    viewModel {
        RecordsViewModel(get())
    }


}