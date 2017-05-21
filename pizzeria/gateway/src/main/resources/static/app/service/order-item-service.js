services.factory('orderItemService', ['$http', function($http) {
    var urlBase = '/resource/order/';
    var orderItemService = {};

    orderItemService.findAll = function (orderId) {
        return $http.get(urlBase + orderId + "/item/");
    };
    
    orderItemService.find = function (orderId, id) {
        return $http.get(urlBase + orderId + "/item/" + id);
    };
    
    orderItemService.create = function (orderId, object) {
        return $http.post(urlBase + orderId + "/item/", object);
    };
    
    orderItemService.update = function (orderId, id, object) {
        return $http.put(urlBase + orderId + "/item/" + id, object);
    };
    
    orderItemService.delete = function (orderId, id) {
        return $http.delete(urlBase + orderId + "/item/" + id);
    };

    return orderItemService;
}]);