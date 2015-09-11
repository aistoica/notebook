angular.module('home', []).controller('homeCtrl', function($scope, $http) {
	$http.get('/user/').success(function(data) {
		$scope.user = data.name;
	});
});
