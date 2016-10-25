var ang = angular.module('testcase', ['ngGrid','ionic'])
.factory('focusForm', function($timeout, $window) {
	return function(id) {
	  $timeout(function() {
		var element = $window.document.getElementById(id);
		console.log(element);
		if(element)
		  element.focus();
	  });
	};
})
.directive('myEnter', function () {
	return function (scope, element, attrs) {
		element.bind("keydown keypress", function (event) {
			if(event.which === 13) {
				scope.$apply(function (){
					scope.$eval(attrs.myEnter);
				});

				event.preventDefault();
			}
		});
	};
});	