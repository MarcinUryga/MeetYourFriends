package com.example.marcin.meetfriends.ui.search_venues

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by marci on 2017-12-24.
 */
@ScreenScope
class SearchVenuesPresenter @Inject constructor(
    private val getNearbyPlacesUseCase: GetNearbyPlacesUseCase
) : BasePresenter<SearchVenuesContract.View>(), SearchVenuesContract.Presenter {

  internal var latitude: Double = 0.toDouble()
  internal var longitude: Double = 0.toDouble()

  override fun resume() {
    super.resume()
    val disposable = getNearbyPlacesUseCase.get("restaurant", "49.767151,20.4531756", 50000)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ venues ->
          //          Timber.d(venues.toString())
          venues.places.forEach {
            Timber.d(it.toString())
          }
          view.showVenues(venues.places)
        }, { error ->
          Timber.e(SearchVenuesActivity::class.java.simpleName, error.printStackTrace())
        })
    disposables?.add(disposable)
  }
}