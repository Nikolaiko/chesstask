package com.otus.homework.chessclient.dispatchers

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class FailedLoginDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse = MockResponse().setStatus("404")
}