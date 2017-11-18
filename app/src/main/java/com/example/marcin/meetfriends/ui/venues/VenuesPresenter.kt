package com.example.marcin.meetfriends.ui.venues

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import javax.inject.Inject

/**
 * Created by MARCIN on 2017-11-13.
 */
@ScreenScope
class VenuesPresenter @Inject constructor(

) : BasePresenter<VenuesContract.View>(), VenuesContract.Presenter {

  override fun onViewCreated() {
    super.onViewCreated()
    view.showFindPlaceTitle()
  }
}