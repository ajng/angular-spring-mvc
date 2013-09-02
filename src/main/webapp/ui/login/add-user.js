'use strict';
define(['text!./add-user.html', 'layout/authentication/auth-layout'], function(template, layout) {

	addUserController.inject = ['$scope', 'Restangular']
	function addUserController($scope, Restangular) {

		var model = $scope.model = {
			username : '',
			password : '',
			confirmPassword : ''
		}

		var message = $scope.message = {
			hasMessage : false,
			text : '',
			class : ''
		}

		function clearMessage() {
			message.hasMessage = false;
			message.text = '';
			message.type = '';
		}

		function showMessage(text, type) {
			message.hasMessage = true;
			message.text = text;
			message.type = type
		}

		var LoginRequest = Restangular.all('login/add-user');

		$scope.createUser = function() {
			if (model.password != model.confirmPassword) {
				showMessage("Passwords entered do not match!", "error");
				return;
			}
			clearMessage();

			function onUserAdded() {
				showMessage("User '" + model.username + "' added", "success");
				model.username = '';
				model.password = '';
				model.confirmPassword = '';
			}

			function onAddFailure() {
				showMessage("Error adding user...", "error");
			}

			LoginRequest.post({username : model.username, password : model.password })
			    .then(onUserAdded, onAddFailure);
		}

	}

	return {
		name : 'add-user',
		url : '/login/add-user',
		template : template,
		controller : addUserController,
		parent : layout
	};
});
