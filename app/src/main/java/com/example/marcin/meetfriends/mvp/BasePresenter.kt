package com.example.marcin.meetfriends.mvp

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by marci on 2017-11-09.
 */
abstract class BasePresenter<V : MvpView> : MvpPresenter {

  @Inject lateinit var view: V
  protected var disposables: CompositeDisposable? = null

  override fun onViewCreated() {}

  override fun start() {}

  override fun resume() {
    disposables = CompositeDisposable()
  }

  override fun pause() {
    disposables?.clear()
    disposables = null
  }

  override fun stop() {}

  override fun destroy() {}
}