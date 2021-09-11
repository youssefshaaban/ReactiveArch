package com.example.reactivearch.domain.entity.error

sealed class Failure {
  object NetworkConnection : Failure()
  sealed class ServerError:Failure() {
    object NotFound : ServerError()
    object ServiceUnavailable : ServerError()
    object AccessDenied : ServerError()
  }

  object UnknownError : Failure()
}