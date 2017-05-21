describe('payroll-controllers', function(){ 
	var $scope,
	deferred,
	paymentPeriodService,
	payslipCalculateService,
    $q;
	
    beforeEach(module('payrollApp')); 
    
    describe('mainController',function(){       
        
        beforeEach(inject(function($controller,  $rootScope, _$q_, _paymentPeriodService_, _payslipCalculateService_){
        	$q = _$q_;
        	$scope = $rootScope.$new();
        	paymentPeriodService = _paymentPeriodService_;
        	payslipCalculateService = _payslipCalculateService_;
        	deferred = $q.defer();
        	       
    		spyOn(paymentPeriodService, 'findAll').and.returnValue(deferred.promise);   
    		spyOn(payslipCalculateService, 'calculate').and.returnValue(deferred.promise);  	 

        	mainController = $controller('mainController', { 
      	      $scope: $scope
      	    });
        }));
        
        it('should initialise', function () {  
        	deferred.resolve({status: 200, data: {paymentPeriods: ["01 July 2012 - 31 July 2012","01 August 2012 - 31 August 2012"]}});              	
        	expect(paymentPeriodService.findAll).toHaveBeenCalled();
        	$scope.$apply();
            expect(JSON.stringify($scope.payslipsHeaders)).toBe(JSON.stringify(["Name", "Pay Period", "Gross Income", "Income Tax", "Net Income", "Super Amount"]));
            $scope.payslipRequests
            expect(JSON.stringify($scope.paymentPeriods)).toBe(JSON.stringify(["01 July 2012 - 31 July 2012","01 August 2012 - 31 August 2012"]));
        });
        
        it('should add employee', function () {          	 
        	deferred.resolve({status: 200, data: {paymentPeriods: ["01 July 2012 - 31 July 2012","01 August 2012 - 31 August 2012"]}});            
        	$scope.$apply();
        	expect($scope.payslipRequests.length).toBe(2);
        	$scope.add();
        	expect($scope.payslipRequests.length).toBe(3);
        	expect($scope.payslipRequests[2].firstName).toBe("");
        	expect($scope.payslipRequests[2].lastName).toBe("");
        	expect($scope.payslipRequests[2].annualSalary).toBe(0);
        	expect($scope.payslipRequests[2].superRate).toBe("0%");
        	expect($scope.payslipRequests[2].paymentStartDate).toBe($scope.paymentPeriods[0]);
        });
        
        it('should remove employee', function () {          	 
        	deferred.resolve({status: 200, data: {paymentPeriods: ["01 July 2012 - 31 July 2012","01 August 2012 - 31 August 2012"]}});            
        	$scope.$apply();
        	expect($scope.payslipRequests.length).toBe(2);
        	$scope.remove(0);
        	expect($scope.payslipRequests.length).toBe(1);
        	expect($scope.payslipRequests[0].firstName).toBe("Ryan");
        	expect($scope.payslipRequests[0].lastName).toBe("Chen");
        	expect($scope.payslipRequests[0].annualSalary).toBe(120000);
        	expect($scope.payslipRequests[0].superRate).toBe("10%");
        	expect($scope.payslipRequests[0].paymentStartDate).toBe("01 March 2013 - 31 March 2013");
        });
        
        it('should calculate payslips and be able to close', function () {        
        	$scope.calculate()
        	expect(payslipCalculateService.calculate).toHaveBeenCalled();
        	expect(payslipCalculateService.calculate).toHaveBeenCalledWith($scope.payslipRequests);        	
        	deferred.resolve({status: 200, data: {payslipResponses: [{grossIncome: 5004, incomeTax: 922, name: "David Rudd", netIncome: 4082, payPeriod: "01 March 2013 - 31 March 2013", superAmount: 450},
        															 {grossIncome: 10000, incomeTax: 2696, name: "Ryan Chen", netIncome: 7304, payPeriod: "01 March 2013 - 31 March 2013", superAmount: 1000}]}});        	        	        	
        	$scope.$apply();
        	expect($scope.payslipResponses.length).toBe(2);     
        	expect(JSON.stringify($scope.payslipResponses)).toBe(JSON.stringify([{grossIncome: 5004, incomeTax: 922, name: "David Rudd", netIncome: 4082, payPeriod: "01 March 2013 - 31 March 2013", superAmount: 450},
				 																 {grossIncome: 10000, incomeTax: 2696, name: "Ryan Chen", netIncome: 7304, payPeriod: "01 March 2013 - 31 March 2013", superAmount: 1000}]));
        	
        	$scope.closeCalculations();
        	expect($scope.payslipResponses.length).toBe(0);     
        });
    });
});