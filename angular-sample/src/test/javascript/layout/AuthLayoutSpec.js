"use strict";
define(['layout/authentication/auth-layout'], function(authLayoutRegistration) {


	describe("auth layout", function() {


		var state = {}
		var scope = {}
		beforeEach(function() {
			new authLayoutRegistration.controller(scope, state);
		});

		it("should make the state available in the scope", function() {
			expect(scope.$state).toBe(state);
		});
	});
});


