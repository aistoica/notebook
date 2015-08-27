angular.module('users', ['ngResource']).controller('users', function($scope, $resource, $http) {
	$scope.users= [];
	
	$scope.status = [];
	$http.get('maritalStatus').success(function(data) {
		$scope.status=data;
		console.log(data);
		
		
	});
	
	$scope.saveUser = function() {
		$scope.users.push($scope.newuser);
		
		//create resource
		var User = $resource('/users/new');
		//call save
		User.save($scope.newuser, function(response){
			$scope.message = response.message;
		});
		
		$scope.newuser = {};
	}
});
