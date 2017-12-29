package com.example.marcin.meetfriends.di

import com.example.marcin.meetfriends.ui.chat.ChatActivity
import com.example.marcin.meetfriends.ui.chat.ChatModule
import com.example.marcin.meetfriends.ui.chat_rooms.ChatRoomsFragment
import com.example.marcin.meetfriends.ui.chat_rooms.ChatRoomsModule
import com.example.marcin.meetfriends.ui.choose_event_icon.ChooseEventIconDialogFragment
import com.example.marcin.meetfriends.ui.choose_event_icon.ChooseEventIconModule
import com.example.marcin.meetfriends.ui.create_event.CreateEventActivity
import com.example.marcin.meetfriends.ui.create_event.CreateEventModule
import com.example.marcin.meetfriends.ui.event_detail.EventDetailActivity
import com.example.marcin.meetfriends.ui.event_detail.EventDetailModule
import com.example.marcin.meetfriends.ui.event_detail.event_description.EventDescriptionFragment
import com.example.marcin.meetfriends.ui.event_detail.event_description.EventDescriptionModule
import com.example.marcin.meetfriends.ui.event_detail.event_questionnaire.EventQuestionnaireFragment
import com.example.marcin.meetfriends.ui.event_detail.event_questionnaire.EventQuestionnaireModule
import com.example.marcin.meetfriends.ui.friends.FriendsActivity
import com.example.marcin.meetfriends.ui.friends.FriendsModule
import com.example.marcin.meetfriends.ui.launch.LaunchActivity
import com.example.marcin.meetfriends.ui.launch.LaunchModule
import com.example.marcin.meetfriends.ui.login.LoginActivity
import com.example.marcin.meetfriends.ui.login.LoginModule
import com.example.marcin.meetfriends.ui.main.MainActivity
import com.example.marcin.meetfriends.ui.main.MainModule
import com.example.marcin.meetfriends.ui.my_schedule.confirmed_events.ConfirmedEventsFragment
import com.example.marcin.meetfriends.ui.my_schedule.confirmed_events.ConfirmedEventsModule
import com.example.marcin.meetfriends.ui.my_schedule.planned_event.PlannedEventsFragment
import com.example.marcin.meetfriends.ui.my_schedule.planned_event.PlannedEventsModule
import com.example.marcin.meetfriends.ui.place_details.PlaceDetailsActivity
import com.example.marcin.meetfriends.ui.place_details.PlaceDetailsModule
import com.example.marcin.meetfriends.ui.questionnaires.QuestionnairesFragment
import com.example.marcin.meetfriends.ui.questionnaires.QuestionnairesModule
import com.example.marcin.meetfriends.ui.search_venues.SearchVenuesActivity
import com.example.marcin.meetfriends.ui.search_venues.SearchVenuesModule
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
  abstract fun friendsActivity(): FriendsActivity

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(VenuesModule::class))
  abstract fun venuesFragment(): VenuesFragment

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(ChatRoomsModule::class))
  abstract fun chatRoomsFragment(): ChatRoomsFragment

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(ChatModule::class))
  abstract fun chatActivity(): ChatActivity

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(EventDetailModule::class))
  abstract fun eventDetailActivity(): EventDetailActivity

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(EventDescriptionModule::class))
  abstract fun eventDescriptionFragment(): EventDescriptionFragment

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(EventQuestionnaireModule::class))
  abstract fun eventQuestionnaireFragment(): EventQuestionnaireFragment

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(QuestionnairesModule::class))
  abstract fun questionnairesFragment(): QuestionnairesFragment

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(CreateEventModule::class))
  abstract fun createEventFragment(): CreateEventActivity

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(ChooseEventIconModule::class))
  abstract fun chooseEventIconFragment(): ChooseEventIconDialogFragment

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(SearchVenuesModule::class))
  abstract fun searchVenuesActivity(): SearchVenuesActivity

  @ScreenScope
  @ContributesAndroidInjector(modules = arrayOf(PlaceDetailsModule::class))
  abstract fun placeDetailsActivity(): PlaceDetailsActivity
}