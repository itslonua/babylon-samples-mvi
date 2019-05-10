package com.babylone.playbook.core.mvp

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

object TestSchedulersFactory : SchedulerProvider {

    override fun io() = Schedulers.trampoline()

    override fun mainThread(): Scheduler = Schedulers.trampoline()

}