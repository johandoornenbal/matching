'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'restangular',
  'myApp.services',
  'myApp.view1',
  'myApp.view2',
  'myApp.view3',
  'myApp.view4',
  'myApp.view5',
  'myApp.version'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/view1'});
}])
.config(function(RestangularProvider) {
    RestangularProvider.setBaseUrl('/restful');
  })
.config(function(RestangularProvider) {

    // add a response interceptor
    RestangularProvider.addResponseInterceptor(function(data, operation, what, url, response, deferred) {
      var extractedData;
      // .. to look for getList operations
      if (operation === "getList") {
        // .. and handle the data and meta data
        extractedData = data.value;      
        if (!extractedData) {
        	extractedData = data.links;
        }
//        extractedData.links = data.links;
//        extractedData.meta = data.data.meta;
      } 
      else {
        extractedData = data.data;
      }
      return extractedData;
    });

});  

