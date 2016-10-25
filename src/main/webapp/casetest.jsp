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
    <link rel="stylesheet" href="css/style_casetest.css" type="text/css"/>

    <script>
        var _LOGIN_ID = "${authVO.login_id}";
    </script>

    <script type="text/javascript" src="assets/js/jQuery.js"></script>
    <script type="text/javascript" src="assets/js/less.js"></script>
    <script type="text/javascript" src="assets/lib/ionic/js/ionic.bundle.js"></script>
    <script type="text/javascript" src="assets/ionic/material-design/ionic.material.min.js"></script>
    <script type="text/javascript" src="js/ng-grid.min.js" data-semver="2.0.11" data-require="ng-grid@*"></script>
    <script src="js/json2.js"></script>
    <!--  script src="js/xml2json2.js"></script -->
    <script src="js/xml2json.js"></script>
    <script src="js/common.js"></script>
    <script src="js/ang_common.js"></script>
    <script src="js/casetest.js"></script>
</head>

<body data-page="home" id="casetest" ng-app="testcase" ng-controller="casetestCtrl">

<ion-header-bar align-title="left" class="bar-positive">
  <img src="images/kt_logo.jpg" style="width:60px; height:44px"/>
  <h1 class="title">kt 지능형감성대화서버 Service Test</h1>
</ion-header-bar>

<div class="bar bar-subheader" style="height:50px;">
     <div class="mainTitleBox">
        <div class="mainTitleTabSel" ng-click="goPage('casetest.do')">Case Test</div>
        <div class="mainTitleTab"    ng-click="goPage('singletest.do')">Single Test</div>
        <div class="mainTitleTab"    ng-click="goPage('customtest.do')">Custom Test</div>
        &nbsp;&nbsp;&nbsp;
        <input type="button" value=" CASETEST MANAGE "
                ng-click="goPage('casetestManage.do')" style="height:33px; line-height:30px; font-size:12px"/>
    </div>
