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
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title>kt 지능형감성대화서버 Service Test</title>
    <link rel="stylesheet" href="css/smoothness/jquery-ui-1.8.16.custom.css" type="text/css"/>	
    <link rel="stylesheet" href="assets/lib/ionic/css/ionic.css" type="text/css" />		
    <link rel="stylesheet" href="assets/ionic/material-design/ionic.material.min.css" type="text/css"/>
    <link rel="stylesheet" href="assets/css/icons/ionicons.min.css" type="text/css"/>		
	<link rel="stylesheet" type="text/css" href="js/ng-grid.css" data-semver="2.0.11" data-require="ng-grid@*" />
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
	<link rel="stylesheet" href="css/grid.css" type="text/css"/>
	
    <script type="text/javascript" src="assets/js/jQuery.js"></script>
    <script type="text/javascript" src="assets/js/less.js"></script>
	<script type="text/javascript" src="assets/lib/ionic/js/ionic.bundle.js"></script>			
    <script type="text/javascript" src="assets/ionic/material-design/ionic.material.min.js"></script>
	<script type="text/javascript" src="js/ng-grid.min.js" data-semver="2.0.11" data-require="ng-grid@*"></script>
	<script src="js/json2.js"></script>
	<script src="js/xml2json2.js"></script>	
    <script src="js/common.js"></script>
    <script src="js/ang_common.js"></script>	
    <script src="js/casetest_manage.js"></script>
</head>	

<body data-page="home" id="casetestManage" ng-app="testcase" ng-controller="casetestManageCtrl">

<ion-header-bar align-title="left" class="bar-positive">
  <img src="images/kt_logo.jpg" style="width:60px; height:44px"/>
  <h1 class="title">kt 지능형감성대화서버 Service Test >> 테스트케이스 관리</h1>
</ion-header-bar>
<div class="bar bar-subheader">
	 <div class="mainTitleBox">
		<div class="mainTitleTabSel" ng-click="goPage('casetest.do')">Case Test</div>
		<div class="mainTitleTab"    ng-click="goPage('singletest.do')">Single Test</div>
		<div class="mainTitleTab"    ng-click="goPage('customtest.do')">Custom Test</div>
		<div style="float:left; margin-left:20px">
	          <input type=text name="edtSearchExam" id="edtSearchExam" />
	    </div>
		<div style="float:left; margin-left:5px">
			<input type=button name="btnSearch" value=" SEARCH " style="width:100px; height:33px" ng-click="loadInitData()"/>
		</div>
		<div style="float:left; margin-left:5px">
			<input type=button name="btnAdd" value=" ADD " style="width:100px; height:33px" ng-click="addTestcase()"/>
		</div>		
	</div>
</div>

 <ion-content  has-header="true" overflow-scroll="false" >
	
	<div style="position:fixed; width:100%; margin-top:70px; margin-left:0px; font-size:16px">
	  	<strong>&nbsp;&nbsp;검색된 결과 : {{casetestList.length}}건 </strong>	  
    	<div class="gridStyle" ng-grid="gridOptions"></div>
	</div>

 </ion-content>
 <br/>
 <br/>
 <br/>
	  
</body>
</html>