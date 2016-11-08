<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");
        response.setHeader("pragma","no-cache");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta name="viewport" content="width=1024"/>
    
    <title>kt 지능형감성대화서버 Service Test</title>
    
    <link rel="stylesheet" href="assets/lib/ionic/css/ionic.css" type="text/css" />

    <script type="text/javascript" src="assets/js/jQuery.js"></script>
    <script type="text/javascript" src="assets/lib/ionic/js/ionic.bundle.js"></script>
    <script type="text/javascript" src="js/ng-grid.min.js" data-semver="2.0.11" data-require="ng-grid@*"></script>
    <script src="js/xml2json2.js"></script>
    <script src="js/ang_common.js"></script>
    <script src="js/singletest.js"></script>
</head>

<body data-page="home" id="casetest" ng-app="testcase" ng-controller="casetestCtrl" style="overflow:auto;">

<jsp:include page="header.jsp" flush="false"/>
<script>
    document.getElementById("liSt").className ="on";
</script>

<!-- LNB -->
<!-- /LNB -->

<!-- Contents -->
<div id="bodyWrapper" style="margin-left:20px;">

	<!-- search area -->
	<div style="height:40px;">
	        발화  : 
	    <input type="text" name="reqmsg" id="reqmsg" size="35" style="vertical-align:middle;"/>
		&nbsp;&nbsp;&nbsp;event : 
		<select name="event" id="event" style="width:270px;vertical-align:middle;">
		     <option value=''></option>
		     <option value='ED:D005E001'>단말 &gt; TV(TV켜짐)</option>
		     <option value='ED:D100E001'>단말 &gt; 홈폰(홈폰으로 전화옴)</option>
		     <option value='ES:300'>상황 &gt; 이방인탐지</option>
		     <option value='ES:301'>상황 &gt; 이상상황탐지 </option>
		     <option value='ES:000'>상황 &gt; 대화서버 상태확인</option>
		     <option value='ES:910'>상황 &gt; 대화이력 clear</option>
		     <option value='ES:900'>상황 &gt; 사용자 인터렉션 없음</option>
		</select>
		&nbsp;&nbsp;&nbsp;<button class="btn btn-primary" ng-click="sendSingleFormForce()"><span class="icon-play-alt"/>START</button>
	</div>
    <div style="clear:both"></div>
    <div class="wiget-gbx" style="float:left;width:530px;">
        <h3>user info</h3>
        <div class="inner">
            <table class="wiget-table tbl_col">
                <colgroup>
                    <col width="80"/>
                    <col width="*"/>
                </colgroup>
                <tbody>
                    <tr>
                        <th>User ID</th>
                        <td><input type="text" name="id" id="id" size="10" value="${authVO.login_id}" readonly/></td>
                    </tr>
                    <tr height="39">
                        <th>가입서비스</th>
                        <td><input type="checkbox" name="chk_uinfo1" id="chk_uinfo1" ng-model="serviceDomainList[1].checked"/>&nbsp;비서
                                    &nbsp;<input type="checkbox" name="chk_uinfo2" id="chk_uinfo2" ng-model="serviceDomainList[2].checked"/>OTV
                                    &nbsp;<input type="checkbox" name="chk_uinfo3" id="chk_uinfo3" ng-model="serviceDomainList[3].checked"/>전화
                                    &nbsp;<input type="checkbox" name="chk_uinfo4" id="chk_uinfo4" ng-model="serviceDomainList[4].checked"/>홈캠
                                    &nbsp;<input type="checkbox" name="chk_uinfo5" id="chk_uinfo5" ng-model="serviceDomainList[5].checked"/>홈IOT
                                    &nbsp;<input type="checkbox" name="chk_uinfo6" id="chk_uinfo6" ng-model="serviceDomainList[6].checked"/>지니뮤직</td>
                    </tr>
                    <tr>
                        <th>선호장르</th>
                        <td><input type="text" name="edtFavGenre" id="edtFavGenre" size="45"/>
                            <button class="btn btn-small" ng-click="showFavGenrePOPClick()">+</button>
                        </td>
                    </tr>
    
                    <tr>
                       <th>추천선호도</th>
                       <td><select name="selFavPoint" id="selFavPoint">
                                    <option value=""></option>
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                  </select></td>
                    </tr>
                    <tr>
                       <th>Status</th>
                       <td><input type="text" name="edtStatus" id="edtStatus" size="45"/>
                           <button class="btn btn-small" ng-click="showStatusPOPClick()">+</button>
                       </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="wiget-gbx" style="margin-left:538px;min-width: 530px;">
        <h3>device info</h3>
        <div class="inner">
            <table class="wiget-table tbl_col">
                <colgroup>
                    <col width="100"/>
                    <col width="*"/>
                </colgroup>
                <tbody>
                    <tr>
                        <th>세탑단말버전(STB Ver)</th>
                        <td><select name="STB_VER" id="STB_VER">
                                <option value="0.4">0.4</option>
                                <option value="0.6">0.6</option>
                              </select>
                        </td>
                    </tr>
                    <tr>
                        <th>클라이언트 단말버전(stbVersion)</th>
                        <td><select name="stbVersion" id="stbVersion">
                                <option value="0.6">0.6</option>
                                <option value="0.4">0.4</option>
                              </select></td>
                    </tr>
                    <tr>
                       <th>devServiceID</th>
                       <td>&nbsp;<input type="text" name="devService_id" id="devService_id" /></td>
                    </tr>
                    <tr>
                       <th>Product ID</th>
                       <td>&nbsp;<input type="text" name="product_id" id="product_id" /></td>
                    </tr>
                    <tr>
                       <th>appID</th>
                       <td>&nbsp;<input type="text" name="appID" id="appID" /></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <!-- search area -->
    
    <!-- result area -->
    <div style="clear:both;">
	    <div class="wiget-gbx" ng-repeat="im in testCaseResult">
		    <h3><strong>{{ im.result_idx }}. {{im.baseTcDt.exam}} </strong> <br/>
		        <span style='font-size:8px; margin-left:20px'>uword : {{im.p_uword}}</span>
		    </h3>
		    <div class="inner">
		        <table class="wiget-table tbl_col">
		            <colgroup>
		                <col width="60"/>
		                <col width="90"/>
		                <col width="*"/>
		            </colgroup>
		            <tbody> 
		                <tr>
		                  <th style="text-align: center;" colspan=2>Item</th>
		                  <th>실제 결과</th>
		                </tr>
		                <tr>
		                  <th style="text-align: center;" colspan=2>Message</th>
		                  <td>{{im.mesg}}</td>
		                </tr>
		                <tr>
		                  <th colspan=2>sysAct</th>
		                  <td>{{im.p_sysAct}}</td>
		                </tr>
		                <tr>
		                  <th colspan=2>actType</th>
		                  <td>{{im.actTypeStr}} ({{im.actType}})</td>
		                </tr>
		                <tr>
		                  <th colspan=2>serviceDomain</th>
		                  <td>{{im.actGroup_str}} ({{im.actGroup}})</td>
		                </tr>
		                <tr>
		                  <th colspan=2>serviceId</th>
		                  <td>{{im.serviceId}}</td>
		                </tr>
		                <tr>
		                  <th rowspan=4>search</th>
		                  <th>&nbsp;srchWord</th>
		                  <td>{{im.srchWord}}</td>
		                </tr>
		                <tr>
		                  <th class="td_white_bold">&nbsp;sWord</th>
		                  <td>{{im.sWord}}</td>
		                </tr>
		                <tr>
		                  <th class="td_white_bold">&nbsp;srchOpt</th>
		                  <td>{{im.srchOpt}}</td>
		                </tr>
		                <tr>
		                  <th class="td_white_bold">&nbsp;srchQry</th>
		                  <td>{{im.srchQry}}</td>
		                </tr>
		                <tr>
		                  <td colspan=3>{{logXML}}</td>
		                </tr>
		            </tbody>
		        </table>
		    </div>
		</div>
	</div>
	<!-- result area -->
</div>
<!-- /Contents -->




  <!-- Result List  ng-repeat="im in testCaseResult" -->


</body>
</html>