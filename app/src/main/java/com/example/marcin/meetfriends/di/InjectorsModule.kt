package com.example.marcin.meetfriends.di

import com.example.marcin.meetfriends.ui.change_event.ChangeEventDialogFragment
import com.example.marcin.meetfriends.ui.change_event.ChangeEventModule
import com.example.marcin.meetfriends.ui.chat.ChatActivity
import com.example.marcin.meetfriends.ui.chat.ChatModule
import com.example.marcin.meetfriends.ui.chat_rooms.ChatRoomsFragment
import com.example.marcin.meetfriends.ui.chat_rooms.ChatRoomsModule
import com.example.marcin.meetfriends.ui.friends.FriendsFragment
import com.example.marcin.meetfriends.ui.friends.FriendsModule
import com.example.marcin.meetfriends.ui.launch.LaunchActivity
import com.example.marcin.meetfriends.ui.launch.LaunchModule
import com.example.marcin.meetfriends.ui.login.LoginActivity
import com.example.marcin.meetfriends.ui.login.LoginModule
import com.example.marcin.meetfriends.ui.main.MainActivity
import com.example.marcin.meetfriends.ui.main.MainModule
import com.example.marcin.meetfriends.ui.menu_navigation.MainNavigationActivity
import com.example.marcin.meetfriends.ui.menu_navigation.MainNavigationModule
import com.example.marcin.meetfriends.ui.my_schedule.confirmed_events.ConfirmedEventsFragment
import com.example.marcin.meetfriends.ui.my_schedule.confirmed_events.ConfirmedEventsModule
import com.example.marcin.meetfriends.ui.my_schedule.planned_event.PlannedEventsFragment
import com.example.marcin.meetfriends.ui.my_schedule.planned_event.PlannedEventsModule
import com.example.marcin.meetfriends.ui.venues.VenuesFragment
import com.example.marcin.meetfriends.ui.venues.VenuesModule
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

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(ConfirmedEventsModule::class))
  abstract fun confirmedEventsFragment(): ConfirmedEventsFragment

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(PlannedEventsModule::class))
  abstract fun plannedEventsFragment(): PlannedEventsFragment

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(FriendsModule::class))
  abstract fun friendsFragment(): FriendsFragment

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(VenuesModule::class))
  abstract fun venuesFragment(): VenuesFragment

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(MainNavigationModule::class))
  abstract fun mainNavigationActivity(): MainNavigationActivity

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(ChatRoomsModule::class))
  abstract fun chatRoomsFragment(): ChatRoomsFragment

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(ChatModule::class))
  abstract fun chatActivity(): ChatActivity

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(ChangeEventModule::class))
  abstract fun changeEventFragment(): ChangeEventDialogFragment
}