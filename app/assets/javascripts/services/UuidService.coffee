class UuidService

    @headers = {'Accept': 'application/json', 'Content-Type': 'application/json'}
    @defaultConfig = { headers: @headers }

    constructor: (@$log, @$http, @$q) ->
        @$log.debug "constructing UuidService"

    getUuid: () ->
        @$log.debug "getUuid()"
        @$http.get("/api/randomUUID").then((response) -> response.data)


servicesModule.service('uuidService', UuidService)