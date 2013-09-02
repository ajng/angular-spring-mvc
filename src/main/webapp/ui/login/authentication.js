'use strict';
define(['angular', 'angular/ui-router'], function(angular) {


	routeToLoginOnAuthFail.$inject = ['$q', 'authenticationService']
	function routeToLoginOnAuthFail($q, authenticationService) {

		function onSuccess(response) {
			return response;
		}

		function onError(response) {
			if (response.status === 401) {
				authenticationService.login();
			}
			return $q.reject(response);
		}

		return function(promise) {
			return promise.then(onSuccess, onError);
		}
	}

	var module = angular.module('authentication', ['ui.state']);
	module.service("authenticationService", ["$injector", "$rootScope", function($injector, $rootScope) {
		
		var lastStartedState = null;
		$rootScope.$on("$stateChangeStart", function(event, toState){
			if (toState.name !== 'login' && toState.name !== 'add-user') {
				lastStartedState = toState;
			}
		});
		
		return {
			onAuthenticated : function() {
				var $state = $injector.get('$state');
				if (lastStartedState) {
					$state.transitionTo(lastStartedState);
					lastStartedState = null;
				}
				else{
					$state.transitionTo("home");
				}
				$rootScope.isLoggedIn = true;
			},
			login: function(){
				var $state = $injector.get('$state');
				$state.transitionTo("login");
			},
			logout: function(){
				$injector.get('$http').get("../logout");
				$injector.get('$state').transitionTo("login");
				$rootScope.isLoggedIn = false;
			}
		}
	}]);
	
	module.config(['$httpProvider', function($httpProvider) {
		$httpProvider.responseInterceptors.push(routeToLoginOnAuthFail);
	}]);

});
