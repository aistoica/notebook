// based on blog.yodersolutions.com

var validationApp = angular.module('validation', []);

validationApp.directive('showErrors', function () {
	return {
		restrict: 'A', //restrict to atribute
		require:'^form', //require a form controller
		link: function (scope, el, attr, formCtrl) {
			
			//find the text box element, which has the 'name' attribute
			var inputEl = el[0].querySelector("[name]");
			
			//convert the native element to angular element
			var inputNgEl = angular.element(inputEl);
			
			//get the name of the text box
			var inputName = inputNgEl.attr('name');
			
			//get the help-block
			var helpText = angular.element(el[0].querySelector(".help-block"));
			
			//apply the has-error class after the user leaves the text box
			inputNgEl.bind('blur', function () {
				el.toggleClass('has-error', formCtrl[inputName].$invalid);
				helpText.toggleClass('hide', formCtrl[inputName].$valid);
			});
			
			scope.$on('show-errors-event', function() {
				el.toggleClass('has-error', formCtrl[inputName].$invalid);
				helpText.toggleClass('hide', formCtrl[inputName].$valid);
			})
		}
	}
});
