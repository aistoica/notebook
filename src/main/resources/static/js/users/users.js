angular.module('users', ['ngResource']).controller('users', function($scope, $resource, $http) {
	$scope.users= [];
	
	$scope.status = [];
	$http.get('maritalStatus').success(function(data) {
		$scope.status=data;
		console.log(data);
	});
	
	$http.get('/users/all').success(function(data) {
		$scope.users = data;
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
	
	$scope.existinguser = function() {
		var User = $resource('/users/edit/:userId', {userId:'@id'});
		var user = User.get({userId:id});
		user.$save(function(response) {
			$scope.message = response.message;
		});
	}
	
/*	$scope.updateUser = function(){	
		var User = $resource('/users/edit/:userId', {userId:'@id'});
		var user = User.get({userId:$scope.id});
		user.$save(function(response) {
			$scope.message = response.message;
		});
		
	}*/
});
