package com.example.marcin.meetfriends.mvp

/**
 * Created by marci on 2017-11-09.
 */
interface MvpPresenter {

  fun onViewCreated()

  fun start()

  fun resume()

  fun pause()

  fun stop()

  fun destroy()
}