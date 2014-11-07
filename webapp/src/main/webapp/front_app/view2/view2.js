'use strict';

angular.module('myApp.view2', ['ngRoute', 'restangular'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view2', {
    templateUrl: 'view2/view2.html',
    controller: 'View2Ctrl'
  });
}])

.controller('View2Ctrl', function($scope, Restangular) {
	
	$scope.isistest = Restangular.all('/').getList('', {'Authorization': 'Basic c3ZlbjpwYXNz'}).$object;
});