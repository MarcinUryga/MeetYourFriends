package com.example.marcin.meetfriends.ui.login;

import dagger.Binds;
import dagger.Module;

/**
 * Created by MARCIN on 2017-11-13.
 */
@Module
public abstract class LoginModule {

  @Binds
  public abstract LoginContract.View bindView(LoginActivity view);

  @Binds
  public abstract LoginContract.Presenter bindPresenter(LoginPresenter presenter);
}
