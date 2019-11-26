package com.rolemodel.scheduler

import io.reactivex.Scheduler
import org.koin.core.KoinComponent

interface SchedulerProvider : KoinComponent {
    fun ui(): Scheduler
    fun io(): Scheduler
}