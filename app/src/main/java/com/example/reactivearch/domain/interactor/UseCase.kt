package com.example.reactivearch.domain.interactor

interface UseCase<T, R> {
  fun execute(param: T): R
}