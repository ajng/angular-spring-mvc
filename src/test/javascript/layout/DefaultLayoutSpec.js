"use strict";
define(['layout/default/default-layout', 'angular/mocks', 'login/authentication'], function(defaultLayoutRegistration) {


	describe("login", function() {

		var $scope;
		var $state;
		var $rootScope;
		var controller;
		var authService;

		beforeEach(function($injector) {
			authService = { logout : jasmine.createSpy()};
			angular.mock.module("authentication", function($provide) {
				$provide.value('authenticationService', authService);
			});
		});

		beforeEach(inject(function($injector) {
			$state = $injector.get("$state");
			$rootScope = $injector.get("$rootScope");
			$scope = $rootScope.$new();
			controller = new defaultLayoutRegistration.controller($scope, $state, authService);
		}));

		describe("logout", function() {
			beforeEach(function() {
				$scope.logout();
			});

			it("should logout", function() {
				expect(authService.logout).toHaveBeenCalled();
			});
		});

		describe("goTo", function() {
			beforeEach(function() {
				spyOn($state, "transitionTo");
				$scope.goTo("newState");
			})

			it("should transition to the new state", function() {
				expect($state.transitionTo).toHaveBeenCalledWith("newState");
			});
		})

		describe("on starting a state changes", function() {
			beforeEach(function() {
				$scope.$broadcast("$stateChangeStart", {name: 'anotherState'});
			});
			
			it("should set the loadingState flag", function(){
				expect($scope.loadingState).toBe(true);	
			});
		});
		
		describe("on successful state change", function() {
			beforeEach(function() {
				$scope.$broadcast("$stateChangeSuccess", {name: 'anotherState'});
			});

			it("should unset the loadingState flag", function(){
				expect($scope.loadingState).toBe(false);
			});
		});
		
		describe("on error during state change", function() {
			beforeEach(function() {
				spyOn(window, "alert");
				$scope.$broadcast("$stateChangeError", {name: 'anotherState'});
			});

			it("should unset the loadingState flag", function(){
				expect($scope.loadingState).toBe(false);
			});
			
			it("should alert with the failure", function(){
				expect(window.alert).toHaveBeenCalledWith("Error Going to anotherState");	
			});
		});
	});
});


