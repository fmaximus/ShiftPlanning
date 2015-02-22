angular.module('ShiftPlanning.physicians', ['ngRoute'])
.config(['$routeProvider', ($routeProvider) ->
  $routeProvider
    .when('/physicians',              { templateUrl: '/assets/partials/physicians/view.html' })
    .when('/physicians/:id/absences', { templateUrl: '/assets/partials/absences/view.html'   })
])
            
.service 'physicianService', 
    class PhysicianService
        @headers = {'Accept': 'application/json', 'Content-Type': 'application/json'}
        @defaultConfig = { headers: @headers }

        constructor: (@$log, @$resource, @$q) ->
            @$log.debug "constructing PhysicianService"
            @Resource = @$resource('/api/physicians/:id', {}, { 'update': { method:'PUT' } })

        list: () ->
            @$log.debug "listPhysicians()"
            @Resource.query().$promise

        get: (_id) ->
            @$log.debug "getPhysician()"
            @Resource.get({id: _id}).$promise    

        save: (physician) ->
            @$log.debug "createPhysician() #{angular.toJson(physician, true)}"
            resource = new @Resource(physician)
            rs = resource.$save()

        remove: (id) ->
            @$log.debug "removePhysician()"
            @Resource.remove({id: id}).$promise                     

.controller 'ListPhysicianCtrl', 
    class ListPhysicianCtrl
        constructor: (@$log, @$scope, @$modal, @physicianService) ->
            @$scope.physicians = []
            @$log.debug "list()"
            @list()
            
        list: () ->    
            @physicianService.list()
            .then(
                (data) =>
                    @$log.debug "Promise returned #{data.length} Physicians"
                    @$scope.physicians = data
                ,
                (error) =>
                    @$log.error "Unable to get Physicians: #{error}"
             )

        create: () ->
            modalInstance = @$modal.open({
                controller: 'SavePhysicianCtrl', 
                templateUrl: '/assets/partials/physicians/edit.html',
                # size: "lg",
                resolve: {
                    physician: () -> {  }
                    action: () -> "Create"
                }
            })

            modalInstance.result.then(() => @list())
    
        edit: (id) ->
            @physicianService.get(id).then(
                (data) =>
                    modalInstance = @$modal.open({
                        controller: 'SavePhysicianCtrl', 
                        templateUrl: '/assets/partials/physicians/edit.html',
                        # size: "lg",
                        resolve: {
                            physician: () -> data
                            action: () -> "Edit"
                        }
                    })

                    modalInstance.result.then(
                        () => @list(),
                        () => @$log.info('Modal dismissed at: ' + new Date());
                    )
            )

.controller 'SavePhysicianCtrl',
    class SavePhysicianCtrl
        constructor: ($log, $scope, physicianService, $modalInstance, physician, action) ->
            $log.debug "constructing SavePhysicianCtrl"
            $scope.physician = physician
            $scope.action = action
            $scope.weekdays = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]

            $scope.save = () ->
                if ($scope.physician.function != "Attending" || !$scope.partTime)
                    delete $scope.physician.partTime

                $log.debug "createPhysician(#{angular.toJson($scope.physician, true)})"
                promise = physicianService.save($scope.physician)
                promise.then(
                    (data) =>
                        $log.debug "Promise returned #{data} Physician"
                        $modalInstance.close();
                    , (error) =>
                            $log.error "Unable to create Physician: #{error}"
                )

            $scope.cancel = () ->
                $modalInstance.dismiss('cancel')

