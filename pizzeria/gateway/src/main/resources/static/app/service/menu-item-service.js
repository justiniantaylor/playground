services.factory('menuItemService', ['$http', function($http) {
    var urlBase = '/resource/menu/';
    var menuItemService = {};

    menuItemService.findAll = function (menuId) {
        return $http.get(urlBase + menuId + "/item/");
    };
    
    menuItemService.find = function (menuId, id) {
        return $http.get(urlBase + menuId + "/item/" + id);
    };
    
    menuItemService.create = function (menuId, object) {
        return $http.post(urlBase + menuId + "/item/", object);
    };
    
    menuItemService.update = function (menuId, id, object) {
        return $http.put(urlBase + menuId + "/item/" + id, object);
    };
    
    menuItemService.delete = function (menuId, id) {
        return $http.delete(urlBase + menuId + "/item/" + id);
    };

    return menuItemService;
}]);