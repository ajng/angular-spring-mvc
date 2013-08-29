require.config({
	baseUrl: "../ui",
	paths: {
		'jquery': 'lib/jquery',
		'angular': 'lib/angular/angular',
		'angular/ui-router': 'lib/angular/angular-ui-router',
		'angular/resource': 'lib/angular/angular-resource',
		'angular/restangular': 'lib/angular/restangular',
		'text': 'lib/text'
	},
	shim: {
		'angular' : {exports : 'angular'},
		'angular/resource': ['angular'],
		'angular/ui-router' : ['angular'],
		'angular/restangular': ['angular', 'lib/lodash']
	}
});
require(["main/angular-start"], function(){});
