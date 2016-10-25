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
    <link rel="stylesheet" href="css/style_singletest.css" type="text/css"/>

    <script>
        var _LOGIN_ID = "${authVO.login_id}";
    </script>

    <script type="text/javascript" src="assets/js/jQuery.js"></script>
    <script type="text/javascript" src="assets/js/less.js"></script>
    <script type="text/javascript" src="assets/lib/ionic/js/ionic.bundle.js"></script>
    <script type="text/javascript" src="assets/ionic/material-design/ionic.material.min.js"></script>
    <script type="text/javascript" src="js/ng-grid.min.js" data-semver="2.0.11" data-require="ng-grid@*"></script>
    <script src="js/json2.js"></script>
    <script src="js/xml2json2.js"></script>
    <script src="js/common.js"></script>
    <script src="js/ang_common.js"></script>
    <script src="js/singletest.js"></script>
</head>

<body data-page="home" id="casetest" ng-app="testcase" ng-controller="casetestCtrl">

<ion-header-bar align-title="left" class="bar-positive">
  <img src="images/kt_logo.jpg" style="width:60px; height:44px"/>
  <h1 class="title">kt 지능형감성대화서버 Service Test</h1>
</ion-header-bar>
<div class="bar bar-subheader">
     <div class="mainTitleBox">
        <div class="mainTitleTab" ng-click="goPage('casetest.do')">Case Test</div>
        <div class="mainTitleTabSel" ng-click="goPage('singletest.do')">Single Test</div>
        <div class="mainTitleTab" ng-click="goPage('customtest.do')">Custom Test</div>
        &nbsp;&nbsp;&nbsp;
        <input type="button" value=" CASETEST MANAGE " ng-click="goPage('casetestManage.do')" style="height:33px; line-height:30px; font-size:12px"/>
    </div>
</div>

 <ion-content  has-header="true" overflow-scroll="true" >

     <!-- main input area -->
     <div class="infoOutBox">
        <table border=1>
	        <tr>
	         <td><span style='font-weight:bold'>User ID :</span></td>
	         <td colspan=4>&nbsp;<input type="text" name="id" id="id" size="40"/></td>
	        </tr>
	        <tr>
	         <td><span style='font-weight:bold; margin-left:10px'>발화 :</span></td>
	         <td>&nbsp;<input type="text" name="reqmsg" id="reqmsg" size="40"/></td>
	         <td><span style='font-weight:bold; margin-left:10px'>event :</span></td>
	         <td><select name="event" id="event" style="height:25px">
	                 <option value=''></option>
	                 <option value='ED:D005E001'>단말 > TV(TV켜짐)</option>
	                 <option value='ED:D100E001'>단말 > 홈폰(홈폰으로 전화옴)</option>
	                 <option value='ES:300'>상황 > 이방인탐지</option>
	                 <option value='ES:301'>상황 > 이상상황탐지 </option>
	                 <option value='ES:000'>상황 > 대화서버 상태확인</option>
	                 <option value='ES:910'>상황 > 대화이력 clear</option>
	                 <option value='ES:900'>상황 > 사용자 인터렉션 없음</option>
	             </select>
	         </td>
	         <td>&nbsp;<input type="button" value="START" class="btnStart" ng-click="sendSingleFormForce()"/></td>
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
                   <td>&nbsp;<input type="text" name="edtFavGenre" id="edtFavGenre" size=45/>
		                   <input type="button" name="edt" id="btnAddGenre" value="+"  ng-click="showFavGenrePOPClick()"/>
		           </td>
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
                   <td><input type="text" name="edtStatus" id="edtStatus" size=45/>
			           <input type="button" name="btnAddStatus" id="btnAddStatus" value="+" ng-click="showStatusPOPClick()"/>
			         </td>
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
                  <input type="text" name="edtFavGenre" id="edtFavGenre" size="40"/>
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
            <table>
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
            <br/>
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
                <br/><br/>
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
            </div> -->
       </div>

       <!-- status -->
       <!-- <div>
         <div class="floatLeftBold">Status</div>
         <div class="floatLeftBold">
           <input type="text" name="edtStatus" id="edtStatus" size=55/>
           <input type="button" name="btnAddStatus" id="btnAddStatus" value="+" ng-click="showStatusPOPClick()"/>
         </div>
       </div>
 -->
       <div class="divider1"></div>
      </div>



      <!-- Result List  ng-repeat="im in testCaseResult" -->
      <div class="resultBox">
         <div ng-repeat="im in testCaseResult">
            <table width=100% border=0 cellspacing=3 cellpadding=0 bgcolor=red>
                <tr>
                    <td colspan=3 class="td_header_dark">
                        &nbsp;&nbsp;<strong>{{ im.result_idx }}. {{im.baseTcDt.exam}} </strong> <br/>
                        <span style='font-size:8px; margin-left:20px'>uword : {{im.p_uword}}</span>
                    </td>
                </tr>
                <tr><td colspan=3 class="tdGrayLine"></td></tr>
                <tr>
                  <td class="td_header_now" colspan=2>Item</td>
                  <td class="td_header">실제 결과</td>
                </tr>
                <tr><td colspan=3 class="tdGrayLine"></td></tr>
                <tr>
                  <td class="td_header_now" colspan=2>Message</td>
                  <td class="td_left">{{im.mesg}}</td>
                </tr>
                <tr><td colspan=3 class="tdGrayLine"></td></tr>
                <tr>
                  <td class="td_header_now" colspan=2>sysAct</td>
                  <td class="td_left">&nbsp;{{im.p_sysAct}}</td>
                </tr>
                <tr><td colspan=3 class="tdGrayLine"></td></tr>
                <tr>
                  <td class="td_header_now" colspan=2>actType</td>
                  <td class="td_left">&nbsp;{{im.actTypeStr}} ({{im.actType}})</td>
                </tr>
                <tr><td colspan=3 class="tdGrayLine"></td></tr>
                <tr>
                  <td class="td_header_now" colspan=2>serviceDomain</td>
                  <td class="td_left">&nbsp;{{im.actGroup_str}} ({{im.actGroup}})</td>
                </tr>
                <tr><td colspan=3 class="tdGrayLine"></td></tr>
                <tr>
                  <td class="td_header_now" colspan=2>serviceId</td>
                  <td class="td_left">&nbsp;{{im.serviceId}}</td>
                </tr>
                <tr><td colspan=3 class="tdGrayLine"></td></tr>
                <tr>
                  <td class="td_header_now" rowspan=7>search</td>
                  <td class="td_white_bold" width=60>&nbsp;srchWord</td>
                  <td class="td_left">&nbsp;{{im.srchWord}}</td>
                </tr>
                <tr><td colspan=3 class="tdGrayLineL"></td></tr>
                <tr>
                  <td class="td_white_bold">&nbsp;sWord</td>
                  <td class="td_left">&nbsp;{{im.sWord}}</td>
                </tr>
                <tr><td colspan=3 class="tdGrayLineL"></td></tr>
                <tr>
                  <td class="td_white_bold">&nbsp;srchOpt</td>
                  <td class="td_left">&nbsp;{{im.srchOpt}}</td>
                </tr>
                <tr><td colspan=3 class="tdGrayLineL"></td></tr>
                <tr>
                  <td class="td_white_bold">&nbsp;srchQry</td>
                  <td class="td_left">&nbsp;{{im.srchQry}}</td>
                </tr>
                <tr><td colspan=3 class="tdGrayLine"></td></tr>
            </table>
        </div>
        <br/>
        <div style="padding:10px">
{{logXML}}
        </div>
      </div>
 </ion-content>
 <br/>
 <br/>
 <br/>

</body>
</html>