'use strict';
define(['text!./home.html','layout/default/default-layout'], function(template, layout){
	
	homeController.$inject=["scope"];
	function homeController($scope){
		$scope.model = {message : "Hello, I am a message from within home controller."};
	}
	
	homeController.$inject = ['$scope'];
	
	return {
		name: 'home',
		url: '/home',
		parent: layout,
		template: template,
		controller: homeController
	};
});