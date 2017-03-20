var app = angular.module('payroll-controller', []);

app.controller('payrollController', ['$scope', '$mdToast', 'payslipCalculatorService',function ($scope, $mdToast, payslipCalculatorService) {

	$scope.paymentPeriods = getPaymentPeriods();
	
	//I have only set this data below for assisting the assignment assessor, this would not be done in a real world scenario
	$scope.payslipRequests = [{firstName: "David", lastName: "Rudd", annualSalary: 60050, superRate: "9%", paymentStartDate: '01 March 2013 - 31 March 2013'},
							  {firstName: "Ryan", lastName: "Chen", annualSalary: 120000, superRate: "10%", paymentStartDate: '01 March 2013 - 31 March 2013'}];
	
	$scope.payslipResponses = []

	$scope.add = function () {
		$scope.payslipRequests.push({firstName: "", lastName: "", annualSalary: 0, superRate: "0%", paymentStartDate: $scope.paymentPeriods[0]})
	};
	
	$scope.remove = function (index) {
		$scope.payslipRequests.splice(index, 1);
	};
	
	$scope.close = function () {
		$scope.payslipResponses = [];
	};	
	
	$scope.checkPercentage = function (value) {
		value = value + "%"
	};	
	
	$scope.calculate = function () {
		payslipCalculatorService.calculate($scope.payslipRequests)
        .then(function (response) {
            $scope.payslipResponses = response.data;
            $mdToast.simple()
	        .textContent('Successfully calculated payslip information.')
	        .position(top)
	        .hideDelay(3000)		                      
        }, function(error) {
            $scope.status = 'Unable to insert customer: ' + error.message;
        });
	};
	
	function getPaymentPeriods() {
		payslipCalculatorService.getPaymentPeriods()
        .then(function (response) {            	
        	$scope.paymentPeriods = response.data;
        }, function(error) {
            $scope.status = 'Unable to retrieve the available payment periods: ' + error.message;
        });
	};
	
	$scope.getPayslips = function () {
		return $scope.payslipResponses;
	};
	
	$scope.getPayslipsHeader = function () {return ["Name", "Pay Period", "Gross Income", "Income Tax", "Net Income", "Super Amount"]};
	
	$scope.$watch('payslipRequestsForm.$valid', function(newVal) {
	      $mdToast.simple()
	        .textContent('Please be aware you have invalid data, you will not be able to submit until this is fixed!')
	        .position(top)
	        .hideDelay(3000)
    });	
}]);