var app = angular.module('robot-services', []);

app.factory('robotService', ['$http', function($http) {
    var urlBase = '/robot/';
    var robotService = {};

    robotService.create = function () {
        return $http.post(urlBase);
    };
  
    return robotService;
}]);

app.factory('robotCommandService', ['$http', function($http) {
    var urlBase = '/robot/command';
    var robotCommandService = {};

    robotCommandService.place = function (robot, x, y, direction) {
        return $http.post(urlBase + "/place/" + x + "/" + y + "/" + direction, robot);
    };
    
    robotCommandService.move = function (robot) {
        return $http.post(urlBase + "/move", robot);
    };
    
    robotCommandService.left = function (robot) {
        return $http.post(urlBase + "/left", robot);
    };
    
    robotCommandService.right = function (robot) {
        return $http.post(urlBase + "/right", robot);
    };

    return robotCommandService;
}]);