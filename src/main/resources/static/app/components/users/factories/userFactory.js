angular.module('users')
	.factory('User', ['$resource', function($resource) {
	return $resource('users/:id', {id: '@id'});
}]);