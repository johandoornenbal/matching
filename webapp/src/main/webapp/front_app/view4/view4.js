'use strict';

angular.module('myApp.view4', ['ngRoute', 'myApp.services'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view4', {
    templateUrl: 'view4/view4.html',
    controller: 'View4Ctrl'
  });
}])
.controller('View4Ctrl', function($scope, restService) {
	restService.isisRestObj('/restful/','GET').then(function(data){
		$scope.homelinks = data.links;
		$scope.homeextensions = data.extensions;
	});
	restService.isisRestObj('/restful/services','GET').then(function(data){
		$scope.servicesObj = data;
	});
	restService.isisRestObj('/restful/services/Persons','GET').then(function(data){
		$scope.personsObj = data;
	});
	restService.isisRestObj('/restful/services/Persons/actions/allPersons/invoke','GET').then(function(data){
		$scope.allpersonsObj = data;
	});
});
