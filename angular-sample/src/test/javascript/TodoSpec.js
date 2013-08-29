define(['todo/todo'], function(todoRegistration){
	"use strict";
	
	describe('todos', function(){
		
		it('has a controller registration with correct name', function(){
			expect(todoRegistration.name).toBe("todos");
		});
	});
	
});