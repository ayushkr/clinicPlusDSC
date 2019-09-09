// Define the `phonecatApp` module
var phonecatApp = angular.module('phonecatApp', []);




phonecatApp.controller('a', function a($scope) {
    var employees = ['Catherine Grant', 'Monica Grant',
        'Christopher Grant', 'Jennifer Grant'];
    $scope.ourEmployees = employees;
    $scope.world = 'world';
});

// Define the `PhoneListController` controller on the `phonecatApp` module
phonecatApp.controller('PhoneListController', function PhoneListController($scope) {
  $scope.phones = [
    {
      name: 'Nexus S',
      snippet: 'Fast just got faster with Nexus S.'
    }, {
      name: 'Motorola XOOM™ with Wi-Fi',
      snippet: 'The Next, Next Generation tablet.'
    }, {
      name: 'MOTOROLA XOOM™',
      snippet: 'The Next, Next Generation tablet.'
    }
  ];
});
