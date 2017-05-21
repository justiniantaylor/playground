services.factory('customerService', ['$http', function($http) {
    var urlBase = '/resource/customer/';
    var customerService = {};

    customerService.findAll = function () {
        return $http.get(urlBase);
    };
    
    customerService.find = function (id) {
        return $http.get(urlBase + "/" + id);
    };
    
    customerService.create = function (object) {
        return $http.post(urlBase, object);
    };
    
    customerService.update = function (id, object) {
        return $http.put(urlBase + "/" + id, object);
    };
    
    customerService.delete = function (id) {
        return $http.delete(urlBase + "/" + id);
    };

    return customerService;
}]);