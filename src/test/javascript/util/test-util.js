"use strict";
define(['todo/todo', 'angular', 'restangular'], function(todoRegistration, angular) {
	return {

		// Apply "sanitizeRestangularOne" function to an array of items
		sanitizeRestangularAll : function sanitizeRestangularAll(items) {
			var self = this;
			var all = _.map(items, function(item) {
				return self.sanitizeRestangularOne(item);
			});
			return self.sanitizeRestangularOne(all);
		},

		// Remove all Restangular/AngularJS added methods in order to use Jasmine toEqual between the retrieve resource and the model
		sanitizeRestangularOne : function(item) {
			return _.omit(item, "route", "parentResource", "getList", "get", "post", "put", "remove", "head", "trace", "options", "patch",
			 "$then", "$resolved", "restangularCollection", "customOperation", "customGET", "customPOST",
			 "customPUT", "customDELETE", "customGETLIST", "$getList", "$resolved", "restangularCollection", "one", "all", "doGET", "doPOST",
			 "doPUT", "doDELETE", "doGETLIST", "addRestangularMethod", "getRestangularUrl");
		}
	}
});
