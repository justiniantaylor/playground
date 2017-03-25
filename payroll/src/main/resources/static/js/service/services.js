var app = angular.module('payroll-service', []);

app.factory('paymentPeriodService', ['$http', function($http) {
    var urlBase = '/payment/period/';
    var paymentPeriodService = {};

    paymentPeriodService.findAll = function () {
        return $http.get(urlBase);
    };

    return paymentPeriodService;
}]);

app.factory('payslipCalculateService', ['$http', function($http) {
    var urlBase = '/payslip/calculate/';
    var payslipCalculateService = {};

    
    payslipCalculateService.calculate = function (payslipRequests) {
        return $http.post(urlBase, payslipRequests);
    };

    return payslipCalculateService;
}]);