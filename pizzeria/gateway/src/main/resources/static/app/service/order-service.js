services.factory('orderService', ['$http', function($http) {
    var urlBase = '/resource/order/';
    var orderService = {};

    orderService.findAll = function () {
        return $http.get(urlBase);
    };
    
    orderService.find = function (id) {
        return $http.get(urlBase + "/" + id);
    };
    
    orderService.create = function (object) {
        return $http.post(urlBase, object);
    };
    
    orderService.update = function (id, object) {
        return $http.put(urlBase + "/" + id, object);
    };
    
    orderService.delete = function (id) {
        return $http.delete(urlBase + "/" + id);
    };

    return orderService;
}]);