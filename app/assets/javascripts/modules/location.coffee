angular.module('ShiftPlanning.locations', ['ngRoute'])
.config(['$routeProvider', ($routeProvider) ->
  $routeProvider
    .when('/locations',           { templateUrl: '/assets/partials/locations/view.html' })
])
            
.service 'locationService', 
    class LocationService
        @headers = {'Accept': 'application/json', 'Content-Type': 'application/json'}
        @defaultConfig = { headers: @headers }

        constructor: (@$log, @$resource, @$q) ->
            @$log.debug "constructing LocationService"
            @Resource = @$resource('/api/locations/:id', {id: '@_id'}, { 'update': { method:'PUT' } })

        list: () ->
            @$log.debug "listLocations()"
            @Resource.query().$promise

        get: (_id) ->
            @$log.debug "getLocation()"
            @Resource.get({id: _id}).$promise    

        save: (location) ->
            @$log.debug "createLocation() #{angular.toJson(location, true)}"
            resource = new @Resource(location)
            if (location.id)
                resource.$update().$promise
            else
                resource.$save().$promise

        remove: (id) ->
            @$log.debug "removeLocation()"
            @Resource.remove({id: id}).$promise                     

.controller 'ListLocationCtrl', 
    class ListLocationCtrl
        constructor: (@$log, @$scope, @$modal, @locationService) ->
            @$scope.locations = []
            @$log.debug "list()"
            @list()
            
        list: () ->    
            @locationService.list()
            .then(
                (data) =>
                    @$log.debug "Promise returned #{data.length} Locations"
                    @$scope.locations = data
                ,
                (error) =>
                    @$log.error "Unable to get Locations: #{error}"
             )
    
        create: () ->
            modalInstance = @$modal.open({
                controller: 'SaveLocationCtrl', 
                templateUrl: '/assets/partials/locations/edit.html',
                size: "lg",
                resolve: {
                    location: () -> { "external": false }
                    action: () -> "Create"
                }
            })

            modalInstance.result.then(() -> @list())
    
        edit: (id) ->
            @locationService.get(id).then(
                (data) =>
                    modalInstance = @$modal.open({
                        controller: 'SaveLocationCtrl', 
                        templateUrl: '/assets/partials/locations/edit.html',
                        size: "lg",
                        resolve: {
                            location: () -> data
                            action: () -> "Edit"
                        }
                    })

                    modalInstance.result.then(
                        () -> @list(),
                        () -> @$log.info('Modal dismissed at: ' + new Date());
                    )
            )

.controller 'SaveLocationCtrl',
    class SaveLocationCtrl
        constructor: ($log, $scope, $location, locationService, $modalInstance, location, action) ->
            $log.debug "constructing SaveLocationCtrl"
            $scope.location = location
            $scope.action = action

            $scope.save = () ->
                $log.debug "createLocation(#{angular.toJson($scope.location, true)})"
                locationService.save($scope.location).then(
                    (data) =>
                        $log.debug "Promise returned #{data} Location"
                        $modalInstance.close();
                    , (error) =>
                            $log.error "Unable to create Location: #{error}"
                )

            $scope.cancel = () ->
                $modalInstance.dismiss('cancel')

