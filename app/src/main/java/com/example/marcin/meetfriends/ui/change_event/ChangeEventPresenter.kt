package com.example.marcin.meetfriends.ui.change_event

import android.content.SharedPreferences
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.storage.SharedPref
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by marci on 2017-11-27.
 */
@ScreenScope
class ChangeEventPresenter @Inject constructor(
    private val getMyEventsUseCase: GetMyEventsUseCase,
    sharedPreferences: SharedPreferences
) : BasePresenter<ChangeEventContract.View>(), ChangeEventContract.Presenter {

  private val sharedPRef = SharedPref(sharedPreferences)

  override fun resume() {
    super.resume()
    val disposable = getMyEventsUseCase.get()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.showLoading() }
        .doFinally { view.hideLoading() }
        .subscribe { events ->
          Timber.d(events.toString())
          view.showMyEvents(events)
        }
    disposables?.add(disposable)
  }

  override fun handleChosenEvent(observableEvent: Observable<Event>) {
    val disposable = observableEvent.subscribe { event ->
      sharedPRef.saveChosenEvent(event.id.let { it!! })
      view.dismissDialogFragment()
    }
    disposables?.add(disposable)
  }
}