package com.android.binapp.di

import androidx.room.Room
import com.android.binapp.data.DataBase
import com.android.binapp.data.repository.Repository
import com.android.binapp.data.retrofit.RetrofitClient
import com.android.binapp.data.retrofit.RetrofitServices
import com.android.binapp.presentation.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            DataBase::class.java,
            "history_db.db"
        ).fallbackToDestructiveMigration()
         .build()
    }

    viewModel {
        MainViewModel( repository = get())
    }

    single {
       val db =  get<DataBase>()
       db.getBinDAO()
    }
    //class Repository(private val binDAO: BinDAO, private val rService: RetrofitServices) {
    single{
        Repository(binDAO = get(),  RetrofitClient.getClient("https://lookup.binlist.net").create(
            RetrofitServices::class.java))
    }

}
