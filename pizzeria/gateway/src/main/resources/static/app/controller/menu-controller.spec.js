describe('gateway-controllers', function(){ 
	var $scope,
	deferred,
	menuService,
	menuItemService,
    $q;
	
    beforeEach(module('gateway')); 
    
    describe('menu-controller',function(){       
        
        beforeEach(inject(function($controller,  $rootScope, _$q_, _menuService_, _menuItemService_){
        	$q = _$q_;
        	$scope = $rootScope.$new();
        	menuService = _menuService_;
        	menuItemService = _menuItemService_;
        	deferred = $q.defer();
        	       
    		spyOn(menuService, 'findAll').and.returnValue(deferred.promise);   
    		spyOn(menuItemService, 'findAll').and.returnValue(deferred.promise);  	 

        	menuController = $controller('menu-controller', { 
      	      $scope: $scope
      	    });
        }));
        
        it('should get menus', function () {  
        	//TODO
        });
        
        it('should select menu', function () {  
        	//TODO
        });
        
        it('should add menu', function () {  
        	//TODO
        });
        
        it('should remove menu', function () {  
        	//TODO
        });
        
        it('should add menu item', function () {  
        	//TODO
        });
        
        it('should remove menu item', function () {  
        	//TODO
        });
        
    });
});