var app = angular.module('robot-services', []);

app.factory('robotService', ['$http', function($http) {
    var urlBase = '/robot';
    var robotService = {};

    robotService.place = function (robot, x, y, direction) {
        return $http.post(urlBase + "/place/" + x + "/" + y + "/" + direction, robot);
    };
    
    robotService.move = function (robot) {
    	console.log(robot);
    	
        return $http.post(urlBase + "/move", robot);
    };
    
    robotService.left = function (robot) {
        return $http.post(urlBase + "/left", robot);
    };
    
    robotService.right = function (robot) {
        return $http.post(urlBase + "/right", robot);
    };

    return robotService;
}]);