</div>

 <ion-content  has-header="true" overflow-scroll="true" >

     <!-- Left Menu -->
     <div class="mainLeftChkBox">
        <div class="floatLeft">
          <input type="checkbox" name='chk_leftall' id='chk_leftall' class="chkBox" ng-click="leftMenuSelAll()"/>
        </div>
        <div class="floatLeft">&nbsp;<strong>전체선택</strong></div>
     </div>
     <div class="mainLeftMenu">
        <div style="position:relative; width:200px; height:30px;" ng-repeat="im in leftmenu">
            <div ng-class="{menuLeft0: im.dept == 0, menuLeft1: im.dept > 0}">
              <input type="checkbox" name='chk' ng-model="im.checked" class="chkBox" ng-click="leftMenuCLick(im)"/>
            </div>
            <div style="float:left; width:120px;">
               <span ng-class="{txtBold:im.dept == 0, txtNormal:im.dept > 0}">{{im.nm}}</span>
            </div>
        </div>

     </div>

     <!-- main input area -->
     <div class="infoOutBox">
		<table>
			<tr>
			     <td><span style='font-weight:bold'>User ID :</span> <input type="text" name="id" id="id" size=40/></td>
			</tr>
		</table>
		
        <div class="uinfoBox1">

		<!-- 가입서비스, 선호장르 입력 -->
		<div class="uinfoBox">
           <table>
               <tr>
                   <td class="fontBold" width="90px">가입서비스</td>
                   <td>&nbsp;<input type="checkbox" name="chk_uinfo1" id="chk_uinfo1" class="chkBox" ng-model="serviceDomainList[1].checked"/>&nbsp;비서
                            <input type="checkbox" name="chk_uinfo2" id="chk_uinfo2" class="chkBox" ng-model="serviceDomainList[2].checked"/>&nbsp;OTV
                            <input type="checkbox" name="chk_uinfo3" id="chk_uinfo3" class="chkBox" ng-model="serviceDomainList[3].checked"/>&nbsp;전화
                            <input type="checkbox" name="chk_uinfo4" id="chk_uinfo4" class="chkBox" ng-model="serviceDomainList[4].checked"/>&nbsp;홈캠
                            <input type="checkbox" name="chk_uinfo5" id="chk_uinfo5" class="chkBox" ng-model="serviceDomainList[5].checked"/>&nbsp;홈IOT
                            <input type="checkbox" name="chk_uinfo6" id="chk_uinfo6" class="chkBox" ng-model="serviceDomainList[6].checked"/>&nbsp;지니뮤직
                   </td>
               </tr>
               <tr>
                   <td class="fontBold">선호장르</td>
                   <td>&nbsp;<input type="text" name="edtFavGenre" id="edtFavGenre" size="45"/>
                             <input type="button" name="edt" id="btnAddGenre" value="+"  ng-click="showFavGenrePOPClick()"/></td>
               </tr>

               <tr>
                   <td class="fontBold">추천선호도</td>
                   <td>&nbsp;<select name="selFavPoint" id="selFavPoint"  class="select80">
                                <option value=""></option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                              </select></td>
               </tr>
               <tr><td colspan=2>&nbsp;</td></tr>
               <tr>
                   <td class="fontBold">Status</td>
                   <td>&nbsp;<input type="text" name="edtStatus" id="edtStatus" size="45"/>
                               <input type="button" name="btnAddStatus" id="btnAddStatus" value="+" ng-click="showStatusPOPClick()"/></td>
                 </tr>
           </table>
           <!-- 
             <div class="paddingTd">가입서비스</div>
             <div class="uinfoChkBox">
                <div class="floatLeft">
                  <input type="checkbox" name="chk_uinfo1" id="chk_uinfo1" class="chkBox" ng-model="serviceDomainList[1].checked">&nbsp;비서
                </div>
                <div class="floatLeft">
                  <input type="checkbox" name="chk_uinfo2" id="chk_uinfo2" class="chkBox" ng-model="serviceDomainList[2].checked">&nbsp;OTV
                </div>
                <div class="floatLeft">
                  <input type="checkbox" name="chk_uinfo3" id="chk_uinfo3" class="chkBox" ng-model="serviceDomainList[3].checked">&nbsp;전화
                </div>
                <div class="floatLeft">
                  <input type="checkbox" name="chk_uinfo4" id="chk_uinfo4" class="chkBox" ng-model="serviceDomainList[4].checked">&nbsp;홈캠
                </div>
                <div class="floatLeft">
                  <input type="checkbox" name="chk_uinfo5" id="chk_uinfo5" class="chkBox" ng-model="serviceDomainList[5].checked">&nbsp;홈IOT
                </div>
                <div class="floatLeft">
                  <input type="checkbox" name="chk_uinfo6" id="chk_uinfo6" class="chkBox" ng-model="serviceDomainList[6].checked">&nbsp;지니뮤직
                </div>
             </div>

             <div class="paddingTd">선호장르</div>
             <div class="uinfoGenre">
                <div class="floatLeft">
                  <input type="text" name="edtFavGenre" id="edtFavGenre" size=40/>
                </div>
                <div class="floatLeft">
                  <input type="button" name="edt" id="btnAddGenre" value="+"  ng-click="showFavGenrePOPClick()"/>
                </div>
                <div class="floatLeft">
                  추천선호도
                </div>
                <div class="floatLeft">
                  <select name="selFavPoint" id="selFavPoint"  class="select80">
                    <option value=""></option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                  </select>
                </div>
                
             </div>
             
                 -->
          </div>
       </div>

       <!-- deviceInfo 입력 -->
       <div class="dinfoBox">
           <table style="height:35px;">
               <tr>
                   <td class="fontBold">세탑단말버전(STB Ver)</td>
                   <td>&nbsp;<select name="STB_VER" id="STB_VER" class="select80">
                            <option value="0.4">0.4</option>
                            <option value="0.6">0.6</option>
                          </select></td>
               </tr>
               <tr>
                   <td class="fontBold">클라이언트 단말버전(stbVersion)</td>
                   <td>&nbsp;<select name="stbVersion" id="stbVersion" class="select80">
                            <option value="0.6">0.6</option>
                            <option value="0.4">0.4</option>
                          </select></td>
               </tr>

               <tr>
                   <td class="fontBold">devServiceID</td>
                   <td>&nbsp;<input type="text" name="devService_id" id="devService_id" /></td>
                 </tr>
                 <tr>
                   <td class="fontBold">Product ID</td>
                   <td>&nbsp;<input type="text" name="product_id" id="product_id" /></td>
                 </tr>
                 <tr>
                   <td class="fontBold">appID</td>
                   <td>&nbsp;<input type="text" name="appID" id="appID" /></td>
                 </tr>
           </table>
           <!--
            <div class="floatLeftBold">
                <div>세탑단말버전</div>
                <div>(STB Ver)</div>
                <div>
                  <select name="STB_VER" id="STB_VER" class="select80">
                    <option value="0.4">0.4</option>
                    <option value="0.6">0.6</option>
                  </select>
                </div>
            </div>
            <div class="floatLeftBold">
                <div>클라이언트 단말버전</div>
                <div>(stbVersion)</div>
                <div>
                  <select name="stbVersion" id="stbVersion" class="select80">
                    <option value="0.6">0.6</option>
                    <option value="0.4">0.4</option>
                  </select>
                </div>
            </div>
            <div class="floatLeftBold">
                <Br/><br/>
                <table>
                 <tr>
                   <td class="fontBold">devServiceID</td>
                   <td>&nbsp;<input type="text" name="devService_id" id="devService_id" /></td>
                 </tr>
                 <tr>
                   <td class="fontBold">Product ID</td>
                   <td>&nbsp;<input type="text" name="product_id" id="product_id" /></td>
                 </tr>
                 <tr>
                   <td class="fontBold">appID</td>
                   <td>&nbsp;<input type="text" name="appID" id="appID" /></td>
                 </tr>
                </table>
            </div>           -->
       </div>


       <!-- status 
       <div>
         <div class="floatLeftBold">Status</div>
         <div class="floatLeftBold">
           <input type="text" name="edtStatus" id="edtStatus" size=55/>
           <input type="button" name="btnAddStatus" id="btnAddStatus" value="+" ng-click="showStatusPOPClick()"/>
         </div>
       </div>
