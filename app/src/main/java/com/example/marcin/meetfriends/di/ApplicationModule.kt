package com.example.marcin.meetfriends.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.beltaief.reactivefb.SimpleFacebookConfiguration
import com.beltaief.reactivefb.util.PermissionHelper
import com.example.marcin.meetfriends.MeetFriendsApplication
import com.example.marcin.meetfriends.R
import com.facebook.login.DefaultAudience
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by marci on 2017-11-09.
 */

@Module
class ApplicationModule {

  @Provides
  @ApplicationContext
  fun provideAppContext(meetFriendsApplication: MeetFriendsApplication): Context {
    return meetFriendsApplication
  }

  @Provides
  fun provideContext(meetFriendsApplication: MeetFriendsApplication): Context {
    return meetFriendsApplication
  }

  @Provides
  @Singleton
  fun provideFacebookPermissionHelper(): Array<PermissionHelper> {
    return arrayOf(
        PermissionHelper.USER_ABOUT_ME,
        PermissionHelper.EMAIL,
        PermissionHelper.USER_PHOTOS,
        PermissionHelper.USER_EVENTS,
        PermissionHelper.USER_ACTIONS_MUSIC,
        PermissionHelper.USER_FRIENDS,
        PermissionHelper.USER_GAMES_ACTIVITY,
        PermissionHelper.USER_BIRTHDAY,
        PermissionHelper.USER_TAGGED_PLACES,
        PermissionHelper.USER_MANAGED_GROUPS,
        PermissionHelper.PUBLISH_ACTION
    )
  }

  @Provides
  @Singleton
  fun provideFacebookConfiguration(permissions: Array<PermissionHelper>): SimpleFacebookConfiguration {
    return SimpleFacebookConfiguration.Builder()
        .setAppId(R.string.facebook_app_id.toString())
        .setPermissions(permissions)
        .setDefaultAudience(DefaultAudience.FRIENDS)
        .setAskForAllPermissionsAtOnce(false)
        .build()
  }

  @Provides
  @Singleton
  fun provideFirebaseAuth(): FirebaseAuth {
    return FirebaseAuth.getInstance()
  }

  @Provides
  @Singleton
  fun provideFirebaseDatabase(): FirebaseDatabase {
    return FirebaseDatabase.getInstance()
  }

  @Provides
  fun providesSharedPreferences(app: MeetFriendsApplication): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(app.applicationContext)
  }
}