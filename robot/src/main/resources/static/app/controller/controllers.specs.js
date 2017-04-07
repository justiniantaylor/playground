describe('robot-controllers', function(){ 
	var $scope,
	deferred,
	robotService,
	robotCommandService,
    $q;
	
    beforeEach(module('robotApp')); 
    
    describe('mainController',function(){       
        
        beforeEach(inject(function($controller,  $rootScope, _$q_, _robotService_, _robotCommandService_){
        	$q = _$q_;
        	$scope = $rootScope.$new();
        	robotService = _robotService_;
        	robotCommandService = _robotCommandService_;
        	deferred = $q.defer();
        	       
    		spyOn(robotService, 'create').and.returnValue(deferred.promise);   
    		spyOn(robotCommandService, 'place').and.returnValue(deferred.promise);  	
    		spyOn(robotCommandService, 'move').and.returnValue(deferred.promise);  
    		spyOn(robotCommandService, 'left').and.returnValue(deferred.promise);  	
    		spyOn(robotCommandService, 'right').and.returnValue(deferred.promise);    

        	mainController = $controller('mainController', { 
      	      $scope: $scope
      	    });
        }));
        
        it('should create robot', function () {  
        	
        	deferred.resolve({status: 200, data: {x: 0, y: 0, facing: null, table: null, placed: false}});       
        	
        	$scope.$apply();
            expect($scope.response).toBe("Robot is ready for your command.");
            expect($scope.commandPlaceholder).toBe("PLACE X,Y,F");
            expect($scope.commandString).toBe("");
            expect($scope.robot).not.toBe(undefined);   
            
            $scope.commandString = "REPORT";
    		$scope.sendCommand();
    		expect($scope.response ).toBe("Nothing to report yet!");	
        });
    
        it('should not create robot', function () {        	
        	deferred.reject();
    		$scope.$apply();
    	    expect($scope.robot).toBe(undefined);
        });
        
        it('invalid robot command', function () { 	 
        	$scope.commandString = "PLAC 1,1,NORTH";
    		$scope.sendCommand();
    		expect($scope.response).toBe("Invalid command!");
    		
    		$scope.commandString = "REPORT";
     		$scope.sendCommand();
     		expect($scope.response ).toBe("Nothing to report yet!");	
        });
    
    	it('should place robot', function () { 	 
    		$scope.commandString = "PLACE 1,1,NORTH";
    		$scope.sendCommand();
    		expect(robotCommandService.place).toHaveBeenCalled();
    		
            deferred.resolve({status: 200, data: {commandAccepted: true, message: "", robot: {x: 1, y: 1, facing: "NORTH", table: {dimensionX: 5, dimensionY: 5}, placed: true}}});
    	    $scope.$apply();    	    
    	    expect($scope.commandPlaceholder).toBe("PLACE X,Y,F | MOVE | LEFT | RIGHT | REPORT");
    	    expect($scope.robot).not.toBe(undefined);
    	    expect($scope.error).toBe(undefined);	 
    	    
    	    $scope.commandString = "REPORT";
    		$scope.sendCommand();
    		expect($scope.response ).toBe("1,1,NORTH");	
    	});
	
    	it('should move robot', function () { 	 
    		$scope.commandString = "MOVE";
    		$scope.sendCommand();
    		expect(robotCommandService.move).toHaveBeenCalled();
    		
            deferred.resolve({status: 200, data: {commandAccepted: true, message: "", robot: {x: 1, y: 2, facing: "NORTH", table: {dimensionX: 5, dimensionY: 5}, placed: true}}});
    	    $scope.$apply();    	    
    	    expect($scope.robot).not.toBe(undefined);
    	    expect($scope.error).toBe(undefined);	
    	    
    	    $scope.commandString = "REPORT";
    		$scope.sendCommand();
    		expect($scope.response ).toBe("1,2,NORTH");	
    	});
    	
    	it('should turn robot left', function () { 	 
    		$scope.commandString = "LEFT";
    		$scope.sendCommand();
    		expect(robotCommandService.left).toHaveBeenCalled();
    		
            deferred.resolve({status: 200, data: {commandAccepted: true, message: "", robot: {x: 1, y: 2, facing: "WEST", table: {dimensionX: 5, dimensionY: 5}, placed: true}}});
    	    $scope.$apply();    	    
    	    expect($scope.robot).not.toBe(undefined);
    	    expect($scope.error).toBe(undefined);	
    	    
    	    $scope.commandString = "REPORT";
    		$scope.sendCommand();
    		expect($scope.response ).toBe("1,2,WEST");	
    	});
    	
    	it('should turn robot right', function () { 	 
    		$scope.commandString = "RIGHT";
    		$scope.sendCommand();
    		expect(robotCommandService.right).toHaveBeenCalled();
    		
            deferred.resolve({status: 200, data: {commandAccepted: true, message: "", robot: {x: 1, y: 2, facing: "EAST", table: {dimensionX: 5, dimensionY: 5}, placed: true}}});
    	    $scope.$apply();    	    
    	    expect($scope.robot).not.toBe(undefined);
    	    expect($scope.error).toBe(undefined);	
    	    
    	    $scope.commandString = "REPORT";
    		$scope.sendCommand();
    		expect($scope.response ).toBe("1,2,EAST");	
    	});
 
    });
});