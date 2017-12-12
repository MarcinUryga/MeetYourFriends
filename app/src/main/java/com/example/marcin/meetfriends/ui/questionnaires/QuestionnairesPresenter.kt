package com.example.marcin.meetfriends.ui.questionnaires

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import javax.inject.Inject

/**
 * Created by marci on 2017-12-12.
 */
@ScreenScope
class QuestionnairesPresenter @Inject constructor(
    private val getFilledQuestionnairesUseCase: GetFilledQuestionnairesUseCase
) : BasePresenter<QuestionairesContract.View>(), QuestionairesContract.Presenter {

  override fun resume() {
    super.resume()
    loadFilledQuestionnaires()
  }

  fun loadFilledQuestionnaires() {
    val disposable = getFilledQuestionnairesUseCase.get().subscribe()
    disposables?.add(disposable)
  }
}