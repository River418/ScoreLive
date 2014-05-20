'use strict';

var myApp = angular.module('scoreliveApp');
  myApp.service('Matchs', function(){
    var service = {  
      matchs : [
        {"league_name": "亚冠杯", "match_home_rank": "2", "match_home_name": "丰田", "match_visiting_rank": "3",
        "match_visiting_name": "恒大", "date": "04-10", "time": "16:30", "first_win": "1.96", "first_draw": "3.2",
        "first_lose": "3.6", "last_win": "1.89", "last_draw": "3.97", "last_lose": "3.4",
        "first_returnRate": "90.2", "last_returnRate": "90.19", "matchId": 2},
        {"league_name": "亚冠杯", "match_home_rank": "4", "match_home_name": "丰田", "match_visiting_rank": "3",
        "match_visiting_name": "恒大", "date": "04-10", "time": "16:30", "first_win": "1.96", "first_draw": "3.2",
        "first_lose": "3.6", "last_win": "1.89", "last_draw": "3.97", "last_lose": "3.4",
        "first_returnRate": "90.2", "last_returnRate": "90.19", "matchId": 3}
      ],
      match : {
        "league_name": "亚冠杯", "match_home_rank": "2", "match_home_name": "丰田", "match_visiting_rank": "3",
        "match_visiting_name": "恒大", "date": "04-10", "time": "16:30", "matchId": 2,
        "companys": [
        {"company_name": "澳门", "first_win": "1.96", "first_draw": "3.2",
        "first_lose": "3.6", "last_win": "1.89", "last_draw": "3.97", "last_lose": "3.4",
        "first_returnRate": "90.2", "last_returnRate": "90.19"}
        ]
      },
      matchDetail : {
        "league_name": "亚冠杯", "match_home_rank": "2", "match_home_name": "丰田", "match_visiting_rank": "3",
        "match_visiting_name": "恒大", "date": "04-10", "time": "16:30","company_name": "澳门", "matchId": 2,
        "odds": [
        { "win": "1.96", "draw": "3.2","lose": "3.6", "oddDate": "04-15"}
        ]
        }
    };

    return service;
  });

  myApp.factory('ajax', ['$http', function($http){
    var firstPageData = function($scope, data){
      $scope.matchs = data;
    };
    var secondPageData = function($scope, data){
      var match ={},companys=[],company={};
      data.forEach(function(el, index){
        company = {};
        $scope.match.league_name = el.league_name;
        $scope.match.match_home_rank = el.match_home_rank;
        $scope.match.match_home_name = el.match_home_name;
        $scope.match.match_visiting_name = el.match_visiting_name;
        $scope.match.match_visiting_rank = el.match_visiting_rank;
        $scope.match.matchId = el.matchId;
        company.company_id = el.company_id;
        company.company_name = el.company_name;
        company.first_draw = el.first_draw;
        company.first_lose = el.first_lose;
        company.first_returnRate = el.first_returnRate;
        company.first_win = el.first_win;
        company.last_draw = el.last_draw;
        company.last_lose = el.last_lose;
        company.last_returnRate = el.last_returnRate;
        company.last_win = el.last_win;
        companys.push(company);
      });
      match.companys = companys;
      $scope.match.companys = match.companys;
    };
    var thirdPageData = function($scope, data){
      $scope.matchDetail.odds = data;
      $scope.matchDetail.league_name = $scope.match.league_name;
      $scope.matchDetail.match_home_rank = $scope.match.match_home_rank;
      $scope.matchDetail.match_home_name = $scope.match.match_home_name;
      $scope.matchDetail.match_visiting_name = $scope.match.match_visiting_name;
      $scope.matchDetail.match_visiting_rank = $scope.match.match_visiting_rank;
      $scope.matchDetail.matchId = $scope.match.matchId;
    };

    return function(config, $scope, page){
      $http({
        method: 'GET',
        url: '/ScoreLive/'+config.url,
        cache: false
      }).
      then(function(data, status) {
        switch(page){
          case 1:
              firstPageData($scope, data.data);
              break;
          case 2:
              secondPageData($scope, data.data);
              break;
          case 3:
              thirdPageData($scope, data.data);
              break;
        }
      })
    };
  }]);

  myApp.controller('MainCtrl', function ($scope, Matchs, ajax) {
    $scope.matchs = Matchs.matchs;
    $scope.selectOptions = [
    {value:"欧赔",key:"odds"},{value:"亚盘",key:"hdc"},{value:"大小球",key:"gl"}];
    var config = {
      url : 'IndexRealLayerOneService?type=odds&matchid=1&num=2&date=20140424'
    };
    $scope.selectOption = $scope.selectOptions[0];
    ajax(config, $scope, 1);
    $scope.select = function(){
      config.url = 'IndexRealLayerOneService?type='+$scope.selectOption.key+'&matchid=1&num=2&date=20140424';
      ajax(config, $scope, 1);
    };
    $scope.goToMatch = function(){
      config.url = 'IndexRealLayerTwoService?type=odds&matchid=1&num=2';
      ajax(config, $scope, 2);
    };
    $scope.match = Matchs.match;
    $scope.goToDetail = function(){
      var config = {
        url: 'IndexRealLayerThirdService?type=odds&matchid=5&companyid=2'
      };
      ajax(config, $scope, 3);
    };
    $scope.matchDetail = Matchs.matchDetail;
  });
