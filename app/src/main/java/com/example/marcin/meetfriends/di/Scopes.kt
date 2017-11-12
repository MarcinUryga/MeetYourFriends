package com.example.marcin.meetfriends.di

import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Created by marci on 2017-11-09.
 */

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ScreenScope

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext