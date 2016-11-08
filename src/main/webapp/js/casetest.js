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

	// Header Vars
	$scope.header = {};
	//	$scope.header.id            = _LOGIN_ID;
	$scope.header.uinfo         = "";    // 가입서비스
	$scope.header.fav_genre     = "";    // 선호장르
	$scope.header.fav_point     = "";    // 추천선호도
	$scope.header.status        = "";
	$scope.header.STB_VER       = "";
	$scope.header.stbVersion    = "";
	$scope.header.devService_id = "";
	$scope.header.product_id    = "";
	$scope.header.appID         = "";
	$scope.header.context       = "";

	// left menu data
	$scope.leftmenu = [];

	// data list
	$scope.headerList 	= [];				// header list
	$scope.actTypeList 	= [];            // act type list

	// testcaseList & result
	$scope.testCaseListAll = [];
	$scope.testCaseList    = [];
	$scope.testCaseResult  = [];
	$scope.testCaseResultSingle = [];

	$scope.logXML = "";
	
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

	// -----------------------------------------------------------------------------------
	// 입력한 헤더 정보들을 만든다.
	// -----------------------------------------------------------------------------------
	$scope.setUinfo = function(){
		var tmp = "";
		for(var i=1; i<7; i++){
			if($scope.serviceDomainList[i].checked){
				tmp += "1";
			}else{
				tmp += "0";
			}
		}
		var favGenre = jQuery("#edtFavGenre").val();
		if(favGenre != ''){
			tmp = favGenre+","+"ID:"+tmp;
		}else{
			tmp = "ID:"+tmp;
		}

		var favPoint = jQuery("#selFavPoint option:selected").val();
		if(favPoint != ''){
			tmp = tmp+",PCS:"+favPoint;
		}

		$scope.header.uinfo = tmp;
	};
	$scope.setSearchHeader = function(){
		$scope.setUinfo();

		$scope.header.id         = jQuery("#id").val();
		$scope.header.fav_point  = jQuery("#selFavPoint").val();
		$scope.header.status     = jQuery("#edtStatus").val();
		$scope.header.STB_VER    = jQuery("#STB_VER").val();
		$scope.header.stbVersion = jQuery("#stbVersion").val();
		$scope.header.devService_id = jQuery("#devService_id").val();
		$scope.header.product_id = jQuery("#product_id").val();
		$scope.header.appID      = jQuery("#appID").val();
		return;
	};


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
	
	// 선호장르 보여주기 버튼 클릭
	$scope.showFavGenrePOPClick = function(){
		window.open(_cmmFavGenrePOP, "pop_fav_genre", makePopupOpt(_cmmPopFavGenreW, _cmmPopFavGenreH));
	};

	// 상태 보여주기 버튼 클릭
	$scope.showStatusPOPClick = function(){
		window.open(_cmmStatusPOP, "pop_status", makePopupOpt(_cmmPopStatusW, _cmmPopStatusH));

	};
	
	$scope.showActionXML = function(uuid, sn){
		window.open(_cmmResultXmlPOP+"?uuid="+uuid+"&sn="+sn, "pop_showxml", makePopupOpt(_cmmXMLW, _cmmXMLH));
	};

	// 레프트메뉴 전체선택
	$scope.leftMenuSelAll = function(){
		var cnt = $scope.leftmenu.length;
		var ck = jQuery("#chk_leftall").prop('checked');
		console.log("ck="+ck);
		for(var i=0; i<cnt; i++){
			$scope.leftmenu[i].checked = ck;
		}
	};

	// 레프트메뉴 한개 선택
	$scope.leftMenuCLick = function(lm){
		if(lm.dept == 0){
			var cnt = $scope.leftmenu.length;
			for(var i=0; i<cnt; i++){
				if( ($scope.leftmenu[i].gid == lm.gid) && ($scope.leftmenu[i].dept > 0) ){
					$scope.leftmenu[i].checked = lm.checked;
				}
			}
		}
	};

	// ==================================================================================
	// 외부호출
	// ==================================================================================
	$scope.setHeaderStatus = function(arg){
		$scope.header.status = arg;
		jQuery("#edtStatus").val(arg);
	};
	$scope.setHeaderFavGenre = function(arg){
		$scope.header.fav_genre = arg;
		jQuery("#edtFavGenre").val(arg);
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
		var cnt = $scope.leftmenu.length;
		for(var i=0; i<cnt; i++){
			var dt = $scope.leftmenu[i];
			if(dt.cat_id == catId){
				ret = dt.nm;
				break;
			}
		}
		return ret;
	};

	$scope.makeTestCase = function(){
		$scope.testCaseList = [];
		var cnt = $scope.leftmenu.length;
		var ck  = $scope.testCaseListAll.length;

		for(var i=0; i<cnt; i++){
			if( $scope.leftmenu[i].checked == true){
				//console.log($scope.leftmenu[i].cat_id);
				for(var p=0; p<ck; p++){
					if( $scope.testCaseListAll[p].cat_id == $scope.leftmenu[i].cat_id){
						$scope.testCaseList = $scope.testCaseList.concat($scope.testCaseListAll[p]);
					}
				}

			}
		}

		$scope.summaryData.test_count = $scope.testCaseList.length;
		console.log($scope.testCaseList.length);
	};

	// ==================================================================================
	// Load Init Data
	// ==================================================================================
	$scope.loadInitData = function(){
		$scope.showLoading("기초 자료를 불러오고 있습니다");
	    jQuery.ajax({
	    	url: "testcaseBaseData.do"
	    	,dataType : "json"
	    })
	    .done(function( dt ) {
	    		$scope.hideLoading();
				setTimeout(function () {
					$scope.$apply(function () {
						$scope.actTypeList       = dt.actTypeList;
						$scope.testCaseListAll   = dt.testcaseList;
						$scope.leftmenu          = dt.testcaseCategoryList;
					});
				}, 1000);
	    });
	};
	$scope.loadInitData();


	// ----------------------------------------------------------------------------------
	// 동기모드로 동작한다. jQuery 1.8이하를 사용해야한다.
	$scope.testGo = function(){
		$scope.setSearchHeader();
		$scope.makeTestCase();

		if($scope.header.id == null ||  $scope.header.id == undefined ||  $scope.header.id == ""){
			focusForm('id');
			alert(' id 를 입력해 주세요 ');
			return;
		}

		if($scope.header.uinfo == null ||  $scope.header.uinfo == undefined || $scope.header.uinfo == ""){
			alert(' 가입서비스를 선택해 주세요 ');
			return;
		}


		var cnt = $scope.testCaseList.length;

		$scope.logXML = "";
		$scope.testCaseResult = [];
		$scope.testCaseResultSingle = [];

		$scope.summaryData = {};
		$scope.summaryData.test_count =  cnt;
		$scope.summaryData.sys_act_match_count = 0;
		$scope.summaryData.act_type_match_count = 0;
		$scope.summaryData.error_count = 0;
		$scope.summaryData.service_id_match_count = 0;
		$scope.summaryData.uinfo_match_count = 0;
		$scope.summaryData.all_match_count = 0;

		// snList를 만들어준다.
		var snList = "";
		for(var i=0; i<cnt; i++){
			var tcDt = $scope.testCaseList[i];
			snList += tcDt.sn+"-";
		}
		if(snList != ""){
			snList = snList.substring(0, snList.length-1);
		}

		var uuid = cmmUuid();
		var prms = {"uuid":uuid,
					"id":$scope.header.id,
					"reqmsg":"",
					"status":$scope.header.status,
					"event":$scope.header.event,
					"uinfo":$scope.header.uinfo,
					"context":$scope.header.context,
					"STB_VER":$scope.header.STB_VER,
					"stbVersion":$scope.header.stbVersion,
					"product_id":$scope.header.product_id,
					"appID":$scope.header.appID,
					"snList":snList,
					"tm":getUnixTimeStamp()} ;

		console.log(prms);

		$scope.showLoading('결과를 기다리고 있습니다. 잠시만 기다려 주세요');
		var urls = "testcaseTest.do";
		$http.get(urls, {params: prms})
		  .success(function(dt) {
			  $scope.hideLoading();
			  if(dt != ""){
				  $scope.loadLogList(uuid, prms);
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
							
							console.log(ok_serviceDomain);

							// all correct count
							if(ok_search==true && ok_sysAct==true && ok_actType==true && ok_serviceDomain==true && ok_Pattern==true){
								actInArr.is_all_match = "1";
								$scope.summaryData.all_match_count++;
							}else{
								actInArr.is_all_match = "0";
							}


							// searched header

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
						/*
						console.log("ok_search="+ok_search);
						console.log("ok_sysAct="+ok_sysAct);
						console.log("ok_actType="+ok_actType);
						console.log("ok_serviceDomain="+ok_serviceDomain);
						console.log("ok_Pattern="+ok_Pattern);
						*/
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

function setHeaderFavGenre(arg){
	angular.element(document.getElementById('casetest')).scope().setHeaderFavGenre(arg);
}

function setHeaderStatus(arg){
	angular.element(document.getElementById('casetest')).scope().setHeaderStatus(arg);
}
