var app = angular.module('payroll-service', []);

app.factory('payslipCalculatorService', ['$http', function($http) {
    var urlBase = '/payslip';
    var payslipCalculatorService = {};

    payslipCalculatorService.getPaymentPeriods = function () {
        return $http.get(urlBase + "/payment-period");
    };
    
    payslipCalculatorService.calculate = function (payslipRequests) {
        return $http.post(urlBase + "/calculate", payslipRequests);
    };

    return payslipCalculatorService;
}]);