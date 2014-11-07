'use strict';

angular.module('myApp.view5', ['ngRoute', 'myApp.services'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view5', {
    templateUrl: 'view5/view5.html',
    controller: 'View5Ctrl'
  });
}])
.controller('View5Ctrl', function($scope, restService) {
	restService.isisRestObj('/restful/services/Persons/actions/allPersons/invoke','GET').then(function(data){
		$scope.allpersonsObj = data.result.value;
		$scope.Person_Title = data.result.value[0].title;
	});
	// init of details
	restService.isisRestObj('/restful/objects/nl.socrates.dom.party.Person/L_1','GET').then(function(data){
		$scope.Person_Obj = data;
	});
	$scope.showMember = function(memberType){
		if (memberType === 'action'){
			return false;
		}
		else{
			return true;
		}
	};
	$scope.detailAction = function(path, title){
		$scope.pathchosen = path;
		restService.isisRestObj(path,'GET').then(function(data){
			$scope.Person_Obj = data;
		});
		$scope.Person_Title = title;
	};
	// get the fields for 'new person invoke action'
	restService.isisRestObj('/restful/services/Persons/actions/newPerson','GET').then(function(data){
		$scope.NewPerson_Obj = data;
	});
	
}).
controller("AddPersonController", function(restService){
	this.addPerson = {};
	this.addPers = function(){
		//lege waarde afvangen van niet verplicht veld
		if (this.addPerson.tussen === undefined){this.addPerson.tussen = '';}
		var myData = 
			'{"referentie": {"value" : "' 
			+ this.addPerson.referentie
			+ '"}, "voornaam": {"value" : "'
			+ this.addPerson.voornaam
			+ '"}, "tussen": {"value" : "'
			+ this.addPerson.tussen
			+ '"}, "achternaam": {"value" : "'
			+ this.addPerson.achternaam
			+ '"}, "geslacht": {"value" : "'
			+ this.addPerson.geslacht
			+ '"}, "geboortedatum": {"value" : "'
			+ this.addPerson.geboortedatum
			+ '"}, "geboorteplaats": {"value" : "'
			+ this.addPerson.geboorteplaats
			+ '"}, "nationaliteit": {"value" : "'
			+ this.addPerson.nationaliteit
			+ '"}}';
		
		this.NewPersonResp_Obj = myData;
		
		restService.isisRestObj('/restful/services/Persons/actions/newPerson/invoke','POST', myData)
		.then(function(data, status){
			alert("Verzonden");
		});
		
		this.addPerson = {}; // Clear the form
	};
	
});
