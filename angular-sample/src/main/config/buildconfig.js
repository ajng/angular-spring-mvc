({
	baseUrl: '${basedir}/src/main/webapp/ui',
	mainConfigFile: '${basedir}/src/main/webapp/ui/main/main.js',
	name: 'main/angular-start',
	optimize: 'uglify2',
	out: '${project.build.directory}/${project.build.finalName}/ui/optimized.js',
	generateSourceMaps: true,
	preserveLicenseComments: false
})