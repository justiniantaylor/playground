var app = angular.module('payroll-controller', []);

app.controller('mainController', ['$scope', '$mdToast', 'paymentPeriodService', 'payslipCalculateService',function ($scope, $mdToast, paymentPeriodService, payslipCalculateService) {

	$scope.paymentPeriods = getPaymentPeriods();
	$scope.payslipsHeaders = ["Name", "Pay Period", "Gross Income", "Income Tax", "Net Income", "Super Amount"];	
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
	
	function getPaymentPeriods() {
		paymentPeriodService.findAll()
        .then(function (response) {                	
        	$scope.paymentPeriods = response.data.paymentPeriods;
        }, function(error) {
            $scope.status = 'Unable to retrieve the available payment periods: ' + error.message;
        });
	};
	
	$scope.calculate = function () {
		payslipCalculateService.calculate($scope.payslipRequests)
        .then(function (response) {
        	console.log(response);
            $scope.payslipResponses = response.data.payslipResponses;
            
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

	$scope.closeCalculations = function () {
		$scope.payslipResponses = [];
	};	
	
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