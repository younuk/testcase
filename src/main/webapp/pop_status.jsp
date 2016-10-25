<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");
        response.setHeader("pragma","no-cache");
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="euc-kr">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    
    <title>TestCase</title>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>    
	<script src="http://code.jquery.com/jquery-1.7.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.3.7/angular.js" data-semver="1.3.7" data-require="angular.js@*"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/ng-grid/2.0.11/ng-grid.min.js" data-semver="2.0.11" data-require="ng-grid@*"></script>
	<script type="text/javascript" src="js/xml2json2.js" charset="utf-8"></script>
	<script src="assets/lib/ionic/js/angular/angular-cookies.js"></script>
	<script src="js/json2.js"></script>
    <script src="js/common.js"></script>
	<script>
		function getUnixTimeStamp(){
			return Math.floor(new Date().getTime() / 1000);
		}				
	</script>		
</head>	

<script type="text/javascript">
angular.module('popStatus', ['ngCookies'])
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
})			
.controller('popStatusCtrl', function($scope, $http, $timeout, focusForm) {

	$scope.envList = [];
	$scope.checkedList = [];
	
	// get env list
	var urls = "conf/status.json";
	var inParams = {"tm":getUnixTimeStamp()};
	$http.get(urls, {params: inParams})
	.success(function(dt) {	
		$scope.envList = dt.status;
	})
	.error(function(data) {
	});
	
	
	// button click
	$scope.btnCancelClick = function(){
		self.close();
	}
	
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
		opener.setHeaderStatus(ii);
		self.close();
	}
	
	
});

</script>

<body data-page="home" ng-app="popStatus" ng-controller="popStatusCtrl">

 <ion-content has-header="true" overflow-scroll="true" >

	 <div class="mainTitleBand">
		사용자상황 선택
	 </div>
	 
	<div style="position:relative; width:100%; margin-top:10px">
	
	  <!--  VOD  -->
	  <div style="float:left; border:2px solid #c0c0c0; padding:5px; margin-right:10px;">
	  	<div style="float:left">
		  	<table>
		  	 <tr><Td colspan=2 class="tdSkyCenter"><strong>사용자상황</strong></Td></tr>
		  	 <tr ng-repeat="im in envList | filter:{ty:'su1'}:true" >
		  	  <td><input type=checkbox name='chk' ng-model="im.checked" class="chkBox" /></td>
		  	  <td>{{im.nm}}</td>
		  	 </tr>
		  	</table>
	  	</div>
	  	<div style="float:left">
		  	<table>
		  	 <tr><Td colspan=2><strong>&nbsp;</strong></Td></tr>
		  	 <tr ng-repeat="im in envList | filter:{ty:'su2'}:true" >
		  	  <td><input type=checkbox name='chk' ng-model="im.checked" class="chkBox" /></td>
		  	  <td>{{im.nm}}</td>
		  	 </tr>
		  	</table>	  	
		</div>
	  </div>

	</div>

    <Br/>
	<table width=100%>
	 <tr>
	  <td align=center>
			<input type=button name="btnOK" id="btnOK" value=" 확인 " ng-click="btnOKClick()" style="width:100px"/>
			<input type=button name="btnCancel" id="btnCancel" value=" 취소 " ng-click="btnCancelClick()" style="width:100px"/>
	  </td>
	 </tr>
	</table>
	

 </ion-content>

 
</body>
</html>