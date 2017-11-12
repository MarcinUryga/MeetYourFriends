package com.example.marcin.meetfriends.di

import com.example.marcin.meetfriends.ui.launch.LaunchActivity
import com.example.marcin.meetfriends.ui.launch.LaunchModule
import com.example.marcin.meetfriends.ui.login.LoginActivity
import com.example.marcin.meetfriends.ui.login.LoginModule
import com.example.marcin.meetfriends.ui.main.MainActivity
import com.example.marcin.meetfriends.ui.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by marci on 2017-11-09.
 */

@Module
abstract class InjectorsModule {

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(LoginModule::class))
  abstract fun loginActivity(): LoginActivity

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(MainModule::class))
  abstract fun mainActivity(): MainActivity

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(LaunchModule::class))
  abstract fun launchActivity(): LaunchActivity
}