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
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title>kt 지능형감성대화서버 Service Test</title>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    
    
    
	<script src="http://code.jquery.com/jquery-1.7.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.3.7/angular.js" data-semver="1.3.7" data-require="angular.js@*"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/ng-grid/2.0.11/ng-grid.min.js" data-semver="2.0.11" data-require="ng-grid@*"></script>
	<script type="text/javascript" src="js/xml2json2.js" charset="euc-kr"></script>
	<script src="assets/lib/ionic/js/angular/angular-cookies.js"></script>
	<script src="assets/lib/ionic/js/angular-ui/angular-ui-router.js"></script>
	<script src="js/json2.js"></script>
    <script src="js/common.js"></script>
    <script>
      var categoryList = ${categoryList};
    </script>
    <script src="js/pop_casetestEdit.js"></script>
   	<script>
		function getUnixTimeStamp(){
			return Math.floor(new Date().getTime() / 1000);
		}				
	</script>	
</head>	

<body data-page="home" ng-app="testEdit" ng-controller="testEdit">

 <ion-content has-header="true" overflow-scroll="true" >

    <div style="margin-left:10px; margin-top:10px; font-size:20px; font-weight:bold; color:maroon" ng-show="data.sn == null"> 
	   TestCase Regist
	</div>
    <div style="margin-left:10px; margin-top:10px; font-size:20px; font-weight:bold; color:maroon" ng-show="data.sn != null"> 
	   TestCase Edit :: {{data.exam}}
	</div>	

	<hr size=1/>
	
	<table cellspacing=1 cellpadding=3 border=0 bgcolor=#c0c0c0>
	  <form name="form" method="post" action="testcaseiom.do">
		<tr><Td colspan=4 bgcolor=#c0c0c0><span style='font-size:8px'>priavte header</span></td></tr>			
		<tr>
		 	<td class='td_header'>status</td>
		 	<td class='td_left'><input type="text" name="p_status" id="p_status" ng-model="data.p_status"  size=35 /></td>
		 	<td class='td_header'>event</td>
		 	<td class='td_left'><input type="text" name="p_event" id="p_event" ng-model="data.p_event"  size=35/></td>
		</tr>
		<tr>
		 	<td class='td_header'>uinfo</td>
		 	<td class='td_left'><input type="text" name="p_uinfo" id="p_uinfo" ng-model="data.p_uinfo"  size=35 /></td>
		 	<td class='td_header'>context</td>
		 	<td class='td_left'><input type="text" name="p_context" id="p_context" ng-model="data.p_context"  size=35/></td>
		</tr>	
		<tr><Td colspan=4 bgcolor=#c0c0c0><span style='font-size:8px'>test body</span></td></tr>	
		<tr>
		  <td class='td_header'>category</td>
		  <td class='td_left' colspan=3>
			 <select name="cat_gid" id="cat_gid" onChange="catGroupChange()" style="width:150px">
				${catTop}
			 </select>
			 <select name="cat_id" id="cat_id" style="width:150px">
				${catMain}
			 </select>			 
			 
		  </td>
		</tr>
		<tr>
		    <td class='td_header'>reqmsg</td>
		    <td class='td_left' colspan=3><input type="text" name="exam" id="exam" ng-model="data.exam" size=80/></td>
		</tr>
		<tr>
		 	<td class='td_header'>service domain</td>
		 	<td class='td_left'><input type="text" name="service_domain" id="service_domain" ng-model="data.service_domain"  size=35 /><br/>(0~6숫자)</td>
		 	<td class='td_header'>내부Domain</td>
		 	<td class='td_left'><input type="text" name="in_domain" id="in_domain" ng-model="data.in_domain"  size=35/></td>
		</tr>
		<tr>
		 	<td class='td_header'>serviceId</td>
		 	<td class='td_left' colspan=3><input type="text" name="service_id_str" id="service_id_str" ng-model="data.service_id_str"  size=80/></td>
		</tr>
		<tr><Td colspan=4 bgcolor=#ffffff></td></tr>
		<tr>
		 	<td class='td_header'>sysAct</td>
		 	<td class='td_left'><input type="text" name="ans_sysAct" id="ans_sysAct" ng-model="data.ans_sysAct"  size=35/></td>
			<td class='td_header'>actType</td>
		 	<td class='td_left'><input type="text" name="ans_actType" id="ans_actType" ng-model="data.ans_actType"  size=35/></td>				  	 
		</tr>	
		<tr><Td colspan=4 bgcolor=#ffffff></td></tr>		
		<tr>
		 	<td class='td_header'>srchWord</td>
		 	<td class='td_left'><input type="text" name="srchword" id="srchword" ng-model="data.ans_srchword"  size=35/></td>
			<td class='td_header'>srchOpt</td>
		 	<td class='td_left'><input type="text" name="srchopt" id="srchopt" ng-model="data.ans_srchopt"  size=35/></td>				  	 
		</tr>
		<tr>
		 	<td class='td_header'>srchQry</td>
		 	<td class='td_left'><input type="text" name="srchqry" id="srchqry" ng-model="data.ans_srchqry"  size=35/></td>
			<td class='td_header'>sword</td>
		 	<td class='td_left'><input type="text" name="sword" id="sword" ng-model="data.ans_sword"  size=35/></td>				  	 
		</tr>
		<tr><Td colspan=4 bgcolor=#c0c0c0><span style='font-size:8px'>matching option</span></td></tr>		
		<tr>
		 	<td class='td_header'>checkMethod</td>
		 	<td class='td_left'><input type="text" name="chk_method" id="chk_method" ng-model="data.chk_method" size=35/><Br/>(equal, ptrn)</td>
			<td class='td_header'>serviceIdTmpl</td>
		 	<td class='td_left'><input type="text" name="service_id_tmpl" id="service_id_tmpl" ng-model="data.service_id_tmpl"  size=35/></td>				  	 
		</tr>
		<tr>		 
		 	<td class='td_header'>serviceIdPtrn</td>
		 	<td class='td_left'><input type="text" name="service_id_ptrn" id="service_id_ptrn" ng-model="data.service_id_ptrn"  size=35/></td>
			<td class='td_header'>-</td>
		 	<td class='td_left'>-</td>				  
		</tr>		
		<tr>
		 <td colspan=4 align=center bgcolor=#ffffff>
		   <input type=button name='btnSubmit' value=' SEND ' ng-click="btnOKClick()"/>	
		   <input type=button name='btnCancel' value=' CLOSE ' onclick="self.close()"/>
		 </td>
		</tr>
	  </form>
	</table>

 
 </ion-content>
 
 
</body>
</html>