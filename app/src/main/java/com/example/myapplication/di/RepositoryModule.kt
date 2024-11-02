package com.example.myapplication.di

import com.example.myapplication.data.Repository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory { Repository(context = androidContext(), remoteDataSource = get()) }
}