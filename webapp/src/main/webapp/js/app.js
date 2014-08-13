// working: www json test
function Hello($scope, $http) {
	$http({
			method: "GET",
			url: 'http://rest-service.guides.spring.io/greeting'
	}).
    success(function(data) {
    	$scope.greeting = data;
    });
};


// working: I have saved http://localhost:8080/restful/services to services.json file
function ISISfile($scope, $http) {
	$http({
			method: "GET",
			url: 'services.json'
	}).
    success(function(isisdata) {
        $scope.isisdata = isisdata;
    });
};

//working; relative call without http://localhost\\:8080
function ISISrel($scope, $http) {
	$http({
			method: "GET",
			url: '/restful/services',
			headers: {'Authorization': 'Basic c3ZlbjpwYXNz'}
	}).
    success(function(isisdata) {
        $scope.isisdata = isisdata;
    });
};

//working; so authorization is not the problem
function ISISrelnoauth($scope, $http) {
	$http({
			method: "GET",
			url: '/restful/services'
	}).
    success(function(isisdata) {
        $scope.isisdata = isisdata;
    });
};

// NOT working
// I tried 'http://localhost:8080/restful/services' and
// http://localhost\\:8080/restful/services and
// http://mmyco.co.uk:8180/isis-onlinedemo/restful/services and
// http://mmyco.co.uk\\:8180/isis-onlinedemo/restful/services and
function ISISwww($scope, $http) {
	$http({
			method: "GET",
			url: 'http://localhost\\:8080/restful/services',
			headers: {'Authorization': 'Basic c3ZlbjpwYXNz'}
	}).
    success(function(isisdata) {
        $scope.isisdata = isisdata;
    });
};