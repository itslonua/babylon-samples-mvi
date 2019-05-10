package com.babylone.playbook.core.mvp

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun io(): Scheduler

    fun mainThread(): Scheduler

}