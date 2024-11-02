package com.example.myapplication.di

import com.example.myapplication.data.remote.RemoteDataSource
import org.koin.dsl.module

val remoteDataSourceModule = module {
    factory { RemoteDataSource(apiService = get()) }
}