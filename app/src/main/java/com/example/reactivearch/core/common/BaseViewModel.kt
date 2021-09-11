package com.example.reactivearch.core.common

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {

  private val compositeDisposable: CompositeDisposable = CompositeDisposable()

  override fun onCleared() {
    super.onCleared()
    if (!compositeDisposable.isDisposed)
      compositeDisposable.dispose()
  }
}