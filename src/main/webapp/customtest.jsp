<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");
        response.setHeader("pragma","no-cache");
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
    <link rel="stylesheet" href="css/style_customtest.css" type="text/css"/>

    <script>
        var _LOGIN_ID = "${authVO.login_id}";
    </script>

    <script type="text/javascript" src="assets/js/jQuery.js"></script>
    <script type="text/javascript" src="assets/js/less.js"></script>
    <script type="text/javascript" src="assets/lib/ionic/js/ionic.bundle.js"></script>
    <script type="text/javascript" src="assets/ionic/material-design/ionic.material.min.js"></script>
    <script type="text/javascript" src="js/ng-grid.min.js" data-semver="2.0.11" data-require="ng-grid@*"></script>
    <script src="js/json2.js"></script>
    <script src="js/xml2json.js"></script>
    <script src="js/common.js"></script>
    <script src="js/ang_common.js"></script>
    <script src="js/customtest.js"></script>
</head>

<body data-page="home" id="casetest" ng-app="testcase" ng-controller="casetestCtrl">

<ion-header-bar align-title="left" class="bar-positive">
  <img src="images/kt_logo.jpg" style="width:60px; height:44px"/>
  <h1 class="title">kt 지능형감성대화서버 Service Test</h1>
</ion-header-bar>

<div class="bar bar-subheader" style="height:50px;">
     <div class="mainTitleBox">
        <div class="mainTitleTab" ng-click="goPage('casetest.do')">Case Test</div>
        <div class="mainTitleTab" ng-click="goPage('singletest.do')">Single Test</div>
        <div class="mainTitleTabSel" ng-click="goPage('customtest.do')">Custom Test</div>
        &nbsp;&nbsp;&nbsp;
        <input type="button" value=" CASETEST MANAGE " ng-click="goPage('casetestManage.do')" style="height:33px; line-height:30px; font-size:12px"/>
    </div>
