ang.controller('casetestCtrl', function($scope, $http, $ionicPopup, $ionicLoading, focusForm) {

	var x2js = new X2JS();

	$scope.serviceDomainList = [
                             	{"service_domain":0, "name":"기본", "checked":true},
                             	{"service_domain":1, "name":"비서", "checked":true},
                             	{"service_domain":2, "name":"OTV", "checked":false},
                             	{"service_domain":3, "name":"전화", "checked":false},
                             	{"service_domain":4, "name":"홈캠", "checked":false},
                             	{"service_domain":5, "name":"홈IOT", "checked":false},
                             	{"service_domain":6, "name":"지니뮤직", "checked":false}
                             ];

	$scope.service_domain_str  = ["기본","비서","OTV","전화","홈캠","홈IOT","지니뮤직"];

	// summary
	$scope.summaryData = {};
	$scope.summaryData.test_count = 0;
	$scope.summaryData.error_count = 0;
	$scope.summaryData.sys_act_match_count    = 0;
	$scope.summaryData.act_type_match_count   = 0;
	$scope.summaryData.service_id_match_count = 0;
	$scope.summaryData.uinfo_match_count      = 0;
	$scope.summaryData.search_match_count     = 0;
	$scope.summaryData.all_match_count        = 0;       // sys_act_match, act_type_match, uinfo_match

	// data list
	$scope.testcaseGroupList = [];		    // group list
	$scope.actTypeList 	= [];
	$scope.categoryList = [];

	// testcaseList & result
	$scope.testCaseList    = [];
	$scope.testCaseResult  = [];

	$scope.logXML = "";

	// ==================================================================================
	// 버튼 클릭
	// ==================================================================================
	$scope.showLoading = function(msg) {
		$ionicLoading.show({
		      template: msg
		});
	};
	$scope.hideLoading = function(){
		$ionicLoading.hide();
	};
    $scope.btnTestClick = function(gid){
    	$scope.testGo(gid);
    };
    $scope.btnEditClick = function(gid){
    	document.location.href = "customtestManage.do?gid="+gid;
    };
    $scope.btnSearchClick = function(){
		$scope.logXML = "";
		$scope.testCaseResult = [];

		$scope.summaryData = {};
		$scope.summaryData.test_count =  0;
		$scope.summaryData.sys_act_match_count = 0;
		$scope.summaryData.act_type_match_count = 0;
		$scope.summaryData.error_count = 0;
		$scope.summaryData.service_id_match_count = 0;
		$scope.summaryData.uinfo_match_count = 0;
		$scope.summaryData.all_match_count = 0;

    	$scope.loadInitData($("input#edtSearch").val());
    };

	$scope.showActionXML = function(uuid, sn){
		window.open(_cmmResultXmlPOP+"?uuid="+uuid+"&sn="+sn, "pop_showxml", makePopupOpt(_cmmXMLW, _cmmXMLH));
	};


	// ==================================================================================
	// Common
	// ==================================================================================
	$scope.findActNameByActType = function(actType){
		var ret = "";
		var cnt = $scope.actTypeList.length;
		for(var i=0; i<cnt; i++){
			var dt = $scope.actTypeList[i];
			if(dt.actType == actType){
				ret = dt.act_type_str;
				break;
			}
		}
		if(ret == ""){
			ret = "undefined";
		}
		
		return ret;
	};

	$scope.findCategoryName = function(catId){
		var ret = "";
		var cnt = $scope.categoryList.length;
		for(var i=0; i<cnt; i++){
			var dt = $scope.categoryList[i];
			if(dt.cat_id == catId){
				ret = dt.nm;
				break;
			}
		}
		return ret;
	};

	// ==================================================================================
	// Load Init Data
	// ==================================================================================
	$scope.loadInitData = function(paramSrch){
		$scope.showLoading("기초 자료를 불러오고 있습니다");

		var srchParam = (isString(paramSrch, "") == "")? "": "?srchWord="+paramSrch;

	    jQuery.ajax({
	    	url: "testcaseGroupListJson.do"+srchParam
	    	,dataType : "json"
	    })
	    .done(function( dt ){
	    	$scope.hideLoading();
			setTimeout(function () {
				$scope.$apply(function () {
					$scope.testcaseGroupList = dt.groupList;
					$scope.actTypeList       = dt.actTypeList;
					$scope.categoryList      = dt.categoryList;
				});
			}, 1000);
	    });
	};
	$scope.loadInitData();

	// ----------------------------------------------------------------------------------
	$scope.testGo = function(gid){
		// testgroup data
		var groupDt = $scope.testcaseGroupList.filter(function(el){ return (el.gid==gid);});
		groupDt = groupDt[0];
		
		var cnt = Number(groupDt.cnt);

		if(cnt == 0){
			alert(" 테스트케이가 존재하지 않습니다 ");
			return;
		}

		var id     = jQuery("#id").val();
		var status = "";
		var event  = "";
		var uinfo  = "";
		var context = "";
		var STB_VER = "";
		var stbVersion = "";
		var product_id = "";
		var appID = "";

		if(groupDt.status != ""){
			status = groupDt.status;
		}

		if(groupDt.event != ""){
			event = groupDt.event;
		}

		if(groupDt.uinfo != ""){
			uinfo = groupDt.uinfo;
		}else{
			uinfo = "ID:111111";
		}

		if(groupDt.context != ""){
			context = groupDt.context;
		}

		if(groupDt.STB_VER != ""){
			STB_VER = groupDt.STB_VER;
		}

		if(groupDt.stbVersion != ""){
			stbVersion = groupDt.stbVersion;
		}

		if(groupDt.product_id != ""){
			product_id = groupDt.product_id;
		}

		if(groupDt.appID != ""){
			appID = groupDt.appID;
		}
		
		$scope.logXML = "";
		$scope.testCaseResult = [];

		$scope.summaryData = {};
		$scope.summaryData.test_count =  cnt;
		$scope.summaryData.sys_act_match_count = 0;
		$scope.summaryData.act_type_match_count = 0;
		$scope.summaryData.error_count = 0;
		$scope.summaryData.service_id_match_count = 0;
		$scope.summaryData.uinfo_match_count = 0;
		$scope.summaryData.all_match_count = 0;

		var uuid = cmmUuid();
		var prms = {"uuid":uuid,
					"id":id,
					"reqmsg":"",
					"status":status,
					"event":event,
					"uinfo":uinfo,
					"context":context,
					"STB_VER":STB_VER,
					"stbVersion":stbVersion,
					"product_id":product_id,
					"appID":appID,
					/*"snList":snList,*/
					"tm":getUnixTimeStamp(),
					"gid":gid} ;


		$scope.showLoading('결과를 기다리고 있습니다. 잠시만 기다려 주세요');
		var urls = "testcaseTest.do";
		$http.get(urls, {params: prms})
		  .success(function(dt) {
			  $scope.hideLoading();
			  if(dt != ""){
				  $scope.testCaseList = dt.testcaseList;
				  $scope.loadLogList(dt.uuid, prms);
			  }
		}).error(function(data) {
			$scope.hideLoading();
		});

	};
	// --------------------------------------------------------------------- load from db log
	$scope.loadLogList = function(uuid, prms){
		var urls = "logListByUuid.do?uuid="+uuid;
		
		$http.get(urls)
		  .success(function(dt) {
			  var len = dt.length;
			  
			  for(var i=0; i<len; i++){
				  $scope.searchResultProc(i, prms, dt[i], uuid);
			  }
		}).error(function(data) {

		});
	};
	// ---------------------------------------------------------------------
	$scope.findBaseDataBySn = function(sn){
		var c = $scope.testCaseList.length;
		for(var i=0; i<c; i++){
			if($scope.testCaseList[i].sn == sn){
				return $scope.testCaseList[i];
				break;
			}
		}
	};
	// ---------------------------------------------------------------------
	$scope.searchResultProc = function(idx, inParams, dt, uuid){
		var tcDt = $scope.findBaseDataBySn(dt.sn);

		var erK  = 0;
		tcDt.actTypeStr =  $scope.findActNameByActType(tcDt.ans_actType);
		tcDt.catNm =  $scope.findCategoryName(tcDt.cat_id);

		/*
		var jStr = xml2json(dt.xml,"");
		jStr = jStr.replace(/#cdata/gi, "cdata");
		var utter = JSON.parse(jStr);
		*/

		var utter;
		try {
			utter = x2js.xml_str2json(dt.xml);
		}catch(err) {
			erK++;
			$scope.summaryData.error_count++;
		}


		if(erK > 0){
			console.log("query error");
		}else{
			if(utter != undefined){
				$scope.testCaseList[idx].is_check = "1";
				$scope.testCaseList[idx].xml = dt;

				var retObj   = utter.dialogframe.response.utter;
				var uword    = retObj.uword;
				var sysAct   = retObj.sysAct;

				var action = retObj.action;
				var actionLen = 0;
				if(action != undefined){
					var actionStr = JSON.stringify(action);
					var prefix = actionStr.substring(0,1);
					if(prefix=="["){
						actionLen = action.length;
						for(var j = 0; j<actionLen; j++){
							var ok_search        = false;
							var ok_sysAct        = false;
							var ok_actType       = false;
							var ok_serviceDomain = false;
							var ok_Pattern       = false;

							var actInArr = action[j];
							actInArr.sn = dt.sn;
							actInArr.i = idx;
							actInArr.result_idx  = (j+1)+"/"+actionLen;
							actInArr.uuid = uuid;
							actInArr.p_sysAct = sysAct;
							actInArr.p_uword  = uword;
							actInArr.baseTcDt = tcDt;
							actInArr.xml = dt;

							// strip cdata
							actInArr.mesg      = cmmGetXMLValue(actInArr.mesg);
							actInArr.serviceId = cmmGetXMLValue(actInArr.serviceId);

							// actTypeString
							actInArr.actTypeStr =	$scope.findActNameByActType(actInArr.actType);
							actInArr.ori_service_domain_str = $scope.service_domain_str[tcDt.service_domain];

							// searchWord check
							var searchWordChkResult = cmmSearchWordValidityCheck(tcDt, actInArr);
							if(searchWordChkResult == "0"){
								$scope.summaryData.search_match_count++;
								ok_search = true;
							}
							actInArr.search_check_result = searchWordChkResult;

							// sysAct, actType match
							if(actInArr.p_sysAct  == tcDt.ans_sysAct){
								$scope.summaryData.sys_act_match_count++;
								ok_sysAct = true;
							}
							if(actInArr.actType == tcDt.ans_actType){
								$scope.summaryData.act_type_match_count++;
								ok_actType = true;
							}

							// serviceId validity check
							var svcPattern = cmmServiceIdValidityCheck(tcDt, actInArr);
							if(svcPattern == "Good"){
								$scope.summaryData.service_id_match_count++;
								ok_Pattern = true;
							}
							actInArr.service_id_check_result = svcPattern;

							// uinfo check
							//var uInfoMatch = $scope.chkUInfoPerm(tcDt.service_domain);
							//actInArr.service_domain_match = uInfoMatch;

							// actGroup check
							actInArr.actGroup_str = $scope.service_domain_str[actInArr.actGroup];
							var actGroupMatch = cmmChkActGroupPerm(inParams, actInArr.actGroup);
							actInArr.actGroup_match = actGroupMatch;
							if(actGroupMatch == "1"){
								$scope.summaryData.uinfo_match_count++;
								ok_serviceDomain = true;
							}

							// all correct count
							if(ok_search==true && ok_sysAct==true && ok_actType==true && ok_serviceDomain==true && ok_Pattern==true){
								actInArr.is_all_match = "1";
								$scope.summaryData.all_match_count++;
							}else{
								actInArr.is_all_match = "0";
							}
							// add action result
							$scope.testCaseResult = $scope.testCaseResult.concat(actInArr);
						}

					}else if(prefix=="{"){
						var ok_search        = false;
						var ok_sysAct        = false;
						var ok_actType       = false;
						var ok_serviceDomain = false;
						var ok_Pattern       = false;

						var actInArr = action;
						actInArr.sn = dt.sn;
						actInArr.i = idx;
						actInArr.result_idx  = "1/1";
						actInArr.uuid = uuid;
						actInArr.p_sysAct = sysAct;
						actInArr.p_uword  = uword;
						actInArr.baseTcDt = tcDt;
						actInArr.xml = dt;

						// strip cdata
						/*
						var tmpSvcId = "";
						if(actInArr.serviceId==null || actInArr.serviceId==undefined || actInArr.serviceId==''){
							tmpSvcId = "";
						}else{
							if(actInArr.serviceId.hasOwnProperty("cdata")){
								tmpSvcId = actInArr.serviceId.cdata;
							}else{
								tmpSvcId = actInArr.serviceId;
							}
						}
						actInArr.serviceId = tmpSvcId;
						*/
						actInArr.mesg      = cmmGetXMLValue(actInArr.mesg);
						actInArr.serviceId = cmmGetXMLValue(actInArr.serviceId);

						// actTypeString
						actInArr.actTypeStr = $scope.findActNameByActType(actInArr.actType);
						actInArr.ori_service_domain_str = $scope.service_domain_str[tcDt.service_domain];

						// searchWord check
						var searchWordChkResult = cmmSearchWordValidityCheck(tcDt, actInArr);
						if(searchWordChkResult == "0"){
							$scope.summaryData.search_match_count++;
							ok_search = true;
						}
						actInArr.search_check_result = searchWordChkResult;

						// sysAct, actType match
						if(actInArr.p_sysAct  == tcDt.ans_sysAct){
							$scope.summaryData.sys_act_match_count++;
							ok_sysAct = true;
						}
						if(actInArr.actType == tcDt.ans_actType){
							$scope.summaryData.act_type_match_count++;
							ok_actType = true;
						}

						// result validity check
						var svcPattern = cmmServiceIdValidityCheck(tcDt, actInArr);
						if(svcPattern == "Good"){
							$scope.summaryData.service_id_match_count++;
							ok_Pattern = true;
						}
						actInArr.service_id_check_result = svcPattern;

						// uinfo check
						//var uInfoMatch = $scope.chkUInfoPerm(tcDt.service_domain);
						//actInArr.service_domain_match = uInfoMatch;

						// actGroup check
						actInArr.actGroup_str = $scope.service_domain_str[actInArr.actGroup];
						var actGroupMatch = cmmChkActGroupPerm(inParams, actInArr.actGroup);
						actInArr.actGroup_match = actGroupMatch;
						if(actGroupMatch == "1"){
							$scope.summaryData.uinfo_match_count++;
							ok_serviceDomain = true;
						}

						// all correct count
						if(ok_search==true && ok_sysAct==true && ok_actType==true && ok_serviceDomain==true && ok_Pattern==true){
							actInArr.is_all_match = "1";
							$scope.summaryData.all_match_count++;
						}else{
							actInArr.is_all_match = "0";
						}

						// add action result
						$scope.testCaseResult = $scope.testCaseResult.concat(actInArr);
					}
				}

				$scope.testCaseList[idx].action_len = actionLen;
			}
		}
	};
});

function btnSearchClick(){
	angular.element(document.getElementById('casetest')).scope().btnSearchClick();
}