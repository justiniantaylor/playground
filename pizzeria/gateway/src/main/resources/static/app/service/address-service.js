services.factory('addressService', ['$http', function($http) {
    var urlBase = '/resource/customer/';
    var addressService = {};

    addressService.findAll = function (customerId) {
        return $http.get(urlBase + customerId + "/address/");
    };
    
    addressService.find = function (customerId, id) {
        return $http.get(urlBase + customerId + "/address/" + id);
    };
    
    addressService.create = function (customerId, object) {
        return $http.post(urlBase + customerId + "/address/", object);
    };
    
    addressService.update = function (customerId, id, object) {
        return $http.put(urlBase + customerId + "/address/" + id, object);
    };
    
    addressService.delete = function (customerId, id) {
        return $http.delete(urlBase + customerId + "/address/" + id);
    };

    return addressService;
}]);