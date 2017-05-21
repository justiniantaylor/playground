var app = angular.module('gateway', ['ngMaterial', 'ngSanitize', 'gateway-controller', 'gateway-service']).config(function($httpProvider) {
	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
})

app.config(function($mdIconProvider) {
	$mdIconProvider
	 .iconSet('social', 'img/icons/sets/social-icons.svg', 24)
	 .iconSet('action', 'https://raw.githubusercontent.com/google/material-design-icons/master/sprites/svg-sprite/svg-sprite-action.svg', 24)
	 .defaultIconSet('img/icons/sets/core-icons.svg', 24);
});