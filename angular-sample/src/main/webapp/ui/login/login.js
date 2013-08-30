'use strict';
define(['text!./login.html', 'layout/authentication/auth-layout' ,'./authentication'], function(template, layout) {

	 loginController.$inject = ["$scope", "$http", "authenticationService"];
	 function loginController($scope, $http, authenticationService) {
		 var model = $scope.model = {
			 username : "",
			 password : ""
		 };
		 var self = this;

		 $scope.authenticate = function() {
			 var loginRequest = $http.post('../login', $scope.model);
			 loginRequest.success(function() {
				 authenticationService.onAuthenticated();
			 });

			 loginRequest.error(function() {
				 $scope.loginFailed = true;
			 });
		 };

	 }


	 return {
		 name : 'login',
		 url : '/login',
		 template : template,
		 controller : loginController,
		 parent : layout
	 };
 }

)
;
	
