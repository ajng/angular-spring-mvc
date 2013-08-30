"use strict";
define(['todo/todo', 'test-util', 'restangular'], function(todoRegistration, testUtil) {

	describe('todos', function() {

		var $httpBackend;
		var Restangular;
		var model;
		var $scope = {};
		var serverTodo = { id : '42',
			text : 'sample',
			done : false
		};


		beforeEach(angular.mock.module("restangular"));

		beforeEach(inject(function($injector) {
			$httpBackend = $injector.get("$httpBackend");
			Restangular = $injector.get("Restangular");
			$scope = {};
			new todoRegistration.controller($scope, Restangular);
			model = $scope.model;
		}));

		describe("Before loading todos", function() {
			it("should set the loading flag", function() {
				expect(model.loading).toBe(true);
			})
		});

		describe("when error loading todos", function() {
			beforeEach(function() {
				$httpBackend.whenGET("/todos").respond(500, '');
				$httpBackend.flush();
			});

			it("should clear the loading flag", function() {
				expect($scope.model.loading).toBe(false);
			});

			it("should set an error message", function() {
				expect(model.error.length).toBeGreaterThan(0);
			});
		});

		describe("when todos loaded succesfully", function() {
			beforeEach(function() {
				$httpBackend.whenGET("/todos").respond([serverTodo ]);
				$httpBackend.flush();
			});

			it("should set the model from a rest request", function() {
				expect(testUtil.sanitizeRestangularAll($scope.model.todos)).toEqual([
					{ id : '42', text : 'sample', done : false}
				]);
			});

			it("should calculate the total correctly", function() {
				expect($scope.model.total()).toBe(1);

			});

			it("should clear the loading flag", function() {
				expect($scope.model.loading).toBe(false);
			});

			describe("when adding todos", function() {
				
				beforeEach(function() {
					$scope.todoForm.text = "Do Something";
					$scope.addTodo();
				});
				afterEach(function(){
					$httpBackend.verifyNoOutstandingExpectation();
				})

				it("should post the todo to the server", function() {
					$httpBackend.expectPOST("/todos", function(newTodo) {
						expect(angular.fromJson(newTodo)).toEqual({ "text" : "Do Something", "done" : false });
						return true;
					}).respond([]);
				});

				describe("and it fails", function() {
					beforeEach(function() {
						$httpBackend.whenPOST("/todos").respond(500, '');
						$httpBackend.flush();
					});

					it("should show error message", function() {
						expect(model.error.length).toBeGreaterThan(0);
					});

					it("should not add the todo", function() {
						expect(model.total()).toBe(1);
					});
				});

				describe("and it succeeds", function() {
					beforeEach(function() {
						$httpBackend.whenPOST("/todos").respond({id : 43, text : 'Do Something', done : false});
						$httpBackend.flush();
					});

					it("should update the total", function() {
						expect(model.total()).toBe(2);
					});
				});

			});
			describe("when updating todos", function(){
				var todo;
				beforeEach(function(){
					todo = model.todos[0];
					todo.text= 'Do Something Else';
					$scope.updateTodo(todo);
				});
				afterEach(function(){
					$httpBackend.verifyNoOutstandingExpectation();
				});

				it("should put the todo to the server", function() {
					$httpBackend.expectPUT("/todos/"+todo.id, function(todoBeingUpdated) {
						expect(angular.fromJson(todoBeingUpdated)).toEqual(testUtil.sanitizeRestangularOne(todo));
						return true;
					}).respond([]);
				});
				
				describe("and it succeeds", function(){
					beforeEach(function(){
						$httpBackend.whenPUT("/todos/"+todo.id).respond({ id: 42, text: 'Updated', done: false});
						$httpBackend.flush();
					});
					
					it("should replace the model with the one returned from the server", function(){
						expect(todo.text).toEqual('Updated');	
					});
				});
				
				describe("and it fails", function(){
					beforeEach(function() {
						$httpBackend.whenPUT("/todos/"+todo.id).respond(500, '');
						$httpBackend.flush();
					});

					it("should show error message", function() {
						expect(model.error.length).toBeGreaterThan(0);
					});
				});
			});
			
			describe("clearing completed", function(){
				var notDeletedTodo = { id:10, text: 'Todo that wasn\'t deleted', done: false};
				beforeEach(function(){
					model.todos.push(notDeletedTodo);
					$scope.clearCompleted();	
				});
				
				afterEach(function(){
					$httpBackend.verifyNoOutstandingExpectation();	
				});
				
				it("should try to delete /todos/completed", function(){
					$httpBackend.expectDELETE("/todos/completed").respond([]);
				});
				
				
				describe("and it succeeds", function(){
					beforeEach(function(){
						$httpBackend.whenDELETE("/todos/completed").respond([notDeletedTodo]);
						$httpBackend.flush();
					});
					
					it("should delete one of the todos", function(){
						expect(model.todos.length).toBe(1);	
					});
					
					it("should restangularize the returned todo item", function(){
						expect(notDeletedTodo.put).not.toBeUndefined();
					});
				});
				
				describe("and it fails", function(){
					beforeEach(function() {
						$httpBackend.whenDELETE("/todos/completed").respond(500, '');
						$httpBackend.flush();
					});

					it("should show error message", function() {
						expect(model.error.length).toBeGreaterThan(0);
					});
				});
				
				
			});
		});
	});
})
;