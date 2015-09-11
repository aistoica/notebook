angular
	.module('app', [
	   'ngRoute',
	   'ngResource',
	   'users',
	   'auth',
	   'login',
	   'home',
	   'validation' 
	]).config(
			function($routeProvider, $httpProvider, $locationProvider) {

				//$locationProvider.html5Mode(true);
				

				$routeProvider.when('/', {
					templateUrl : 'app/components/home/home.html',
					controller : 'homeCtrl'
				}).when('/users', {
					templateUrl : 'app/components/users/views/userShow.html',
					controller : 'userShowCtrl'
				}).when('/users/new', {
					templateUrl : 'app/components/users/views/userAdd.html',
					controller : 'userAddCtrl'
				}).when('/users/:id', {
					templateUrl : 'app/components/users/views/userEdit.html',
					controller : 'userEditCtrl'
				}).when('/userProfile/:id', {
					templateUrl : 'app/components/users/views/userProfile.html',
					controller : 'userProfileCtrl'
				}).when('/upload/:id', {
					templateUrl : 'app/components/users/views/userFiles.html',
					controller : 'userFilesCtrl'
				}).when('/uploadPhoto/:id', {
					templateUrl : 'app/components/users/views/userPhoto.html',
					controller : 'userPhotoCtrl'
				}).when('/login', {
					templateUrl : 'app/components/login/login.html',
					controller : 'loginCtrl'
				}).otherwise('/');

				$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

			})
		.run(function(auth) {
			// Initialize auth module with the home page and login/logout path
			// respectively
			auth.init('/', '/login', '/logout');

		});

