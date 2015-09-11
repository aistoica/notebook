angular.module('users')
	.controller('userShowCtrl', function($scope, $resource, $http, User) {
		$scope.users= [];
		
		$scope.users = User.query();
		
		$scope.deleteUser = function(user) {
			user.$remove(function(){
				$scope.users = User.query();
			});
		};
		
	});