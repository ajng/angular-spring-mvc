'use strict';
define(function(require) {
	
	var mainModule = require('main/angular-module');
	mainModule.config(["$stateProvider", "$urlRouterProvider", function($stateProvider, $urlRouterProvider) {
		
		function registerControllers(controllers){
			if(!_.isArray(controllers)){
				controllers = [controllers];
			}
			_(controllers).forEach(function(controller){
				$stateProvider.state(controller);	
			});
		}
		
		$urlRouterProvider.otherwise("/home");
		
		registerControllers(require('layout/default/default-layout'));
		registerControllers(require('layout/authentication/auth-layout'));
		registerControllers(require('home/home'));
		registerControllers(require('todo/todo'));
		registerControllers(require('login/login'));
		registerControllers(require('login/add-user'));
	}]);
});
