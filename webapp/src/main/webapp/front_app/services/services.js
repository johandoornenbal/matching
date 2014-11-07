'use strict';

/* Services */

angular.module('myApp.services', [])
.factory('restService', ['$q', '$http', function($q, $http, myMethod, myData){
	
	var isisRestObj = function(path, myMethod, myData){
		var isisObj = $q.defer();
		$http({
//			method: "GET",
			method: myMethod,
			url: path,
			data: myData,
			headers: {'Authorization': 'Basic c3ZlbjpwYXNz' }
		})
		.success(function(isisdata) {
			isisObj.resolve(isisdata);
		})
		.error(function(reason){
			isisObj.reject(reason);
		})

		return isisObj.promise;
	};
	return {isisRestObj : isisRestObj};
}])


// DEPRICATED isisHome factory - was for learning a lesson
.factory('isisHome', function($http){
	
	var isisHome = {};
	
	$http({
		method: "GET",
		url: '/restful/',
		headers: {'Authorization': 'Basic c3ZlbjpwYXNz' }
	}).
	success(function(isisdata, status) {
		isisHome.data = isisdata;
		isisHome.status = status;
	}).
	error(function(isisdata, status){
		isisHome.data = "mislukt";
		isisHome.status = status;
	});
	// IK snap niet dat ik niet een object binnen het object kan overdragen in de controller
	// ANTWOORD: ga gebruik maken van promises: zie hieronder in restService
	return isisHome;
});