-->
       <div class="divider1"></div>
      </div>

      <!-- Summary -->
      <div class="summaryBox">
        <div class="floatLeft">
            <input type="button" value="START" class="btnStart" ng-click="testGo()"/>
        </div>
        <div class="floatLeftSmry">
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
                    <td class="smry3" rowspan=2>{{summaryData.test_count}}</td>
                    <td class="smry3" rowspan=2>{{testCaseResult.length}}</td>
                    <td class="smry3" rowspan=2>{{summaryData.all_match_count}}</td>
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
        </div>
      </div>


      <!-- Result List  ng-repeat="im in testCaseResult" -->
      <div class="resultBox">
         <div ng-repeat="im in testCaseResult">
            <table width="100%" border=0 cellspacing=0 cellpadding=0 bgcolor=red>
                <tr>
                    <td colspan=4 class="td_header_dark">
                        &nbsp;&nbsp;<strong>{{im.i}}({{ im.result_idx }}). {{im.baseTcDt.catNm}}->{{im.baseTcDt.exam}} </strong> <br/>
                        <span style='font-size:8px; margin-left:20px'>uword : {{im.p_uword}}</span>
                    </td>
                    <td class="td_header_dark" width=80 align=center>
                       <input type="button" name='btnShowActionXML' id='btnShowActionXML' value=' XML ' ng-click="showActionXML(im.uuid, im.sn)"/>
                    </td>
                </tr>
                <Tr><td colspan=5 class="tdGrayLine"></td></Tr>
                <tr>
                  <td class="td_header_now" colspan=2>Item</td>
                  <td class="td_header">예상 결과(정상)</td>
                  <td class="td_header">실제 결과</td>
                  <td class="td_header">결과</td>
                </tr>
                <Tr><td colspan=5 class="tdGrayLine"></td></Tr>
                <tr>
                  <td class="td_header_now" colspan=2>Message</td>
                  <td class="td_left"></td>
                  <td class="td_left">{{im.mesg}}</td>
                  <td class="td_center"></td>
                </tr>
                <Tr><td colspan=5 class="tdGrayLine"></td></Tr>
                <tr>
                  <td class="td_header_now" colspan=2>sysAct</td>
                  <td class="td_left">&nbsp;{{im.baseTcDt.ans_sysAct}}</td>
                  <td class="td_left">&nbsp;{{im.p_sysAct}}</td>
                  <td class="td_center">
                    <span class='blueResult' ng-show=" im.baseTcDt.ans_sysAct == im.p_sysAct ">OK</span>
                    <span class='redResult' ng-show=" im.baseTcDt.ans_sysAct != im.p_sysAct ">NOK</span>
                  </td>
                </tr>
                <Tr><td colspan=5 class="tdGrayLine"></td></Tr>
                <tr>
                  <td class="td_header_now" colspan=2>actType</td>
                  <td class="td_left">&nbsp;{{im.baseTcDt.actTypeStr}} ({{im.baseTcDt.ans_actType}})</td>
                  <td class="td_left">&nbsp;{{im.actTypeStr}} ({{im.actType}})</td>
                  <td class="td_center">
                    <span class='blueResult' ng-show=" im.baseTcDt.ans_actType == im.actType ">OK</span>
                    <span class='redResult' ng-show=" im.baseTcDt.ans_actType != im.actType ">NOK</span>
                  </td>
                </tr>
                <Tr><td colspan=5 class="tdGrayLine"></td></Tr>
                <tr>
                  <td class="td_header_now" colspan=2>serviceDomain</td>
                  <td class="td_left">&nbsp;{{im.ori_service_domain_str}}</td>
                  <td class="td_left">&nbsp;{{im.actGroup_str}} ({{im.actGroup}})</td>
                  <td class="td_center">
                    <span class='blueResult' ng-show=" im.actGroup_match == '1' ">OK</span>
                    <span class='redResult' ng-show=" im.actGroup_match != '1' ">NOK</span>
                  </td>
                </tr>
                <Tr><td colspan=5 class="tdGrayLine"></td></Tr>
                <tr>
                  <td class="td_header_now" colspan=2>serviceId</td>
                  <td class="td_left">&nbsp;<span ng-show="im.baseTcDt.chk_method == 'equal'"><strong>&nbsp;{{im.baseTcDt.service_id_str}}</strong></span>
                                    <span ng-show="im.baseTcDt.chk_method != 'equal'" style='color:blue'><strong>&nbsp;{{im.baseTcDt.service_id_tmpl}}</strong></span>
                 </td>
                  <td class="td_left">
                    <div  style="word-wrap:break-word; width:600px;">{{im.serviceId}}</div>
                  </td>
                  <td class="td_center">
                    <span class='blueResult' ng-show=" im.service_id_check_result == 'Good' ">OK</span>
                    <span class='redResult' ng-show=" im.service_id_check_result != 'Good' ">NOK</span>
                  </td>
                </tr>
                <Tr><td colspan=5 class="tdGrayLine"></td></Tr>
                <tr style="">
                  <td class="td_header_now" rowspan=7>search</td>
                  <td class="td_white_bold" style="background:#efefef" width=60>&nbsp;srchWord</td>
                  <td class="td_left">&nbsp;{{im.baseTcDt.ans_srchword}}</td>
                  <td class="td_left">&nbsp;{{im.srchWord}}</td>
                  <td class="td_center">
                  </td>
                </tr>
                <Tr><td colspan=4 class="tdGrayLineL"></td></Tr>
                <tr>
                  <td class="td_white_bold" style="background:#efefef" >&nbsp;sWord</td>
                  <td class="td_left">&nbsp;{{im.baseTcDt.ans_sword}}</td>
                  <td class="td_left">&nbsp;{{im.sWord}}</td>
                  <td class="td_center">
                  </td>
                </tr>
                <Tr><td colspan=4 class="tdGrayLineL"></td></Tr>
                <tr>
                  <td class="td_white_bold" style="background:#efefef" >&nbsp;srchOpt</td>
                  <td class="td_left">&nbsp;{{im.baseTcDt.ans_srchopt}}</td>
                  <td class="td_left">&nbsp;{{im.srchOpt}}</td>
                  <td class="td_center">
                  </td>
                </tr>
                <Tr><td colspan=4 class="tdGrayLineL"></td></Tr>
                <tr>
                  <td class="td_white_bold" style="background:#efefef" >&nbsp;srchQry</td>
                  <td class="td_left">&nbsp;{{im.baseTcDt.ans_srchqry}}</td>
                  <td class="td_left">&nbsp;{{im.srchQry}}</td>
                  <td class="td_center">
                  </td>
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