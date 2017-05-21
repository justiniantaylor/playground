controllers.controller('menu-controller', ['$scope', '$mdToast', 'menuService', 'menuItemService',function ($scope, $mdToast, menuService, menuItemService) {
	$scope.menus = [];
	$scope.menu;
	$scope.menuItems = [];
	$scope.menuItem;
	
	getMenus();

	$scope.addMenu = function () {
		$scope.menus.push({id: null, startDate: "", endDate: ""})
	};
	
	$scope.removeMenu = function (index) {
		$scope.menus.splice(index, 1);
	};
	
	$scope.addMenuItem = function () {
		$scope.menuItems.push({id: null, code: "", description: "", unitPriceInCents: 0, available: true, menuId: $scope.menu ? $scope.menu.id : null})
	};
	
	$scope.removeMenuItem = function (index) {
		$scope.menuItems.splice(index, 1);
	};
	
	function getMenus() {
		menuService.findAll()
        .then(function (response) {                	
        	$scope.menus = response.data.menu;            	
        }, function(error) {
            $scope.status = 'Unable to retrieve active menu: ' + error.message;
        });
	};
	
	function getMenuItems(menuId) {
		menuItemService.findAll(menuId)
        .then(function (response) {           	
        	$scope.menuItems = response.data.menuItems;       
        }, function(error) {
            $scope.status = 'Unable to retrieve the menus: ' + error.message;
        });
	};
}]);