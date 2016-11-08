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
        .menuLeft0{
            margin-left:0px;
        }
        .menuLeft20{
            margin-left:20px;
        }
    </style>
    <script type="text/javascript" src="assets/js/jQuery.js"></script>
    <script type="text/javascript" src="assets/lib/ionic/js/ionic.bundle.js"></script>
    <script type="text/javascript" src="js/ng-grid.min.js" data-semver="2.0.11" data-require="ng-grid@*"></script>
    <script src="js/xml2json.js"></script>
    <script src="js/ang_common.js"></script>
    <script src="js/casetest.js"></script>
</head>

<body data-page="home" id="casetest" ng-app="testcase" ng-controller="casetestCtrl" style="overflow:auto;">
<jsp:include page="header.jsp" flush="false"/>
<script>
    document.getElementById("liCt").className ="on";
</script>

<hr/>

<!-- LNB -->
<div id="lnbWrapper">
    <div class="lnb">
        <h3 style="text-align:left;padding-left:10px;"><input type="checkbox" name='chk_leftall' id='chk_leftall' ng-click="leftMenuSelAll()"/>전체선택</h3>
        <div style="padding:10px;line-height:25px;">
         <ul ng-repeat="im in leftmenu">
             <li ng-class="{menuLeft0: im.dept == 0, menuLeft20: im.dept > 0}">
                 <input type="checkbox" name='chk' ng-model="im.checked" ng-click="leftMenuCLick(im)"/>
                 <span ng-class="{txtBold:im.dept == 0, txtNormal:im.dept > 0}">{{im.nm}}</span><span ng-show="im.dept > 0">({{im.tc_cnt}})</span> 
             </li>
         </ul>
        </div>
    </div>
</div>
<!-- /LNB -->

<!-- Contents -->
<div id="bodyWrapper">
    <!-- <h2><span class="icon-folder-stroke"></span>팬혜택 이벤트</h2> -->

    <!-- search area -->
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
                 <td><input type="text" name="devService_id" id="devService_id" /></td>
              </tr>
              <tr>
                 <th>Product ID</th>
                 <td><input type="text" name="product_id" id="product_id" /></td>
              </tr>
              <tr>
                 <th>appID</th>
                 <td><input type="text" name="appID" id="appID" /></td>
              </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="wiget-cfm txa_c mar_b20" style="clear:both;">
        <button class="btn btn-primary" ng-click="testGo()"><span class="icon-play-alt"/>START</button>
    </div>
    <!-- search area -->

    <!-- summary area -->
    <div class="wiget-gbx" ng-show="testCaseResult.length > 0">
        <h3 style="margin-bottom:0px;">SUMMARY</h3>
        <div class="inner">
            <table class="wiget-table smmrTable">
                <thead>
                </thead>
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
                        <td rowspan=2>{{summaryData.test_count}}</td>
                        <td rowspan=2>{{testCaseResult.length}}</td>
                        <td rowspan=2>{{summaryData.all_match_count}}</td>
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
            &nbsp;{{im.i}}({{ im.result_idx }}). {{im.baseTcDt.catNm}}->{{im.baseTcDt.exam}}
            &nbsp;<span style='font-size:8px; margin-left:30px;'>uword : {{im.p_uword}}</span>
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
<!-- /Contents -->

</body>
</html>