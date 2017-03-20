(function () {
	var app = angular.module('payrollApp', ['ngMaterial', 'ngSanitize', 'ngCsv', 'payroll-controller', 'payroll-service', 'payroll-directive']);
	
	app.config(function($mdIconProvider) {
		 $mdIconProvider
		 .iconSet('social', 'img/icons/sets/social-icons.svg', 24)
		 .iconSet('action', 'https://raw.githubusercontent.com/google/material-design-icons/master/sprites/svg-sprite/svg-sprite-action.svg', 24)
		 .defaultIconSet('img/icons/sets/core-icons.svg', 24);
	});

}());