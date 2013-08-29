'use strict';
define(['text!./default.html'], function(template) {

	defaultLayoutController.$inject = ['$scope', '$state', 'authenticationService'];
	function defaultLayoutController($scope, $state, authenticationService) {
		$scope.$state = $state;
		$scope.logout = function() {
			authenticationService.logout();
		}


		$scope.goTo = function(stateName) {
			$state.transitionTo(stateName);
		}

		$scope.$on("$stateChangeStart", function(event, toState) {
			$scope.loadingState=true;
			console.log("Start change to " + toState.name);
		});
		$scope.$on("$stateChangeSuccess", function(event, toState) {
			$scope.loadingState=false;
			console.log("Success change to " + toState.name);
		});
		$scope.$on("$stateChangeError", function(event, toState, params, fromState) {
			$scope.loadingState=false;
			console.log("Error change to " + toState.name);
			alert("Error Going to " + toState.name);

		});
	}

	return {
		name : 'default',
		abstract : true,
		controller : defaultLayoutController,
		template : template
	};
})
;