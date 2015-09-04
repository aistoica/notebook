var userApp = angular.module('users', ['ngResource', 'ngRoute', 'ngFileUpload']);

userApp.factory('User', ['$resource', function($resource) {
	return $resource('users/:id', {id: '@id'});
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

userApp.controller('users', function($scope, $resource, $http, User) {
	$scope.users= [];
	
	$scope.users = User.query();
	
	$scope.deleteUser = function(user) {
		user.$remove(function(){
			$scope.users = User.query();
		});
	};
	
});

userApp.controller('usersUploadCtrl', function($scope, $routeParams, $http, $timeout, Upload){
	
	var id = $routeParams.id;
	$scope.id = $routeParams.id;
		
	$http.get('/upload/'+id).
		then(function(resp) {
			$scope.fileNames = resp.data;
			console.log($scope.fileNames);
		});
	
	$scope.uploadFiles = function(files) {
    	//var id = $routeParams.id;
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
	

	
/*	$scope.downloadFile = function(fileEntry) {
		$http.get('/download/' + id + '/' + fileEntry).
			then(function(resp) {
				
			});
	}*/
});

userApp.controller('usersPhotoUploadCtrl', function($scope, $routeParams, $http, $timeout, Upload){
	var id = $routeParams.id;
	   $scope.uploadPic = function(file) {
		    file.upload = Upload.upload({
		      url: '/uploadPhoto/'+id,
		      method: 'POST',
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
		      // Math.min is to fix IE which reports 200% sometimes
		      file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
		    });
		}		
});

userApp.controller('usersProfileCtrl', ['$scope', '$routeParams', '$location', '$http', 'User', 
                                     function($scope, $routeParams, $location, $http, User) {
	//$scope.statusList = ['married', 'not married'];
	var photo;
	var existinguser = User.get({id:$routeParams.id}, function (data) {
	 	$http.get('/downloadPhoto/' + data.id + '/' + data.photo)
		.then(function(resp) {
			$scope.picFile = resp.data;
			//console.log(resp.data);
	});
	 });




/*	$scope.save = function() {
		$scope.existinguser.$save(function() {
			$location.path('/users');
		});
	};*/
		
}]);
