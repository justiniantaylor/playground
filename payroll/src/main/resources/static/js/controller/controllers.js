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
            
            var pinTo = $scope.getToastPosition();
            $mdToast.show(
            	      $mdToast.simple()
            	        .textContent('Successfully calculated payslip information.')
            	        .position(pinTo )
            	        .hideDelay(3000)
            	    );		                      
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
	
	var last = {
        bottom: false,
        top: true,
        left: false,
        right: true
    };

    $scope.toastPosition = angular.extend({},last);

    $scope.getToastPosition = function() {
        sanitizePosition();

        return Object.keys($scope.toastPosition)
        .filter(function(pos) { return $scope.toastPosition[pos]; })
        .join(' ');
    };
    
    function sanitizePosition() {
	    var current = $scope.toastPosition;
	
	    if ( current.bottom && last.top ) current.top = false;
	    if ( current.top && last.bottom ) current.bottom = false;
	    if ( current.right && last.left ) current.left = false;
	    if ( current.left && last.right ) current.right = false;
	
	    last = angular.extend({},current);
    }

}]);