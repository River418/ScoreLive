'use strict';

angular
  .module('scoreliveApp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/detail', {
        templateUrl: 'views/detail.html',
        controller: 'MainCtrl'
      })
      .when('/change', {
        templateUrl: 'views/companyDetail.html',
        controller: 'MainCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
