angular.module('users')
.controller('userProfileCtrl', ['$scope', '$routeParams', '$location', '$http', 'User', 
                                 function($scope, $routeParams, $location, $http, User) {
//$scope.statusList = ['married', 'not married'];
var photo;
	
$scope.existinguser = User.get({id:$routeParams.id}, function (data) {

	$scope.userId = data.id;
	console.log($scope.userId);
	if(data.photo == null) {
		$scope.picFile = 'assets/images/default_user.png';
	}
	else {
	 	$http.get('/users/downloadPhoto/' + data.id + '/' + data.photo)
		.then(function(resp) {
			$scope.picFile = resp.data;
		});
	}

});
}]);