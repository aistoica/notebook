angular.module('users')
.controller('userPhotoCtrl', function($scope, $location, $routeParams, $http, $timeout, Upload){
	var id = $routeParams.id;
	   $scope.uploadPic = function(file) {
		    file.upload = Upload.upload({
		      url: '/users/uploadPhoto/'+id,
		      method: 'POST',
		      file: file,

		    });

		    file.upload.progress(function (evt) {
		      	// Math.min is to fix IE which reports 200% sometimes
		      	file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
		    }).success(function (data, status, headers, config) {
		     	$location.path('/userProfile/' + id);
		    }).error(function (data, status, headers, config) {
		    	console.log("error");
		    });
		};
});