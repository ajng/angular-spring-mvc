'use strict';
define(['text!./todo.html', 'layout/default/default-layout', 'lib/lodash'], function(template, layout, _) {

	todoController.inject = ['$scope', 'Restangular']
	function todoController($scope, Restangular) {
		var model = $scope.model = {
			todos : [],
			loading : true,
			error : '',
			total : function() {
				return this.todos.length;
			}
		};

		var todoForm = $scope.todoForm = {
			text : ''
		}

		var Todos = Restangular.all('todos');

		function refreshTodos() {

			function onGotList(todos) {
				model.loading = false;
				model.todos = todos;
			}

			function onError() {
				model.loading = false;
				model.error = "Error loading todo items from server";
			}

			Todos.getList().then(onGotList, onError);
		}

		$scope.formTodoText = "";

		$scope.addTodo = function() {

			function onTodoAdded(todo) {
				model.todos.push(todo);
				model.error = '';
				todoForm.text = '';
			}

			function onAddFailed() {
				model.error = 'Failed to add todo item';
			}

			Todos.post({
				text : todoForm.text,
				done : false
			}).then(onTodoAdded, onAddFailed);

		};

		$scope.updateTodo = function(todo) {
			function onTodoUpdated(updatedTodo){
				_.extend(todo, updatedTodo);
			}
			
			function onUpdateFailed(){
				model.error = "Failed to update todo itemm in server";
			}
			todo.put().then(onTodoUpdated, onUpdateFailed);
		};
		
		function restangularizeTodoList(todos){
			var todos = _.map(todos, function(todo){
				return Restangular.restangularizeElement(null, todo, "todos");
			});
			return Restangular.restangularizeCollection(null, todos, "todos");
		}
		

		$scope.clearCompleted = function() {
			function onRemovedCompleted(newTodos) {
				model.todos = restangularizeTodoList(newTodos)
			}

			function onRemoveError() {
				model.error = "Error removing completed todos.";
			}

			Todos.one("completed").remove().then(onRemovedCompleted, onRemoveError);
		};

		refreshTodos();
	}

	return {
		name : 'todos',
		url : '/todos',
		parent : layout,
		template : template,
		controller : todoController
	};
});
