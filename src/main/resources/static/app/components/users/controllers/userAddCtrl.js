angular.module('users')
	.controller('userAddCtrl', ['$scope', '$resource', '$http', '$location','User', 
	                             function($scope, $resource, $http, $location, User) {
		$scope.users= [];
		$scope.saveUser = function() {
			
			//validate event
			$scope.$broadcast('show-errors-event');
			
			if($scope.userForm.$invalid)
				return
					
			$scope.users.push($scope.newuser);
		
			//create resource
			var User = $resource('/users/new');
			//call save
			User.save($scope.newuser, function(response){
				$scope.message = response.message;
				$location.path('/users');
			});
			
			$scope.newuser = {};
		}
		
		$scope.cancel = function() {
			$scope.newuser={}		
			$location.path('/users');
		}
	}]);	