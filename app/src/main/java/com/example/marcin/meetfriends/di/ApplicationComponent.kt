package com.example.marcin.meetfriends.di

import com.example.marcin.meetfriends.MeetFriendsApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by marci on 2017-11-09.
 */

@Singleton
@Component(modules = arrayOf(
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    InjectorsModule::class
))
interface ApplicationComponent : AndroidInjector<MeetFriendsApplication> {

  @Component.Builder
  abstract class Builder : AndroidInjector.Builder<MeetFriendsApplication>()
}