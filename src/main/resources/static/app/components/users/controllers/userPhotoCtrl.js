angular.module('users')
.controller('userPhotoCtrl', function($scope, $routeParams, $http, $timeout, Upload){
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