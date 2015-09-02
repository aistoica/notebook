var userApp = angular.module('users', ['ngResource', 'ngRoute', 'ngFileUpload']);

userApp.factory('User', ['$resource', function($resource) {
	return $resource('users/:id', {id: '@id'});
}]);


userApp.controller('usersEditCtrl', ['$scope', '$routeParams', '$location', 'User', 
                                     function($scope, $routeParams, $location, User) {
	$scope.statusList = ['married', 'not married'];
	$scope.existinguser = User.get({id:$routeParams.id});
	$scope.save = function() {
		$scope.existinguser.$save(function() {
			$location.path('/users');
		});
	};
		
}]);

userApp.controller('usersAddCtrl', ['$scope', '$resource', '$http', '$location','User', 
                                    function($scope, $resource, $http, $location, User) {
	$scope.users= [];
	$scope.saveUser = function() {
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
}]);


userApp.controller('users', function($scope, $resource, $http, User) {
	$scope.users= [];
	
/*	$scope.status = [];
	$http.get('maritalStatus').success(function(data) {
		$scope.status=data;
		console.log(data);
	});
	*/
/*	$http.get('/users/all').success(function(data) {
		$scope.users = data;
		console.log(data);
	});	*/
	
	$scope.users = User.query();
	
	$scope.deleteUser = function(user) {
		user.$remove(function(){
			$scope.users = User.query();
		});
	};
	
});

userApp.controller('usersUploadCtrl', function($scope, $routeParams, $timeout, Upload){
    $scope.uploadFiles = function(files) {
    	var id = $routeParams.id;
        $scope.files = files;
        angular.forEach(files, function(file) {
            if (file && !file.$error) {
         		file.upload = Upload.upload({
                  url: '/upload/'+id,
                  file: file
                });

                file.upload.then(function (response) {
                  $timeout(function () {
                    file.result = response.data;
                  });
                }, function (response) {
                  if (response.status > 0)
                    $scope.errorMsg = response.status + ': ' + response.data;
                });

                file.upload.progress(function (evt) {
                  file.progress = Math.min(100, parseInt(100.0 * 
                                           evt.loaded / evt.total));
                });
    		}   
        });
    }
});
