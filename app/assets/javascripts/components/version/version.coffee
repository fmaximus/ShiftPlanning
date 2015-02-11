angular.module('ShiftPlanning.version', [
  'ShiftPlanning.version.interpolate-filter',
  'ShiftPlanning.version.version-directive'
])
.value('version', '0.1');

angular.module('ShiftPlanning.version.version-directive', [])
.directive('appVersion', ['version', (version) ->
        (scope, elm, attrs) ->
            elm.text(version)
    ])

angular.module('ShiftPlanning.version.interpolate-filter', [])
.filter('interpolate', ['version', (version) ->
        (text) -> 
            String(text).replace(/\%VERSION\%/mg, version)
    ])