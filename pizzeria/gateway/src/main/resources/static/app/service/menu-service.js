services.factory('menuService', ['$http', function($http) {
    var urlBase = '/resource/menu/';
    var menuService = {};

    menuService.findAll = function () {
        return $http.get(urlBase + "/");
    };
    
    menuService.find = function (id) {
        return $http.get(urlBase + id);
    };

    menuService.create = function (object) {
        return $http.post(urlBase, object);
    };
    
    menuService.update = function (id, object) {
        return $http.put(urlBase + id, object);
    };
    
    menuService.delete = function (id) {
        return $http.delete(urlBase + id);
    };

    return menuService;
}]);