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
    <script type="text/javascript" src="assets/ionic/material-design/ionic.material.min.js"></script>
    <script type="text/javascript" src="js/ng-grid.min.js" data-semver="2.0.11" data-require="ng-grid@*"></script>
    <script src="js/xml2json.js"></script>
    <script src="js/ang_common.js"></script>
    <script src="js/customtest.js"></script>
</head>

<body data-page="home" id="casetest" ng-app="testcase" ng-controller="casetestCtrl" style="overflow:auto;">

<jsp:include page="header.jsp" flush="false"/>
<script>
     document.getElementById("liCst").className ="on";
</script>


<!-- LNB -->
<!-- /LNB -->

<!-- Contents -->
<div id="bodyWrapper" style="margin-left:20px;">
    
    <!-- grid area -->
    <div class="wiget-gbx">
        <h3 style="line-height: 27px;">
            <strong>&nbsp;&nbsp;검색된 결과 : {{testcaseGroupList.length}}건 </strong>
            <span style="float:right;padding-right:10px;">
                <input type="text" name="edtSearch" id="edtSearch" size="35"  onkeypress="if(event.keyCode==13) { btnSearchClick(); return false;}"/>
		        <input type="hidden" id="id" value="${authVO.login_id}"/>
		        &nbsp;<button class="btn btn-blue btn-small" ng-click="btnSearchClick()">SEARCH</button>
		        &nbsp;<button class="btn btn-teal btn-small" ng-click="btnEditClick('')"><span class="icon-plus"/>ADD</button>
            </span>
        </h3>
        <div class="inner" style="height:316px;overflow:auto;">
            <table class="wiget-table tbl_row">
                <thead>
                    <tr>
                      <th>케이스그룹</th>
			          <th>Cnt</th>
			          <th>등록자</th>
			          <th>등록일</th>
			          <th>기능</th>
			        </tr>
			    </thead>
			    <tbody>
			        <tr ng-repeat="im in testcaseGroupList">
			           <td>{{im.group_nm}}</td>
			           <td>{{im.cnt}}</td>
			           <td>{{im.login_nm}}</td>
			           <td>{{im.rgsde}}</td>
			           <td style="padding:7px;"><button class="btn btn-success btn-small" ng-click="btnTestClick(im.gid)"> TEST </button>
			               <button class="btn btn-success btn-small" ng-click="btnEditClick(im.gid)"> EDIT </button>
			           </td>
			        </tr>
                </tbody>
            </table>
        </div>
    </div>
    <!-- grid area -->
    
    <!-- summary area -->
    <div class="wiget-gbx" ng-show="testCaseResult.length > 0">
        <h3 style="margin-bottom:0px;">SUMMARY</h3>
        <div class="inner">
            <table class="wiget-table smmrTable">
                <tbody>
                    <tr>
                        <td>Test Case Cnt.</td>
		                <td>Action Cnt.</td>
		                <td>Result</td>
		                <td>ActType</td>
		                <td>Service Domain</td>
		                <td>ServiceID</td>
                    </tr>
                    
		            <tr>
		                <td rowspan="2">{{summaryData.test_count}}</td>
		                <td rowspan="2">{{testCaseResult.length}}</td>
		                <td rowspan="2">{{summaryData.all_match_count}}</td>
		                <td class="corBlue">{{summaryData.act_type_match_count}}</td>
		                <td class="corBlue">{{summaryData.uinfo_match_count}}</td>
		                <td class="corBlue">{{summaryData.service_id_match_count}}</td>
		            </tr>
		            <tr>
		                <td class="corRed">{{testCaseResult.length - summaryData.act_type_match_count}}</td>
		                <td class="corRed">{{testCaseResult.length - summaryData.uinfo_match_count}}</td>
		                <td class="corRed">{{testCaseResult.length - summaryData.service_id_match_count}}</td>
		            </tr>
                </tbody>
            </table>
        </div>
    </div>
    <!-- summary area -->
                    
    <!-- result area -->
    <div class="wiget-gbx" ng-repeat="im in testCaseResult">
        <h3><button class="btn btn-small" ng-click="showActionXML(im.uuid, im.sn)">XML</button>
            &nbsp;<strong>{{im.i}}({{ im.result_idx }}). {{im.baseTcDt.catNm}}->{{im.baseTcDt.exam}} </strong>
            &nbsp;<span style='font-size:8px; margin-left:20px'>uword : {{im.p_uword}}</span>
        </h3>
        <div class="inner">
            <table class="wiget-table tbl_col">
                <colgroup>
                    <col width="60"/>
                    <col width="90"/>
                    <col width="220"/>
                    <col width="*"/>
                    <col width="50"/>
                </colgroup>
                <tbody>
                 <tr>
                   <th style="text-align: center;" colspan=2>Item</th>
                   <th style="text-align: center;">예상 결과(정상)</th>
                   <th style="text-align: center;">실제 결과</th>
                   <th style="text-align: center;">결과</th>
                 </tr>
                 <tr>
                   <th style="text-align: center;" colspan=2>Message</th>
                   <td></td>
                   <td>{{im.mesg}}</td>
                   <td></td>
                 </tr>
                 <tr>
                   <th style="text-align: center;" colspan=2>sysAct</th>
                   <td>{{im.baseTcDt.ans_sysAct}}</td>
                   <td>{{im.p_sysAct}}</td>
                   <td>
                     <span class='result corBlue' ng-show=" im.baseTcDt.ans_sysAct == im.p_sysAct ">OK</span>
                     <span class='result corRed' ng-show=" im.baseTcDt.ans_sysAct != im.p_sysAct ">NOK</span>
                   </td>
                 </tr>
                 <tr>
                   <th style="text-align: center;" colspan=2>actType</th>
                   <td>{{im.baseTcDt.actTypeStr}} ({{im.baseTcDt.ans_actType}})</td>
                   <td>{{im.actTypeStr}} ({{im.actType}})</td>
                   <td>
                     <span class='result corBlue' ng-show=" im.baseTcDt.ans_actType == im.actType ">OK</span>
                     <span class='result corRed' ng-show=" im.baseTcDt.ans_actType != im.actType ">NOK</span>
                   </td>
                 </tr>
                 <tr>
                   <th style="text-align: center;" colspan=2>serviceDomain</th>
                   <td>{{im.ori_service_domain_str}}</td>
                   <td>{{im.actGroup_str}} ({{im.actGroup}})</td>
                   <td>
                     <span class='result corBlue' ng-show=" im.actGroup_match == '1' ">OK</span>
                     <span class='result corRed' ng-show=" im.actGroup_match != '1' ">NOK</span>
                   </td>
                 </tr>
                 <tr>
                   <th style="text-align: center;" colspan=2>serviceId</th>
                   <td><span ng-show="im.baseTcDt.chk_method == 'equal'"><strong>&nbsp;{{im.baseTcDt.service_id_str}}</strong></span>
                       <span ng-show="im.baseTcDt.chk_method != 'equal'" style='color:blue'><strong>&nbsp;{{im.baseTcDt.service_id_tmpl}}</strong></span>
                  </td>
                  <td>
                     <div style="word-wrap:break-word;">{{im.serviceId}}</div>
                   </td>
                   <td>
                     <span class='result corBlue' ng-show=" im.service_id_check_result == 'Good' ">OK</span>
                     <span class='result corRed' ng-show=" im.service_id_check_result != 'Good' ">NOK</span>
                   </td>
                 </tr>
                 <tr>
                   <th rowspan=4>search</th>
                   <th>srchWord</th>
                   <td>{{im.baseTcDt.ans_srchword}}</td>
                   <td>{{im.srchWord}}</td>
                   <td></td>
                 </tr>
                 <tr>
                   <th>sWord</th>
                   <td>{{im.baseTcDt.ans_sword}}</td>
                   <td>{{im.sWord}}</td>
                   <td></td>
                 </tr>
                 <tr>
                   <th>srchOpt</th>
                   <td>{{im.baseTcDt.ans_srchopt}}</td>
                   <td>{{im.srchOpt}}</td>
                   <td></td>
                 </tr>
                 <tr>
                   <th>srchQry</th>
                   <td>{{im.baseTcDt.ans_srchqry}}</td>
                   <td>{{im.srchQry}}</td>
                   <td></td>
                 </tr>
                </tbody>
            </table>
        </div>
    </div>
    <!-- result area -->
</div>

      
</body>
</html>