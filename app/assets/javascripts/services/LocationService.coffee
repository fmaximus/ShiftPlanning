class LocationService

    @headers = {'Accept': 'application/json', 'Content-Type': 'application/json'}
    @defaultConfig = { headers: @headers }

    constructor: (@$log, @$resource, @$q) ->
        @$log.debug "constructing LocationService"
        @Location = @$resource('/api/locations/:id', {id: '@id'}, { 'update': { method:'PUT' } });

    list: () ->
        @$log.debug "listLocations()"
        @Location.query().$promise
        
    get: (id) ->
        @$log.debug "getLocation()"
        @Location.get({id: id}).$promise    

    save: (location) ->
        @$log.debug "saveLocation() #{angular.toJson(location, true)}"
        resource = new @Location(location)
        resource.$save().$promise

    remove: (id) ->
        @$log.debug "removeLocation()"
        @Location.remove({id: id}).$promise   

servicesModule.service('locationService', LocationService)