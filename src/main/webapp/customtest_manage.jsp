<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");
        response.setHeader("pragma","no-cache");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="kr">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta name="viewport" content="width=1024"/>
    
    <title>kt 지능형감성대화서버 Service Test</title>
    
    <link rel="stylesheet" href="assets/lib/ionic/css/ionic.css" type="text/css" />    
    <style>
        #leftCatetorySel{
            float:left;
            width:200px;
        }
        #leftCatetorySel td{
            padding:5px;   
            cursor: pointer;
        }
    </style>
    
    <script type="text/javascript" src="assets/js/jQuery.js"></script>
    <script type="text/javascript" src="assets/lib/ionic/js/ionic.bundle.js"></script>
    <script type="text/javascript" src="js/ng-grid.min.js" data-semver="2.0.11" data-require="ng-grid@*"></script>
    <script src="js/json2.js"></script>
    <script src="js/xml2json2.js"></script>
    <script src="js/common.js"></script>
    <script src="js/ang_common.js"></script>
    <script src="js/customtest_manage.js"></script>
</head>

<body data-page="home" id="customtestManage" ng-app="testcase" ng-controller="customtestManageCtrl" style="overflow:auto;">

<jsp:include page="header.jsp" flush="false"/>
<script>
    document.getElementById("liCst").className ="on";
    var _gid = "${gid}";
</script>

<!-- LNB -->
<!-- /LNB -->

<!-- Contents -->
<div id="bodyWrapper" style="margin-left:20px;">
    <!-- search area -->
    <div style="height:35px;">케이스그룹명 : <input type="text" name="group_nm" id="group_nm" ng-model="header.group_nm" size="40"/>
        &nbsp;&nbsp;&nbsp;<button class="btn btn-primary btn-small" ng-click="btnSaveClick()">SAVE</button>
    </div>
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
    <div style="clear:both"></div>
    
    <!--  category dropdown -->
    <div id="leftCatetorySel">
        <div class="wiget-gbx">
	        <table class="tbl_row">
	            <tbody>
	                <tr>
	                   <td onclick="selSubCatList('')">전체</td>
	                </tr>
	                <tr ng-repeat="im in upperCategoryList" ng-click="selSubCatList('{{im.gid}}')">
	                   <td>{{im.pnm}}</td>
	                </tr>
	            </tbody>
	        </table>
        </div>
        <div class="wiget-gbx" style="height:250px;overflow:auto;">
	        <table class="tbl_row">
	           <tbody>
	               <tr ng-repeat="im in categoryList" ng-click="selTestCaseList('{{im.cat_id}}')">
	                  <td>{{im.nm}}({{im.tc_cnt}})</td>
	               </tr>
	           </tbody>
	       </table>
       </div>
    </div>
    <!--  category dropdown -->
    
    <!-- grid area -->
    <div class="wiget-gbx" style="margin-left:220px;height:600px;overflow:auto;">
        <div class="inner"  >
            <table class="wiget-table tbl_row">
                <thead>
                    <tr>
                      <th width="40"></th>
                      <th width="50">sid</th>
                      <th width="100">구분</th>
                      <th width="100">서비스</th>
                      <th>발화</th>
                      <th width="100">최초등록자</th>
                      <th width="100">등록일</th>
                      <th width="50">actType</th>
                    </tr>
                </thead>
                <tbody>
                    <tr ng-repeat="im in testcaseGroupList">
                       <td><input type="checkbox" name="chk" ng-model="im.chk"/></td>
                       <td>{{im.sn}}</td>
                       <td>{{im.pnm}}</td>
                       <td>{{im.nm}}</td>
                       <td>{{im.exam}}</td>
                       <td>{{im.regist_id}}</td>
                       <td>{{im.regist_dt}}</td>
                       <td>{{im.ans_actType}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <!-- grid area -->
</div>
<!-- Contents -->


</body>
</html>