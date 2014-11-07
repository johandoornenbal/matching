'use strict';

angular.module('myApp.view3', ['ngRoute', 'restangular'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view3', {
    templateUrl: 'view3/view3.html',
    controller: 'View3Ctrl'
  });
}])

.controller('View3Ctrl', function($scope, Restangular) {
	
	$scope.isistest = Restangular.all('services/Persons').getList('links', {'Authorization': 'Basic c3ZlbjpwYXNz'}).$object;
});