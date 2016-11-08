<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");
        response.setHeader("pragma","no-cache");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>

    <title>TestCase</title>
    <link rel="stylesheet" type="text/css" href="css/cms.css"/>

    <script type="text/javascript" src="assets/js/jQuery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.3.7/angular.js" data-semver="1.3.7" data-require="angular.js@*"></script>
    <script type="text/javascript" src="js/xml2json2.js" charset="utf-8"></script>
    <script src="assets/lib/ionic/js/angular/angular-cookies.js"></script>
    <script src="js/json2.js"></script>
    <script src="js/common.js"></script>

    <script type="text/javascript">
    angular.module('popFavGenre', ['ngCookies'])
    .factory('focusForm', function($timeout, $window) {
        return function(id) {
          $timeout(function() {
            var element = $window.document.getElementById(id);
            if(element) element.focus();
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
    })
    .controller('popFavGenreCtrl', function($scope, $http, $timeout, focusForm) {

        $scope.envList = [];
        $scope.checkedList = [];

        // get env list
        var urls = "conf/status.json";
        var inParams = {"tm":getUnixTimeStamp()};
        $http.get(urls, {params: inParams})
        .success(function(dt) {
            $scope.envList = dt.fav_genre;
        })
        .error(function(data) {
        });

        // button click
        $scope.btnCancelClick = function(){
            self.close();
        };

        // button save
        $scope.btnOKClick = function(){
            var ii = "";
            var cnt = $scope.envList.length;
            for(var i=0; i<cnt; i++){
                if($scope.envList[i].checked == true){
                    ii += $scope.envList[i].id+",";
                }
            }
            if(ii.length>1){
                ii = ii.substr(0, ii.length-1);
            }
            opener.setHeaderFavGenre(ii);
            self.close();
        };
    });
    </script>
</head>

<body data-page="home" ng-app="popFavGenre" ng-controller="popFavGenreCtrl">

<div class="layer-wrap pop-wrap" style="display: block;">
    <div class="layer-header">
        <h2>컨텐츠 선호장르 선택</h2>
    </div>
    <div class="layer-content">
        <!-- Contents Area -->
        <div class="wiget-gbx" style="width:140px;float:left;">
            <h3>뉴스</h3>
            <table class="wiget-table tbl_row">
                <tbody>
                    <tr ng-repeat="im in envList | filter:{ty:'news'}:true">
                      <td><input type=checkbox name='chk' ng-model="im.checked"/></td>
                      <td>{{im.nm}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="wiget-gbx" style="width:300px;float:left;">
            <h3>VOD</h3>
            <table class="wiget-table tbl_row" style="width:148px;float:left;">
                <tbody>
                    <tr ng-repeat="im in envList | filter:{ty:'vod1'}:true">
                      <td><input type=checkbox name='chk' ng-model="im.checked" /></td>
                      <td>{{im.nm}}</td>
                    </tr>
                </tbody>
            </table>
            <table class="wiget-table tbl_row" style="width:148px;float:left;">
                <tbody>
                     <tr ng-repeat="im in envList | filter:{ty:'vod2'}:true">
                      <td><input type=checkbox name='chk' ng-model="im.checked" /></td>
                      <td>{{im.nm}}</td>
                     </tr>
                </tbody>
            </table>
        </div>
        <div class="wiget-gbx" style="width:300px;float:left;">
            <h3>Music</h3>
            <table class="wiget-table tbl_row" style="width:148px;float:left;">
                <tbody>
                    <tr ng-repeat="im in envList | filter:{ty:'music1'}:true">
                      <td><input type=checkbox name='chk' ng-model="im.checked" /></td>
                      <td>{{im.nm}}</td>
                    </tr>
                </tbody>
            </table>
            <table class="wiget-table tbl_row" style="width:148px;float:left;">
                <tbody>
                    <tr ng-repeat="im in envList | filter:{ty:'music2'}:true">
                      <td><input type=checkbox name='chk' ng-model="im.checked" /></td>
                      <td>{{im.nm}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <!-- /Contents Area -->
    </div>
    <div class="divBttm">
        <button class="btn btn-primary" ng-click="btnOKClick()"><span class="icon-check"></span>확인</button>
        <button class="btn" ng-click="btnCancelClick()"><span class="icon-x"></span>취소</button>
    </div>
</div>

</body>
</html>