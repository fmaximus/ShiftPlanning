dependencies = [
    'ngRoute',
    'ngResource'
    'ui.bootstrap',
    'checklist-model',
    'ShiftPlanning.locations',
    'ShiftPlanning.physicians',
    'ShiftPlanning.services',
    'ShiftPlanning.version'
]

angular
    .module('ShiftPlanning', dependencies)
    .config ($routeProvider) ->
        $routeProvider
            .otherwise({redirectTo: '/locations'})
    .config ($locationProvider) ->
        $locationProvider.html5Mode({
            enabled: true,
            requireBase: false
        })

@servicesModule = angular.module('ShiftPlanning.services', ['ngResource'])