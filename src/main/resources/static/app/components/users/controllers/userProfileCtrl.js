angular.module('users')
.controller('userProfileCtrl', ['$scope', '$routeParams', '$location', '$http', 'User', 
                                 function($scope, $routeParams, $location, $http, User) {
//$scope.statusList = ['married', 'not married'];
var photo;
var existinguser = User.get({id:$routeParams.id}, function (data) {
	if(data.photo == null)
		$scope.picFile = '/images/default_user.png';
	else {
	 	$http.get('/downloadPhoto/' + data.id + '/' + data.photo)
		.then(function(resp) {
			$scope.picFile = resp.data;
			//console.log(resp.data);
			$scope.user = existinguser;
		});
	}

});
}]);