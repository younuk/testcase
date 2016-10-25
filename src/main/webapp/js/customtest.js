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
	$scope.testcaseGroupList  = [];		    // group list
	$scope.actTypeList = [];
	$scope.categoryList = [];

	// testcaseList & result
	$scope.testCaseListAll = [];
	$scope.testCaseList    = [];
	$scope.testCaseResult  = [];

	$scope.logXML = "";
	$scope.testCaseListSort    = ['in_domain','act_id'];
	$scope.resultFilter = {};




	// ==================================================================================
	// 버튼 클릭
	// ==================================================================================
	$scope.goPage = function(url){
		document.location.href = url;
	}

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
    	/*
	    jQuery.ajax({
	    	url: "testcaseByGidJson.do?gid="+gid,dataType : "json"
	    })
	    .done(function( dt ) {
			$scope.testCaseList = dt;
			$scope.testGo(gid);
	    });
	    */
    }
    $scope.btnEditClick = function(gid){
    	document.location.href = "customtestManage.do?gid="+gid;
    }
    $scope.btnSearchClick = function(){
		$scope.logXML = "";
		$scope.testCaseResult = [];
		$scope.searchTotalCount = 0;

		$scope.summaryData = {};
		$scope.summaryData.test_count =  0;
		$scope.summaryData.sys_act_match_count = 0;
		$scope.summaryData.act_type_match_count = 0;
		$scope.summaryData.error_count = 0;
		$scope.summaryData.service_id_match_count = 0;
		$scope.summaryData.uinfo_match_count = 0;
		$scope.summaryData.all_match_count = 0;

    	$scope.loadInitData($("input#edtSearch").val());
    }


	// ==================================================================================
	// Common
	// ==================================================================================
	$scope.s4 = function(){
		return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
	}
	$scope.uuid = function(){
		return $scope.s4()+$scope.s4()+'-'+$scope.s4()+'-'+$scope.s4()+'-'+$scope.s4()+'-'+$scope.s4()+$scope.s4()+$scope.s4();
	}

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
	}

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
	}

	$scope.range = function(min, max, step){
		step = step || 1;
		var input = [];
		for (var i = min; i <= max; i += step) input.push(i);
		return input;
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
	    .done(function( dt ) {
	    	console.log(dt);
	    	$scope.hideLoading();
			setTimeout(function () {
				$scope.$apply(function () {
					$scope.testcaseGroupList = dt.groupList;
					$scope.testCaseListAll   = dt.testcaseList;
					$scope.actTypeList       = dt.actTypeList;
					$scope.categoryList      = dt.categoryList;
				});
			}, 1000);
	    });
	}
	$scope.loadInitData();



	// ----------------------------------------------------------------------------------
	$scope.testGo = function(gid){
		// testgroup data
		var gcnt = $scope.testcaseGroupList.length;
		var groupDt;
		for(var k=0; k<gcnt; k++){
			var d = $scope.testcaseGroupList[k];
			if(d.gid == gid){
				groupDt = d;
				break;
			}
		}

		// sn list
		var snList = "";
		$scope.testCaseList = [];
		var tcnt = $scope.testCaseListAll.length;
		var groupDt;
		for(var p=0; p<tcnt; p++){
			var d = $scope.testCaseListAll[p];
			if(d.gid == gid){
				$scope.testCaseList = $scope.testCaseList.concat(d);
				snList += d.sn+"-";
			}
		}

		if(snList != ""){
			snList = snList.substring(0, snList.length-1);
		}

		// if no testcase
		var cnt = $scope.testCaseList.length;
		if(cnt == 0){
			alert(" 테스트케이가 존재하지 않습니다 ");
			return;
		}

		var id     = _LOGIN_ID;
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
		$scope.searchTotalCount = 0;

		$scope.summaryData = {};
		$scope.summaryData.test_count =  cnt;
		$scope.summaryData.sys_act_match_count = 0;
		$scope.summaryData.act_type_match_count = 0;
		$scope.summaryData.error_count = 0;
		$scope.summaryData.service_id_match_count = 0;
		$scope.summaryData.uinfo_match_count = 0;
		$scope.summaryData.all_match_count = 0;

		var uuid = $scope.uuid();
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
					"snList":snList,
					"tm":getUnixTimeStamp()} ;


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

	}
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
	}
	// ---------------------------------------------------------------------
	$scope.findBaseDataBySn = function(sn){
		console.log("findBaseDataBySn="+sn);
		console.log($scope.testCaseList);
		var c = $scope.testCaseList.length;
		for(var i=0; i<c; i++){
			if($scope.testCaseList[i].sn == sn){
				return $scope.testCaseList[i];
				break;
			}
		}
	}
	// ---------------------------------------------------------------------
	$scope.searchResultProc = function(idx, inParams, dt, uuid){
		//$scope.searchTotalCount++;
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
							actInArr.mesg      = $scope.getXMLValue(actInArr.mesg);
							actInArr.serviceId = $scope.getXMLValue(actInArr.serviceId);

							// actTypeString
							actInArr.actTypeStr =	$scope.findActNameByActType(actInArr.actType);
							actInArr.ori_service_domain_str = $scope.service_domain_str[tcDt.service_domain];

							// searchWord check
							var searchWordChkResult = $scope.searchWordValidityCheck(tcDt, actInArr);
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
							var svcPattern = $scope.serviceIdValidityCheck(tcDt, actInArr);
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
							var actGroupMatch = $scope.chkActGroupPerm(inParams, actInArr.actGroup);
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
						actInArr.mesg      = $scope.getXMLValue(actInArr.mesg);
						actInArr.serviceId = $scope.getXMLValue(actInArr.serviceId);

						// actTypeString
						actInArr.actTypeStr = $scope.findActNameByActType(actInArr.actType);
						actInArr.ori_service_domain_str = $scope.service_domain_str[tcDt.service_domain];

						// searchWord check
						var searchWordChkResult = $scope.searchWordValidityCheck(tcDt, actInArr);
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
						var svcPattern = $scope.serviceIdValidityCheck(tcDt, actInArr);
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
						var actGroupMatch = $scope.chkActGroupPerm(inParams, actInArr.actGroup);
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
	}



	// --------------------------------------------------------------- return cdata value
	$scope.getXMLValue = function(obj){
		var ret = "";
		if(obj==null || obj==undefined || obj==''){
			ret = "";
		}else{
			if(obj.hasOwnProperty("cdata")){
				ret = obj.cdata;
			}else{
				ret = obj;
			}
		}
		return ret;
	}

	// --------------------------------------------------------------- actGroup check : match =1, unmatch =0;
	$scope.chkActGroupPerm = function(inParams, arg){
		console.log("-----------chkActGroupPerm--------------------------------");
		console.log(inParams);

		var tt = "";
		if(arg == undefined || arg==""){
			arg = "0";
		}
		if(arg == "0"){
			tt = "1";
		}else{
			if(inParams.uinfo == ""){
				return "0";
			}else{
				var uinfo = $scope.findValueByKey(inParams.uinfo, "ID");
				var idx = eval(arg);
				if(idx>0){
					idx = idx-1;
					tt  = uinfo[idx];
					//console.log("chkActGroupPerm="+arg+":"+uinfo+":"+tt);
				}else{
					tt = "0";
				}
			}
		}
		return tt;
	}

	// ---------------------------------------------------------------
	$scope.loadAllId = function(){
		$scope.catFilter = {all_search:'0'};
	}
	$scope.loadSelId = function(){
		var selId = jQuery("#show-filter").val();
		//console.log("loadSelId="+selId);
		$scope.catFilter = {service_domain:selId};
	}
	$scope.loadTestGroup = function(){
		var selId = jQuery("#testgroup-filter").val();
		$scope.catFilter = {gid:selId};
	}
	$scope.getSearchFilter = function(){
		return $scope.catFilter;
	}


	$scope.setResultFilter = function(arg){
		if(arg != ''){
			$scope.resultFilter = {is_all_match:arg};
		}else{
			$scope.resultFilter = {};
		}
	}
	$scope.getResultFilter = function(){
		return $scope.resultFilter;
	}


	// ---------------------------------------------------------------
	$scope.showXML = function(testId){
		//console.log(testId);
		var xml = "";
		for(var i=0; i<$scope.testCaseList.length; i++){
			var dd = $scope.testCaseList[i].test_id;
			if(dd==testId){
				xml = $scope.testCaseList[i].xml;
				break;
			}
		}
		var xmlText = new XMLSerializer().serializeToString(xml);
		if(xmlText != ''){
			$scope.logXML = xmlText.replace(/>/gi,'>\n');
		}
		console.log(xmlText);
	}


	// ---------------------------------------------------------------
	$scope.showConsole = function(arg){
		console.log(arg);
	}

	// ---------------------------------------------------------------
	$scope.closeSingleBox = function(){
		$scope.actionResultSingle = [];
	}

	// ---------------------------------------------------------------
	$scope.closeXMLLog = function(){
		$scope.logXML = "";
	}

	// ---------------------------------------------------------------
	// serviceIdValidityCheck
	// ---------------------------------------------------------------
	$scope.serviceIdValidityCheck = function(tcDt, action){
		var chkMethod = tcDt.chk_method;
		var oriServiceIdTmpl = tcDt.service_id_tmpl;
		var oriServiceIdPtrn = tcDt.service_id_ptrn;
		var oriServiceIdStr  = tcDt.service_id_str;
		var serviceId = action.serviceId;

		// return value is error count
		var ret = "";

		if(chkMethod=="ptrn"){
			if(oriServiceIdPtrn=="param"){					ret = $scope.ptParam(oriServiceIdTmpl, serviceId);
			}else if(oriServiceIdPtrn=="value_integer"){	ret = $scope.ptValueInteger(oriServiceIdTmpl, serviceId);
			}else if(oriServiceIdPtrn=="value_string"){		ret = $scope.ptValueString(oriServiceIdTmpl, serviceId);
			}else if(oriServiceIdPtrn=="value_url"){		ret = $scope.ptValueUrl(oriServiceIdTmpl, serviceId);
			}else if(oriServiceIdPtrn=="na"){				ret = $scope.ptNa(oriServiceIdTmpl, serviceId);
			}else if(oriServiceIdPtrn=="ne"){				ret = $scope.ptNe(oriServiceIdTmpl, serviceId);
			}else if(oriServiceIdPtrn=="key_value"){		ret = $scope.ptKeyValue(oriServiceIdTmpl, serviceId);
			}else if(oriServiceIdPtrn=="key_static"){		ret = $scope.ptKeyStatic(oriServiceIdTmpl, serviceId);
			}else{
			}

		}else{  // equal
			if(action.serviceId == oriServiceIdStr){
				ret = "0";
			}else{
				ret = "1";
			}
		}

		//console.log("servicePatternCheck="+oriServiceId+":"+oriServicePtrn+":"+serviceId+"------>"+ret);
		if(ret == "999"){
			return "Key / Value not exist";
		}else if(ret == "0"){
			return "Good";
		}else {
			return ret;
		}
	}

	// ---------------------------------------------------------------------
	$scope.searchWordValidityCheck = function(tcDt, action){
		var ret = "0";
		//console.log(tcDt);
		//console.log("--------------------------------------------------------------------");
		//console.log(action);
		if(tcDt.ans_srchword == null){
			tcDt.ans_srchword = "";
		}
		if(action.srchWord == null){
			action.srchWord = "";
		}
		if(tcDt.ans_srchopt == null){
			tcDt.ans_srchopt = "";
		}
		if(action.srchOpt == null){
			action.srchOpt = "";
		}
		if(tcDt.ans_srchqry == null){
			tcDt.ans_srchqry = "";
		}
		if(action.srchQry == null){
			action.srchQry = "";
		}
		if(tcDt.ans_sword == null){
			tcDt.ans_sword = "";
		}
		if(action.sWord == null){
			action.sWord = "";
		}



		if( tcDt.ans_srchword != action.srchWord){
			//console.log(tcDt.ans_srchword+":"+action.srchWord);
			ret = "1";
		}
		if(tcDt.ans_srchopt != action.srchOpt){
			//console.log(tcDt.ans_srchopt+":"+action.srchOpt);
			ret = "1";
		}
		if(tcDt.ans_srchqry != action.srchQry){
			//console.log(tcDt.ans_srchqry+":"+action.srchQry);
			ret = "1";
		}
		if(tcDt.ans_sword != action.sWord){
			//console.log(tcDt.ans_sword+":"+action.sWord);
			ret = "1";
		}

		//console.log("--------------------------------------------------------------------");
		//console.log("-:"+ret);
		return ret;
	}

	// --------------------------------------------------------------- // param
	$scope.ptParam = function(ptrn, svcId){
		return "EyeChecking";
	}

	// ---------------------------------------------------------------	 // value_integer
	$scope.ptValueInteger = function (ptrn, svcId){
		if(jQuery.isNumeric(svcId) == true){
			return "0";
		}else{
			return "1";
		}
	}

	// ---------------------------------------------------------------	// value_string	:: not url
	$scope.ptValueString = function (ptrn, svcId){
		var ret = 999;
		if(svcId == "" || svcId == undefined){
			ret = "1";
		}else{
			if( (typeof svcId) == 'string' ){
				if(svcId.startsWith("http:\/\/") == true || svcId.startsWith("https:\/\/") == true){
					ret = "1";
				}else{
					ret = "0";
				}
			}else{
				ret = "1";
			}
		}
		return ret;
	}

	// ---------------------------------------------------------------	// value_url
	$scope.ptValueUrl = function (ptrn, svcId){
		if(svcId.startsWith("http:\/\/") == true || svcId.startsWith("https:\/\/") == true){
			return "0";
		}else{
			return "1";
		}
	}

	// --------------------------------------------------------------- // na
	$scope.ptNa = function (ptrn, svcId){
		return "NeedEyeChecking";
	}

	// ---------------------------------------------------------------	// ne
	$scope.ptNe = function (ptrn, svcId){
		if(svcId =='' || svcId ==undefined || svcId==null){
			return "0";
		}else{
			return "1";
		}
	}

	// ---------------------------------------------------------------	// key_value
	$scope.ptKeyValue = function (ptrn, svcId){
		var ptrArr  = ptrn.split(",");
		if(ptrArr != undefined){
			var errCount = 0;
			for(var k=0; k<ptrArr.length; k++){
				var kyArr = ptrArr[k];
				if(kyArr != undefined){
					var arrVal = kyArr.split(":");
					var kk = arrVal[0];
					var kv = arrVal[1];
					var rv = $scope.findValueByKey(svcId, kk);
					//console.log("kk.kv.rv="+kk+":"+kv+":"+rv);
					errCount += $scope.chkErrorCount(rv, kv);
				}
			}
			//console.log("AllErrCount="+errCount);
		}
		return errCount;
	}
	$scope.findValueByKey = function(str, key){
		var ret = "";
		if(str==null || str==undefined){
			str = "";
		}
		if(str != ''){
			var ptrArr  = str.split(",");
			if(ptrArr != undefined){
				for(var k=0; k<ptrArr.length; k++){
					var kyArr = ptrArr[k];
					//console.log(kyArr[0]);
					if(kyArr != undefined){
						var arrVal = kyArr.split(":");
						var kk = arrVal[0];
						var kv = arrVal[1];
						//console.log(kk+":"+kv);
						if(kk==key){
							ret += kv+",";
						}
					}
				}
			}
		}
		return ret;
	}

	// ---------------------------------------------------------------	// key_static
	$scope.ptKeyStatic = function (ptrn, svcId){
		var ptrArr  = ptrn.split(",");
		if(ptrArr != undefined){
			var errCount = 0;
			for(var k=0; k<ptrArr.length; k++){
				var kyArr = ptrArr[k];
				if(kyArr != undefined){
					var arrVal = kyArr.split(":");
					var kk = arrVal[0];
					var kv = arrVal[1];
					if(kv.startsWith("static-")){
						kv = kv.replace(/static-/gi, "");
					}
					var rv = $scope.findValueByKey(svcId, kk);
					if(rv != null){
						rv = rv.substring(0, rv.length-1);
					}

					//console.log("ptKeyStatic----kk="+kk+":kv="+kv+":rv="+rv);
					if(kv != rv){
						errCount += $scope.chkErrorCount(rv, kv);
					}
				}
			}
			//console.log("ptKeyStatic-AllErrCount="+errCount);
		}
		return errCount;
	}


	// ===============================================================
	// return data type error count (, split)
	// ===============================================================
	// ---------------------------------------------------------------
	$scope.chkErrorCount = function(str, ty){
		var arrCount = 0;
		var okCount  = 0;
		if(str == "" || str == undefined){
			arrCount = 999;
		}else{
			var arr = str.split(",");
			if(arr != undefined){
				arrCount = arr.length-1;   // end is always ,
				for(var k=0; k<arr.length; k++){
					var kv = arr[k];
					if(kv == undefined){
						kv = "";
					}

					// ----------------------------------------------
					if(ty=="string"){
						if(kv != ''){
							okCount++;
						}
					// ----------------------------------------------
					}else if(ty=="integer"){
						if(jQuery.isNumeric(kv) == true){
							okCount++;
						}
					// ----------------------------------------------
					}else if(ty=="url"){
						if(kv.startsWith("http:\/\/") == true || kv.startsWith("https:\/\/") == true){
							okCount++;
						}
					// ----------------------------------------------
					}else if(ty=="date_strip"){
						if(kv.length == 12){
							console.log("kv.length="+kv.length);
							okCount++;
						}
					// ----------------------------------------------
					}else if(ty=="date_dash"){
					// ----------------------------------------------
					}else if(ty=="date_ymd"){
						if(kv.length == 8){
							okCount++;
						}
					// ----------------------------------------------
					}else if(ty=="date_hm"){
						if(kv.length == 4){
							okCount++;
						}
					}
				}
			}
		}
		//console.log("ty="+ty+"arrCount="+arrCount+" /okCount="+okCount);
		return arrCount - okCount;
	}

	// -------------------------------------------------------------
	$scope.setServicePtrn = function(arg){
		$scope.selTestItem.service_ptrn = arg;
	}

	// ------------------------------------------------------------- action requery, xml
	$scope.showActionXML = function(idx){
		var dt = $scope.actionResult[idx];
		console.log(dt);
	}

});

function setHeaderFavGenre(arg){
	angular.element(document.getElementById('casetest')).scope().setHeaderFavGenre(arg);
}

function setHeaderStatus(arg){
	angular.element(document.getElementById('casetest')).scope().setHeaderStatus(arg);
}

function loadInitData(){
	angular.element(document.getElementById('casetest')).scope().loadInitData();
}

function btnSearchClick(){
	angular.element(document.getElementById('casetest')).scope().btnSearchClick();
}

