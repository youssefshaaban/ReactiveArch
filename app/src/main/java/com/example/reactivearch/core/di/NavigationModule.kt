package com.example.reactivearch.core.di

import com.example.reactivearch.core.navigation.AppNavigator
import com.example.reactivearch.core.navigation.AppNavigatorImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Named

@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModule {
  @Binds
  abstract fun bindAppNavigator(navigator: AppNavigatorImp): AppNavigator
}