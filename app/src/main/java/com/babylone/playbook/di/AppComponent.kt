package com.babylone.playbook.di

import com.babylone.playbook.PlaybookApp
import com.babylone.playbook.data.DataModule
import com.babylone.playbook.data.network.NetworkModule
import com.babylone.playbook.domain.DomainModule
import com.babylone.playbook.ui.details.DetailsActivityModule
import com.babylone.playbook.ui.main.MainActivityModule
import com.babylone.playbook.ui.splash.SplashModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        NetworkModule::class,
        DomainModule::class,
        SplashModule::class,
        MainActivityModule::class,
        DetailsActivityModule::class,
        AndroidInjectionModule::class,
        ActivityBuilder::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: PlaybookApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: PlaybookApp)
}