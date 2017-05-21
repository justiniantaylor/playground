services.factory('campaignService', ['$http', function($http) {
    var urlBase = '/resource/campaign/';
    var campaignService = {};

    campaignService.findAll = function () {
        return $http.get(urlBase + "/");
    };
    
    campaignService.find = function (id) {
        return $http.get(urlBase + id);
    };
    
    campaignService.create = function (object) {
        return $http.post(urlBase, object);
    };
    
    campaignService.update = function (id, object) {
        return $http.put(urlBase + id, object);
    };
    
    campaignService.delete = function (id) {
        return $http.delete(urlBase + id);
    };

    return campaignService;
}]);