package com.example.marcin.meetfriends.ui.venues

import dagger.Binds
import dagger.Module

/**
 * Created by MARCIN on 2017-11-13.
 */
@Module
abstract class VenuesModule {

  @Binds
  abstract fun bindsView(view: VenuesFragment): VenuesContract.View

  @Binds
  abstract fun bindPresenter(presenter: VenuesPresenter): VenuesContract.Presenter
}