</div>

 <ion-content  has-header="true" overflow-scroll="true" >

     <!-- main input area -->
     <div class="infoOutBox">
       <table id="searchBox">
        <tr>
         <td><input type="text" name="edtSearch" id="edtSearch"  onkeypress="if(event.keyCode==13) { btnSearchClick(); return false;}"/>
            &nbsp;<input type="button" name="btnReg" id="btnReg" value=" 검색 " ng-click="btnSearchClick()"/>
            &nbsp;<input type="button" name="btnReg" id="btnReg" value=" 추가 " ng-click="btnEditClick('')"/></td>
        </tr>
       </table>
       <div style="height:3px"></div>
       <table width=600 cellspacing=1 cellpadding=2 style="border-collapse:collapse; border:1px gray solid;">
         <tr>
          <td class='td_header'>케이스그룹</td>
          <td class='td_header'>Cnt</td>
          <td class='td_header'>등록자</td>
          <td class='td_header'>등록일</td>
          <td class='td_header'>기능</td>
         </tr>
         <tr ng-repeat="im in testcaseGroupList">
           <td class="td_left"  style="border:1px gray solid; padding-left:5px">{{im.group_nm}}</td>
           <td class="td_center"  style="border:1px gray solid;">{{im.cnt}}</td>
           <td class="td_center" style="border:1px gray solid;">{{im.login_nm}}</td>
           <td class="td_center" style="border:1px gray solid;">{{im.rgsde}}</td>
           <td class="td_center" style="border:1px gray solid;">
              <input type="button" name='btnTest' value='TEST' ng-click="btnTestClick(im.gid)" />
              <input type="button" name='btnEdit' value='EDIT' ng-click="btnEditClick(im.gid)" />
           </td>
         </tr>
       </table>
     </div>

     <!-- Result List  ng-repeat="im in testCaseResult" -->
     <div class="resultBox">
        <table ng-show="testCaseResult.length != 1">
            <tr>
                <td class="smry1">Test Case Cnt.</td>
                <td class="smry1">Action Cnt.</td>
                <td class="smry1">Result</td>
                <td class="smry1">ActType</td>
                <td class="smry1">Service Domain</td>
                <td class="smry1">ServiceID</td>
            </tr>
            <tr>
                <td class="smry3" rowspan="2">{{summaryData.test_count}}</td>
                <td class="smry3" rowspan="2">{{testCaseResult.length}}</td>
                <td class="smry3" rowspan="2">{{summaryData.all_match_count}}</td>
                <td class="smry2">{{summaryData.act_type_match_count}}</td>
                <td class="smry2">{{summaryData.uinfo_match_count}}</td>
                <td class="smry2">{{summaryData.service_id_match_count}}</td>
            </tr>
            <tr>
                <td class="smry2"><span style="color:red">{{testCaseResult.length - summaryData.act_type_match_count}}</span></td>
                <td class="smry2"><span style="color:red">{{testCaseResult.length - summaryData.uinfo_match_count}}</span></td>
                <td class="smry2"><span style="color:red">{{testCaseResult.length - summaryData.service_id_match_count}}</span></td>
            </tr>
        </table>

         <div ng-repeat="im in testCaseResult">
            <table width="100%" border=0 cellspacing=0 cellpadding=0>
                <tr>
                    <td colspan="4" class="td_header_dark">
                        &nbsp;&nbsp;<strong>{{im.i}}({{ im.result_idx }}). {{im.baseTcDt.catNm}}->{{im.baseTcDt.exam}} </strong> <br/>
                        <span style='font-size:8px; margin-left:20px'>uword : {{im.p_uword}}</span>
                    </td>
                    <td class="td_header_dark" width=80 align=center>
                       <input type="button" name='btnShowActionXML' id='btnShowActionXML' value=' XML ' ng-click="showActionXML(im.i)"/>
                    </td>
                </tr>
                <tr><td colspan="5" class="tdGrayLine"></td></tr>
                <tr>
                  <td class="td_header_now" colspan="2">Item</td>
                  <td class="td_header">예상 결과(정상)</td>
                  <td class="td_header">실제 결과</td>
                  <td class="td_header_100">결과</td>
                </tr>
                <tr><td colspan="5" class="tdGrayLine"></td></tr>
                <tr>
                  <td class="td_header_now" colspan="2">Message</td>
                  <td class="td_left"></td>
                  <td class="td_left">{{im.mesg}}</td>
                  <td class="td_center"></td>
                </tr>
                <tr><td colspan="5" class="tdGrayLine"></td></tr>
                <tr>
                  <td class="td_header_now" colspan="2">sysAct</td>
                  <td class="td_left">&nbsp;{{im.baseTcDt.ans_sysAct}}</td>
                  <td class="td_left">&nbsp;{{im.p_sysAct}}</td>
                  <td class="td_center">
                    <span class='blueResult' ng-show=" im.baseTcDt.ans_sysAct == im.p_sysAct ">OK</span>
                    <span class='redResult' ng-show=" im.baseTcDt.ans_sysAct != im.p_sysAct ">NOK</span>
                  </td>
                </tr>
                <tr><td colspan="5" class="tdGrayLine"></td></tr>
                <tr>
                  <td class="td_header_now" colspan="2">actType</td>
                  <td class="td_left">&nbsp;{{im.baseTcDt.actTypeStr}} ({{im.baseTcDt.ans_actType}})</td>
                  <td class="td_left">&nbsp;{{im.actTypeStr}} ({{im.actType}})</td>
                  <td class="td_center">
                    <span class='blueResult' ng-show=" im.baseTcDt.ans_actType == im.actType ">OK</span>
                    <span class='redResult' ng-show=" im.baseTcDt.ans_actType != im.actType ">NOK</span>
                  </td>
                </tr>
                <tr><td colspan="5" class="tdGrayLine"></td></tr>
                <tr>
                  <td class="td_header_now" colspan="2">serviceDomain</td>
                  <td class="td_left">&nbsp;{{im.ori_service_domain_str}}</td>
                  <td class="td_left">&nbsp;{{im.actGroup_str}} ({{im.actGroup}})</td>
                  <td class="td_center">
                    <span class='blueResult' ng-show=" im.actGroup_match == '1' ">OK</span>
                    <span class='redResult' ng-show=" im.actGroup_match != '1' ">NOK</span>
                  </td>
                </tr>
                <tr><td colspan="5" class="tdGrayLine"></td></tr>
                <tr>
                  <td class="td_header_now" colspan="2">serviceId</td>
                  <td class="td_left">&nbsp;<span ng-show="im.baseTcDt.chk_method == 'equal'"><strong>&nbsp;{{im.baseTcDt.service_id_str}}</strong></span>
                                    <span ng-show="im.baseTcDt.chk_method != 'equal'" style='color:blue'><strong>&nbsp;{{im.baseTcDt.service_id_tmpl}}</strong></span>
                  </td>
                  <td class="td_left"><p style="word-wrap:break-word;width:390px;margin:0px;">{{im.serviceId}}</p>
                    <!--<div   width:600px;"></div> -->
                  </td>
                  <td class="td_center">
                    <span class='blueResult' ng-show=" im.service_id_check_result == 'Good' ">OK</span>
                    <span class='redResult' ng-show=" im.service_id_check_result != 'Good' ">NOK</span>
                  </td>
                </tr>
                <tr><td colspan="5" class="tdGrayLine"></td></tr>
                <tr>
                  <td class="td_header_now" rowspan="7" width="70px">search</td>
                  <td class="td_white_bold" width="70px">&nbsp;srchWord</td>
                  <td class="td_left" width="120px">&nbsp;{{im.baseTcDt.ans_srchword}}</td>
                  <td class="td_left">&nbsp;{{im.srchWord}}</td>
                  <td class="td_center"></td>
                </tr>
                <tr><td colspan="4" class="tdGrayLineL"></td></tr>
                <tr>
                  <td class="td_white_bold">&nbsp;sWord</td>
                  <td class="td_left">&nbsp;{{im.baseTcDt.ans_sword}}</td>
                  <td class="td_left">&nbsp;{{im.sWord}}</td>
                  <td class="td_center"></td>
                </tr>
                <tr><td colspan="4" class="tdGrayLineL"></td></tr>
                <tr>
                  <td class="td_white_bold">&nbsp;srchOpt</td>
                  <td class="td_left">&nbsp;{{im.baseTcDt.ans_srchopt}}</td>
                  <td class="td_left">&nbsp;{{im.srchOpt}}</td>
                  <td class="td_center"></td>
                </tr>
                <tr><td colspan="4" class="tdGrayLineL"></td></tr>
                <tr>
                  <td class="td_white_bold">&nbsp;srchQry</td>
                  <td class="td_left">&nbsp;{{im.baseTcDt.ans_srchqry}}</td>
                  <td class="td_left">&nbsp;{{im.srchQry}}</td>
                  <td class="td_center"></td>
                </tr>
            </table>
        </div>
      </div>
 </ion-content>
 <br/>
 <br/>
 <br/>
</body>
</html>