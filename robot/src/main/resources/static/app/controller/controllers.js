var app = angular.module('robot-controllers', []);

app.controller('mainController', ['$scope', 'COMMANDS','robotService', 'robotCommandService',function ($scope, COMMANDS, robotService, robotCommandService) {
		
	$scope.robot;
	$scope.commandString = '';
	$scope.response = "Welcome, please issue a command for your robot."
	$scope.commandPlaceholder = "PLACE X,Y,F";		
	createRobot();
	
	$scope.sendCommand = function () {	
		if ($scope.commandString == '') {
			$scope.response = "Invalid command!"
			return;
		}
		$scope.commandString = angular.uppercase($scope.commandString);
		
		if($scope.commandString.indexOf(COMMANDS.PLACE + ' ') !=-1) {
			var commandParams = $scope.commandString.replace(COMMANDS.PLACE + ' ', '');		
			placeRobot(commandParams);
		} else if ($scope.commandString == COMMANDS.MOVE) {
			moveRobot();
		} else if ($scope.commandString == COMMANDS.LEFT) {
			turnRobotLeft();
		} else if ($scope.commandString == COMMANDS.RIGHT) {
			turnRobotRight();
		} else if ($scope.commandString == COMMANDS.REPORT) {
			report();		
		} else {
			$scope.response = "Invalid command!"
		}		
	};
	
	function createRobot() {
		
		robotService.create()
        .then(function (response) {        
        	$scope.robot = response.data;
        	$scope.response = "Robot is ready for your command.";	   		        
        }, function(error) {
            $scope.response = 'Could not create a new robot!';
        });
	};
	
	function placeRobot(commandParams) {	
		var arrayOfCommandParams = commandParams.split(',');
		if (arrayOfCommandParams.length == 3) {
			robotCommandService.place($scope.robot, arrayOfCommandParams[0], arrayOfCommandParams[1], arrayOfCommandParams[2])
	        .then(function (response) {	        	
	        	$scope.robot = response.data.robot;	
	        	$scope.response = response.data.message;	
	        	$scope.commandPlaceholder = "PLACE X,Y,F | MOVE | LEFT | RIGHT | REPORT";
	        }, function(error) {
	            $scope.response = 'Invalid command!';
	        });
		} else {
			$scope.response = "Invalid command!";
		}		
	};
	
	function moveRobot() {
		robotCommandService.move($scope.robot)
        .then(function (response) {
        	$scope.robot = response.data.robot;	
        	$scope.response = response.data.message;	
        }, function(error) {
        	 $scope.response = 'Invalid command!';
        });
	};
	
	function turnRobotLeft() {
		robotCommandService.left($scope.robot)
        .then(function (response) {
        	$scope.robot = response.data.robot;	
        	$scope.response = response.data.message;	
        }, function(error) {
        	 $scope.response = 'Invalid command!';
        });
	};
	
	function turnRobotRight() {
		robotCommandService.right($scope.robot)
        .then(function (response) {
        	$scope.robot = response.data.robot;	
        	$scope.response = response.data.message;	
        }, function(error) {
        	 $scope.response = 'Invalid command!';
        });
	};
	
	function report() {
		if( $scope.robot != null && $scope.robot.placed == true) {
			$scope.response = $scope.robot.x + ','+  $scope.robot.y + "," +  $scope.robot.facing;	
		} else {
			$scope.response = "Nothing to report yet!";
		}		
	};
}]);