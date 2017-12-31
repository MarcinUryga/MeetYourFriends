package com.example.marcin.meetfriends.ui.place_details

import com.example.marci.googlemaps.pojo.Location
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.common.GetPlaceDetailsUseCase
import com.example.marcin.meetfriends.ui.common.PlaceIdParams
import com.example.marcin.meetfriends.ui.place_details.viewmodel.PlaceDetails
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by marci on 2017-12-29.
 */
@ScreenScope
class PlaceDetailsPresenter @Inject constructor(
    private val getPlaceDetailsUseCase: GetPlaceDetailsUseCase,
    private val placeIdParams: PlaceIdParams
) : BasePresenter<PlaceDetailsContract.View>(), PlaceDetailsContract.Presenter {

  var placeLocation: Location? = null

  override fun onViewCreated() {
    super.onViewCreated()
    getPlaceDetails(placeIdParams.placeId)
  }

  private fun getPlaceDetails(placeId: String) {
    val disposable = getPlaceDetailsUseCase.get(placeId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.showProgressBar() }
        .doFinally { view.hideProgressBar() }
        .subscribe({ placeDetails ->
          placeLocation = placeDetails.result.geometry.location
          view.showPlaceDetails(PlaceDetails(
              id = placeDetails.result.placeId,
              photos = placeDetails.result.photos,
              placeIcon = placeDetails.result.icon,
              location = placeDetails.result.geometry.location,
              name = placeDetails.result.name,
              address = placeDetails.result.formattedAddress,
              phoneNumber = placeDetails.result.internationalPhoneNumber,
              websiteUrl = placeDetails.result.website,
              rating = placeDetails.result.rating,
              weekdayOpeningHours = placeDetails.result.openingHours?.weekdayText,
              reviews = placeDetails.result.reviews
          ))
        })
    disposables?.add(disposable)
  }

  override fun clickedOpenGoogleMapsButton() {
    placeLocation?.let {
      view.startGoogleMaps(it)
    }
  }
}