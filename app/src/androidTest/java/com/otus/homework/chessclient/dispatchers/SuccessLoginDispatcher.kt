package com.otus.homework.chessclient.dispatchers

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class SuccessLoginDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse = MockResponse()
        .setResponseCode(200)
        .setBody("{\"accessToken\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE4OTg3MDI4MTAsInVzZXIiOiI3In0.L57pNo0-bT99zMIx9OMAZfWQ55XtJGP_AY0qJIPyla8\"}")
}