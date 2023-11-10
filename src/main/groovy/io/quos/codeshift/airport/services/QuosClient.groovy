package io.quos.codeshift.airport.services

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client

@Client("https://backend.quos.io/")
interface QuosClient {

    @Post("/duffel/search")
    Map search(@Body Map request)

    @Post("/airport/search")
    List<Map> airports(@Body Map request)
}
