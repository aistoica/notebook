angular
		.module('hello', [ 'ngRoute', 'ngResource', 'users', 'auth', 'home', 'navigation', 'validation' ])
		.config(

				function($routeProvider, $httpProvider, $locationProvider) {

					$locationProvider.html5Mode(true);

					$routeProvider.when('/', {
						templateUrl : 'js/home/home.html',
						controller : 'home'
					}).when('/users', {
						templateUrl : 'js/users/users.html',
						controller : 'users'
					}).when('/users/new', {
						templateUrl : 'js/users/user_add.html',
						controller : 'usersAddCtrl'
					}).when('/users/:id', {
						templateUrl : 'js/users/user_edit.html',
						controller : 'usersEditCtrl'
					}).when('/userProfile/:id', {
						templateUrl : 'js/users/user_profile.html',
						controller : 'usersProfileCtrl'
					}).when('/upload/:id', {
						templateUrl : 'js/users/upload.html',
						controller : 'usersUploadCtrl'
					}).when('/uploadPhoto/:id', {
						templateUrl : 'js/users/upload_photo.html',
						controller : 'usersPhotoUploadCtrl'
					}).when('/login', {
						templateUrl : 'js/navigation/login.html',
						controller : 'navigation'
					}).otherwise('/');

					$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

				}).run(function(auth) {

				// Initialize auth module with the home page and login/logout path
				// respectively
				auth.init('/', '/login', '/logout');

		});
