angular.module('users')
	.controller('userEditCtrl', ['$scope', '$routeParams', '$location', 'User', 
	                              function($scope, $routeParams, $location, User) {
		$scope.statusList = ['married', 'not married'];
		$scope.existinguser = User.get({id:$routeParams.id});
		$scope.save = function() {
			
			//validate event
			$scope.$broadcast('show-errors-event');
			
			if($scope.userForm.$invalid)
				return
				
			$scope.existinguser.$save(function() {
				$location.path('/users');
			});
		};
		$scope.cancel = function() {
			$scope.newuser={}		
			$location.path('/users');
		}
		
	}]);