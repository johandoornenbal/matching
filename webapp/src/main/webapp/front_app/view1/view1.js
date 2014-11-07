'use strict';

angular.module('myApp.view1', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view1', {
    templateUrl: 'view1/view1.html',
    controller: 'View1Ctrl'
  });
}])

.controller('View1Ctrl', function ISISwww($scope, $http) {
	$http({
		method: "POST",
		url: '/restful/services/Persons/actions/newPerson/invoke',
//		data: { "referentie" : { "value" : "REST3" }, "voorletter(s)" : { "value" : "R" }, "achternaam" : { "value" : "REST" }, "doopnaam" : { "value" : "Restfullio" }, "geslacht" : { "value" : "MALE" }, "geboortedatum" : { "value" : "1990-10-10" }, "geboorteplaats" : { "value" : "RESTPLACE" }, "nationaliteit" : { "value" : "RESTLANDER" } },
//		url: 'http://socrates.johandoornenbal.eu.cloudbees.net/restful/objects/nl.socrates.dom.party.Person/L_1',
		headers: {'Authorization': 'Basic c3ZlbjpwYXNz' }
	}).
	success(function(isisdata, status) {
		$scope.isisdata = isisdata;
		$scope.status = status;
	}).
	error(function(isisdata, status){
		$scope.isisdata = "mislukt";
		$scope.status = status;
	});
});
