var app = angular.module('robot-controllers', []);

app.controller('robotController', ['$scope','robotService', 'robotCommandService',function ($scope, robotService, robotCommandService) {
		
	$scope.robot;
	createRobot();
	
	$scope.commandString = '';
	$scope.response = "Welcome, please issue a command for your robot."
	$scope.commandList = "PLACE X,Y,F";		
		
	function createRobot() {	
		robotService.create()
        .then(function (response) {
        	$scope.robot = response.data;	
        	$scope.response = "Robot is ready for your command.";	   		        
        }, function(error) {
            $scope.response = 'Could not create a new robot!';
        });
	};
	
	$scope.sendCommand = function () {	
		if ($scope.commandString == '') {
			$scope.response = "Invalid command!"
			return;
		}
		$scope.commandString = angular.uppercase($scope.commandString);
		
		if($scope.commandString.indexOf('PLACE ') !=-1) {
			var subCommand = $scope.commandString.replace('PLACE ', '');		
			var array = subCommand.split(',');
			if (array.length == 3) {
				robotCommandService.place($scope.robot, array[0], array[1], array[2])
		        .then(function (response) {
		        	$scope.robot = response.data.robot;	
		        	$scope.response = response.data.message;	
		        	$scope.commandList = "PLACE X,Y,F | MOVE | LEFT | RIGHT | REPORT";
		        }, function(error) {
		            $scope.response = 'Invalid command!';
		        });
			} else {
				$scope.response = "Invalid command!"
			}
		} else if ($scope.commandString == 'MOVE') {
			robotCommandService.move($scope.robot)
	        .then(function (response) {
	        	$scope.robot = response.data.robot;	
	        	$scope.response = response.data.message;	
	        }, function(error) {
	        	 $scope.response = 'Invalid command!';
	        });
		} else if ($scope.commandString == 'LEFT') {
			robotCommandService.left($scope.robot)
	        .then(function (response) {
	        	$scope.robot = response.data.robot;	
	        	$scope.response = response.data.message;	
	        }, function(error) {
	        	 $scope.response = 'Invalid command!';
	        });
		} else if ($scope.commandString == 'RIGHT') {
			robotCommandService.right($scope.robot)
	        .then(function (response) {
	        	$scope.robot = response.data.robot;	
	        	$scope.response = response.data.message;	
	        }, function(error) {
	        	 $scope.response = 'Invalid command!';
	        });
		} else if ($scope.commandString == 'REPORT') {
			if( $scope.robot.x != null) {
				$scope.response = $scope.robot.x + ','+  $scope.robot.y + "," +  $scope.robot.facing;	
			} else {
				$scope.response = "Nothing to report yet!";
			}			
		} else {
			$scope.response = "Invalid command!"
		}		
	};
}]);