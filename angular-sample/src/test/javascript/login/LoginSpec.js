"use strict";
define(['login/login', 'angular/mocks'], function(loginRegistration) {


	describe("login", function() {

		var $httpBackend;
		var $scope;
		var model;
		var controller;
		var authService;

		beforeEach(function() {
			authService = {onAuthenticated : jasmine.createSpy()};
			angular.mock.module(function($provide) {
				$provide.value('authenticationService', authService);
			});
		});

		beforeEach(inject(function($injector) {
			$httpBackend = $injector.get("$httpBackend");
			var $http = $injector.get("$http");
			$scope = {};
			controller = new loginRegistration.controller($scope, $http, authService);
			model = $scope.model;
		}));

		describe("when authenticating", function() {
			beforeEach(function() {
				model.username = "jdoe";
				model.password = "mypass";
				$scope.authenticate();
			});

			afterEach(function() {
				$httpBackend.verifyNoOutstandingExpectation();
			});

			it("should call post username and password to the login resource", function() {
				$httpBackend.expectPOST('../login',function(postedModel) {
					expect(angular.fromJson(postedModel)).toEqual(model);
					return true;
				}).respond("OK");
			});
			
			describe("and it succeeds", function(){
				beforeEach(function(){
					$httpBackend.whenPOST("../login").respond(200, 'OK');
					$httpBackend.flush();
				});
				
				it("should notify the authentication service", function(){
					expect(authService.onAuthenticated).toHaveBeenCalled();
				});
			});

			describe("and it fails", function() {
				beforeEach(function() {
					$httpBackend.whenPOST('../login').respond(401, 'FAILURE');
					$httpBackend.flush();
				});

				it("should set login failed flag", function() {
					expect($scope.loginFailed).toBe(true);
				});

				it("should not notify the authentication service", function() {
					expect(authService.onAuthenticated).not.toHaveBeenCalled();
				});
			});
		});

	});

});
