var tests = [];
for (var file in window.__karma__.files) {
	if (window.__karma__.files.hasOwnProperty(file)) {
		if (/Spec\.js$/.test(file)) {
			tests.push(file);
		}
	}
}


requirejs.config({
	// Karma serves files from '/base'
	baseUrl : "/base/src/main/webapp/ui",

	paths : {
		'jquery' : '../lib/jquery',
		'underscore' : '../../lib/underscore'
	},

	paths : {
		'jquery' : 'lib/jquery',
		'angular' : 'lib/angular/angular',
		'angular/ui-router' : 'lib/angular/angular-ui-router',
		'angular/resource' : 'lib/angular/angular-resource',
		'angular/mocks' : 'lib/angular/angular-mocks',
		'restangular' : 'lib/angular/restangular',
		'text' : 'lib/text',
		'test-util' : '/base/src/test/javascript/util/test-util'
	},
	shim : {
		'angular' : {exports : 'angular'},
		'angular/resource' : ['angular'],
		'angular/ui-router' : ['angular'],
		'restangular' : ['angular', 'angular/mocks', 'lib/lodash'],
		'angular/mocks' : ['angular']
	},

	// ask Require.js to load these files (all our tests)
	deps : tests,

	// start test run, once Require.js is done
	callback : window.__karma__.start
});
