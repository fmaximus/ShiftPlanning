angular.module('ShiftPlanning.locations', ['ngRoute'])
.config(['$routeProvider', ($routeProvider) ->
  $routeProvider
    .when('/locations',           { controller: 'ListLocationCtrl', templateUrl: '/assets/partials/locations/view.html' })
    .when('/locations/create',    { controller: 'SaveLocationCtrl', templateUrl: '/assets/partials/locations/create.html' })
    .when('/locations/edit/:id',  { controller: 'SaveLocationCtrl', templateUrl: '/assets/partials/locations/update.html' })
])

.controller 'ListLocationCtrl', (@$log, @$scope, @locationService) ->
    @$scope.locations = []
    @$log.debug "list()"

    @locationService.list()
    .then(
        (data) =>
            @$log.debug "Promise returned #{data.length} Users"
            @$scope.locations = data
        ,
        (error) =>
            @$log.error "Unable to get Users: #{error}"
     )

.controller 'SaveLocationCtrl',
    class SaveLocationCtrl
        constructor: (@$log, @$scope, @$location,  @locationService, @uuidService) ->
            @$log.debug "constructing CreateUserController"
            @$scope.location = {}

        createLocation: (location) ->
            @$log.debug "createLocation(#{angular.toJson(@location, true)})"
            @uuidService.getUuid().then(
                (uuid) =>
                    @$log.debug "Promise returned UUID #{uuid}"
                    @location.id = uuid
                    @locationService.save(@location).then(
                        (data) =>
                            @$log.debug "Promise returned #{data} Location"
                            @$scope.location = data
                            @$location.path("/locations")
                        , (error) =>
                            @$log.error "Unable to create Location: #{error}"
                    )
                ,
                (error) =>
                    @$log.error "Unable fetching UUID: #{error}"
            )
            
