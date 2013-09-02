'use strict';
define(['text!./auth-layout.html'], function(template) {

	authLayoutController.inject = ['$scope, $state']
	function authLayoutController($scope, $state) {
		$scope.$state = $state;
	}

	return {
		name : 'authentication',
		abstract : true,
		controller : authLayoutController,
		template : template
	};
})
;
