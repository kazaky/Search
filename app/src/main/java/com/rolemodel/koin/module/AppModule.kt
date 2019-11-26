package com.rolemodel.koin.module

import com.rolemodel.data.repository.DataRepository
import com.rolemodel.data.repository.DataRepositoryImpl
import com.rolemodel.scheduler.AppSchedulerProvider
import com.rolemodel.scheduler.SchedulerProvider
import org.koin.dsl.module

val appModule = module {
    single<DataRepository> { DataRepositoryImpl(get()) }
    single<SchedulerProvider> { AppSchedulerProvider() }
}