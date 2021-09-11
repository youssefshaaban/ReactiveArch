package com.example.reactivearch.domain.entity.error

interface ErrorHandler {
  fun getError(throwable: Throwable):Failure
}