package com.graphybyte.githubtrendingrepo.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserBaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserApiRetrofitInstance
