'use strict';
//Defines the main angular module for the app. Using require.js you can import this to have access to the angular module.
define(['angular', 'angular/ui-router', 'angular/restangular', 'login/authentication' ], function(angular) {
	return angular.module('angular-sample', ['ui.state', 'restangular', 'authentication']).config(["RestangularProvider", function(RestangularProvider) {
		RestangularProvider.setBaseUrl("../");
	}]);
});
