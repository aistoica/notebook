angular.module('users')
.controller('userFilesCtrl', function($scope, $routeParams, $http, $timeout, Upload){
	
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
                
            	$scope.fileNames.push(file.name)
    		}   
        });
    }
	
});