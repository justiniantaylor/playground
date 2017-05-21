services.factory('campaignNotificationService', ['$http', function($http) {
    var urlBase = '/resource/campaign/';
    var campaignNotificationService = {};

    campaignNotificationService.findAll = function (campaignId) {
        return $http.get(urlBase + campaignId + "/notification/");
    };
    
    campaignNotificationService.find = function (campaignId, id) {
        return $http.get(urlBase + campaignId + "/notification/" + id);
    };
    
    campaignNotificationService.create = function (campaignId, object) {
        return $http.post(urlBase + campaignId + "/notification/", object);
    };
    
    campaignNotificationService.update = function (campaignId, id, object) {
        return $http.put(urlBase + campaignId + "/notification/" + id, object);
    };
    
    campaignNotificationService.delete = function (campaignId, id) {
        return $http.delete(urlBase + campaignId + "/notification/" + id);
    };

    return campaignNotificationService;
}]);