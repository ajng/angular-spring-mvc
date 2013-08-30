"use strict";
define(['login/authentication', 'angular/mocks'], function(loginRegistration) {


	describe("authentication service", function() {

		var $rootScope;
		var authService;
		var $state;
		var $httpBackend;
		var $http;

		beforeEach(function() {
			angular.mock.module("authentication");
		});

		beforeEach(inject(function($injector) {
			authService = $injector.get("authenticationService");
			$rootScope = $injector.get("$rootScope");
			$state = $injector.get("$state");
			$httpBackend = $injector.get("$httpBackend");
			$http = $injector.get("$http");
			spyOn($state, "transitionTo");
		}));

		afterEach(function() {
		});


		describe("when login called", function() {
			beforeEach(function() {
				authService.login();
			});

			it("should transition to the login state", function() {
				expect($state.transitionTo).toHaveBeenCalledWith("login");
			});
		});

		describe("when logout called", function() {
			beforeEach(function() {
				$httpBackend.whenGET("../lgout").respond("OK");
				authService.logout();
			});


			it("should transition to the login state again", function() {
				expect($state.transitionTo).toHaveBeenCalledWith("login");
			});

			it("should hit the logout action on the server", function() {
				$httpBackend.expectGET("../logout").respond("OK");
				$httpBackend.verifyNoOutstandingExpectation()
			});

			it("should clear the logged in flag", function() {
				expect($rootScope.isLoggedIn).toBe(false);
			});

		});

		describe("when on authenticated called  with no prior state", function() {
			beforeEach(function() {
				authService.onAuthenticated();
			});

			it("should transition to the home state", function() {
				expect($state.transitionTo).toHaveBeenCalledWith("home");
			});

			it("should set the logged in flag", function() {
				expect($rootScope.isLoggedIn).toBe(true);
			});
		});
		
		describe("when on authenticated called after a prior state was navigated to", function() {
			
			beforeEach(function() {
				$rootScope.$broadcast("$stateChangeStart", {name: "navigated-state"});
				authService.onAuthenticated();
			});

			it("should transition to the state that had been started before", function() {
				expect($state.transitionTo).toHaveBeenCalledWith({name: "navigated-state"});
			});
		});
		
		describe("when navigating directly to login after navigating somewhere else", function() {

			beforeEach(function() {
				$rootScope.$broadcast("$stateChangeStart", {name: "somewhere-else"});
				$rootScope.$broadcast("$stateChangeStart", {name: "login"});
				authService.onAuthenticated();
			});

			it("should transition to the state that had been started before login", function() {
				expect($state.transitionTo).toHaveBeenCalledWith({name: "somewhere-else"});
			});
		});
		
		describe("when navigating directly to add-user after navigating somewhere else", function() {

			beforeEach(function() {
				$rootScope.$broadcast("$stateChangeStart", {name: "somewhere-else"});
				$rootScope.$broadcast("$stateChangeStart", {name: "add-user"});
				authService.onAuthenticated();
			});

			it("should transition to the state that had been started before login", function() {
				expect($state.transitionTo).toHaveBeenCalledWith({name: "somewhere-else"});
			});
		});
		
		describe("when navigating to more than one place before being authenticated", function() {

			beforeEach(function() {
				$rootScope.$broadcast("$stateChangeStart", {name: "somewhere-else"});
				$rootScope.$broadcast("$stateChangeStart", {name: "last-place"});
				authService.onAuthenticated();
			});

			it("should transition to the last state navigated to before login", function() {
				expect($state.transitionTo).toHaveBeenCalledWith({name: "last-place"});
			});
		});


		describe("when navigating somewhere succesfully", function(){
			beforeEach(function(){
				$httpBackend.whenGET("/success").respond(200, "OK");
				$http.get("/success");
				$httpBackend.flush();
				
			});
			
			it("should not automatically navigate anywhere", function(){
				expect($state.transitionTo).not.toHaveBeenCalled();
			});
		});
		
		
		
		
		describe("when navigating to a resource encounters an error other than a 401", function(){
			beforeEach(function(){
				$httpBackend.whenGET("/fail").respond(500, "ERROR");
				$http.get("/fail");
				$httpBackend.flush();
			});

			it("should not automatically navigate anywhere", function(){
				expect($state.transitionTo).not.toHaveBeenCalled();
			});
		});
		
		describe("when navigating to an unauthorized resource", function(){
			beforeEach(function(){
				$httpBackend.whenGET("/secure").respond(401, "UNAUTHORIZED");
				$http.get("/secure");
				$httpBackend.flush();
			});

			it("should navigate to the login", function(){
				expect($state.transitionTo).toHaveBeenCalledWith("login");
			});
		});


	});
});
