package io.quos.codeshift.airport.services

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn

@Client(id="gateway")
@ExecuteOn(TaskExecutors.IO)
interface QuosClient {
    @Post("/duffel/search")
    Map search(@Body Map request)

    @Post("/airport/search")
    List<Map> airports(@Body Map request)

    @Post("/advertising/adsForContext")
    List<Map> adsForContext(@Body Map request)
}
