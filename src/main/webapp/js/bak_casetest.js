ang.controller('casetestCtrl', function($scope, $http, $ionicPopup, $ionicLoading, focusForm) {

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
	$scope.header.id            = _LOGIN_ID;
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
	
	jQuery("#id").val(_LOGIN_ID );
	
	
		
	// left menu data
	$scope.leftmenu = [];
	
	// data list
	$scope.headerList = [];				// header list
	$scope.actTypeList = [];            // act type list 
	
	// testcaseList & result
	$scope.testCaseListAll = [];
	$scope.testCaseList    = [];
	$scope.testCaseResult  = [];
	$scope.testCaseResultSingle = [];
	
	$scope.searchTotalCount = 0;
	$scope.logXML = "";
	$scope.testCaseListSort    = ['in_domain','act_id'];
	$scope.resultFilter = {};
	
	
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
			var k = i-1;			
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
		$scope.header.uinfo = tmp;
	}
	$scope.setSearchHeader = function(){
		$scope.setUinfo();
		$scope.header.id         = jQuery("#id").val();
		$scope.header.fav_point  = jQuery("#selFavPoint").val();
		$scope.header.status     = jQuery("#edtStatus").val();;
		$scope.header.STB_VER    = jQuery("#STB_VER").val();;
		$scope.header.stbVersion = jQuery("#stbVersion").val();;
		$scope.header.devService_id = jQuery("#devService_id").val();;
		$scope.header.product_id = jQuery("#product_id").val();;
		$scope.header.appID = jQuery("#appID").val();;
		
		console.log($scope.header);
	}
	
	
	// ==================================================================================
	// 버튼 클릭 
	// ==================================================================================
	// 선호장르 보여주기 버튼 클릭
	$scope.showFavGenrePOPClick = function(){
		var winWidth  = 680;
		var winHeight = 580;
		var winURL  = "pop_fav_genre.jsp";
		var winName = "pop_fav_genre";
		var winPosLeft = (screen.width - winWidth) / 2;
		var winPosTop  = (screen.height - winHeight) / 2;
		var winOpt = "width="+winWidth+",height="+winHeight+",top="+winPosTop+",left="+winPosLeft;
		window.open(winURL, winName, winOpt + ",menubar=no,status=no,scrollbars=no,resizable=no");
	}
	
	// 선호장르 보여주기 버튼 클릭
	$scope.showStatusPOPClick = function(){
		var winWidth  = 450;
		var winHeight = 570;
		var winURL  = "pop_status.jsp";
		var winName = "pop_status";
		var winPosLeft = (screen.width - winWidth) / 2;
		var winPosTop  = (screen.height - winHeight) / 2;
		var winOpt = "width="+winWidth+",height="+winHeight+",top="+winPosTop+",left="+winPosLeft;
		window.open(winURL, winName, winOpt + ",menubar=no,status=no,scrollbars=no,resizable=no");
	}
	
	// 레프트메뉴 전체선택
	$scope.leftMenuSelAll = function(){
		var cnt = $scope.leftmenu.length;
		var ck = jQuery("#chk_leftall").prop('checked');
		console.log("ck="+ck);
		for(var i=0; i<cnt; i++){
			$scope.leftmenu[i].checked = ck;
		}
	}

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
	}
	
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
	
	// ==================================================================================
	// 외부호출
	// ==================================================================================
	$scope.setHeaderStatus = function(arg){
		console.log("setHeaderStatus="+arg);
		$scope.header.status = arg;
		jQuery("#edtStatus").val(arg);
	}
	$scope.setHeaderFavGenre = function(arg){
		console.log("setHeaderFavGenre="+arg);		
		$scope.header.fav_genre = arg;
		jQuery("#edtFavGenre").val(arg);
	}
	
	
	// ==================================================================================
	// Common
	// ==================================================================================
	$scope.ordTitle = function(){
		$scope.testCaseListSort = ['exam'];
	}
	
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
		var cnt = $scope.leftmenu.length;
		for(var i=0; i<cnt; i++){
			var dt = $scope.leftmenu[i];
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
	}
	
	// ==================================================================================
	// Load Init Data
	// ==================================================================================
	$scope.loadInitData = function(){
		$scope.showLoading("기초 자료를 불러오고 있습니다");
	    jQuery.ajax({
	    	url: "testcaseBaseData.do",dataType : "json"
	    })
	    .done(function( dt ) {
	    		$scope.hideLoading();
				//console.log(dt);
				setTimeout(function () {
					$scope.$apply(function () {
						$scope.actTypeList       = dt.actTypeList;
						$scope.testCaseListAll   = dt.testcaseList;
						$scope.leftmenu          = dt.testcaseCategoryList;
						//console.log($scope.actTypeList);
					});
				}, 1000);
	    });		
	}
	$scope.loadInitData();


	// ==================================================================================
	// single send from title click
	// ==================================================================================	
	$scope.sendSingleFormForce = function(){
		$scope.selTestItem = {};
		$scope.testCaseResultSingle = [];					
		$scope.testCaseResult = [];	
		
		$scope.logXML = "";
		var urls = "/kdialog/web/getActionGetBrokerUTF";
		var vv = $scope.data.reqmsg_f;
		var inParams = {"i":"0",
			            "id":$scope.data.id, 
			            "status":$scope.data.status, 
						"reqmsg":encodeURIComponent(vv),
						"event":$scope.data.event,
						"uinfo":$scope.data.uinfo,
						"context":$scope.data.context,
						"STB_VER":$scope.data.STB_VER,
						"stbVersion":$scope.data.stbVersion,
						"product_id":$scope.data.product_id,
						"appID":$scope.data.appID,
						"tm":getUnixTimeStamp()} ;
						
		$http.get(urls, {params: inParams})
			.success(function(dt) {	
				$scope.logXML = dt;	
			})
			.error(function(data) {
		});		
	}

	// ----------------------------------------------------------------------------------
	$scope.sendSingle = function(testId){
		$scope.logXML = "";	
		$scope.testCaseResultSingle = [];					
		$scope.testCaseResult = [];		
		
		var tcDt;
		var idx;
		for(var i=0; i<$scope.testCaseList.length; i++){
			var dd = $scope.testCaseList[i].test_id;
			if(dd==testId){
				tcDt = $scope.testCaseList[i];
				idx = i;
				$scope.testCaseList[i].is_check = "1";
				break;
			}
		}
		
		var pStatus   = $scope.data.status;
		var pEvent    = $scope.data.event;
		var pUinfo    = $scope.data.uinfo;
		var pContext  = $scope.data.context;
		if(tcDt.p_status != ''){	pStatus  = tcDt.p_status;	}
		if(tcDt.p_event  != ''){	pEvent   = tcDt.p_event;	}
		if(tcDt.p_uinfo  != ''){	pUinfo   = tcDt.p_uinfo;	}
		if(tcDt.p_context != ''){	pContext = tcDt.p_context;	}

		var inParams = {"i":idx,
						"id":$scope.data.id, 
						"reqmsg":encodeURIComponent(tcDt.exam),
						"status":pStatus, 						
						"event":pEvent,
						"uinfo":pUinfo,
						"context":pContext,
						"STB_VER":$scope.data.STB_VER,
						"stbVersion":$scope.data.stbVersion,
						"product_id":$scope.data.product_id,
						"appID":$scope.data.appID,
						"tm":getUnixTimeStamp()} ;

			console.log(inParams);
			
			$scope.testCaseResultSingle = [];					
			var urls = "/kdialog/web/getActionGetBrokerUTF";
			jQuery.ajax ( {
				url: urls,
				dataType: "xml",
				data: inParams,
				async: false,
				success: function( dt ) {
					//console.log(dt);
					if(dt != null){
						var xmlText = new XMLSerializer().serializeToString(dt);
						$scope.logXML = xmlText;
						$scope.searchMainResult(idx, inParams, tcDt, dt);						
					}
				}
			});	
	}

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
		$scope.searchTotalCount = 0;			
		
		$scope.summaryData = {};
		$scope.summaryData.test_count =  cnt;
		$scope.summaryData.sys_act_match_count = 0;
		$scope.summaryData.act_type_match_count = 0;
		$scope.summaryData.error_count = 0;
		$scope.summaryData.service_id_match_count = 0;
		$scope.summaryData.uinfo_match_count = 0;
		$scope.summaryData.all_match_count = 0;		
		
		$scope.showLoading();
		setTimeout(function () {
			
			$scope.$apply(function () {
				
				for(var i=0; i<cnt; i++){
					var tcDt = $scope.testCaseList[i];
					var prms = {"i":i,
								"id":$scope.header.id, 
								"reqmsg":tcDt.exam,
								"status":$scope.header.status, 						
								"event":$scope.header.event,
								"uinfo":$scope.header.uinfo,
								"context":$scope.header.context,	
								"STB_VER":$scope.header.STB_VER,
								"stbVersion":$scope.header.stbVersion,
								"product_id":$scope.header.product_id,
								"appID":$scope.header.appID,
								"tm":getUnixTimeStamp()} ;

						$scope.searchMain(i, prms, tcDt);
				}
				
			});			
			

		}, 5000);
		$scope.hideLoading();
		//console.log("---------------------------------------------------------");
		//console.log($scope.testCaseResult);
	}
	
	// --------------------------------------------------------------------- sync (not async - need jQuery 1.8 below)
	$scope.searchMain = function(idx, inParams, tcDt){
		$scope.testCaseResultSingle = [];					
		var urls = "getActionGetBrokerUTF.do";

		jQuery.ajax ( {
			url: urls,
			dataType: "xml",
			data: inParams,
			async: false,
			success: function( dt ) {
				console.log(dt);
				if(dt != null){
					$scope.searchMainResult(idx, inParams, tcDt, dt);						
				}
			}
		});
	}
	$scope.searchMainResult = function(idx, inParams, tcDt, dt){
		//$scope.searchTotalCount++;
		idx = inParams.i;
		var erK = 0;
		
		tcDt.actTypeStr =  $scope.findActNameByActType(tcDt.ans_actType);	
		tcDt.catNm =  $scope.findCategoryName(tcDt.cat_id);
		
		var jStr = xml2json(dt,"");
		jStr = jStr.replace(/#cdata/gi, "cdata");
		var utter = JSON.parse(jStr);
															
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
							actInArr.i = idx;
							actInArr.result_idx  = (j+1)+"/"+actionLen;
							actInArr.uuid = $scope.uuid();
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
							var actGroupMatch = $scope.chkActGroupPerm(actInArr.actGroup);
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
						actInArr.i = idx;
						actInArr.result_idx  = "1/1";
						actInArr.uuid = $scope.uuid();
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
						var actGroupMatch = $scope.chkActGroupPerm(actInArr.actGroup);
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
	$scope.chkActGroupPerm = function(arg){
		var tt = "";
		if(arg == undefined || arg==""){
			arg = "0";
		}	
		if(arg == "0"){
			tt = "1";
		}else{
			if($scope.header.uinfo == ""){
				return "0";
			}else{
				var uinfo = $scope.findValueByKey($scope.header.uinfo, "ID");	
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
	// -------------------------------------------------------------
	$scope.testItemModifyMain = function(){
		if(confirm(' Would you like to edit ? ')==false){
			return;
		}
		
		var urls = "http://litual.cafe24.com/dss/proc/modify_item.php";
		$http.get(urls, {params: inParams})
			.success(function(dt) {	
					console.log(dt);
			})
			.error(function(data) {
				alert("error");
		});	
	}
	
	// ------------------------------------------------------------- action requery, xml
	$scope.showActionXML = function(idx){
		var dt = $scope.actionResult[idx];
		console.log(dt);
	}
	
	
	/*
	// ============================================================== addHeader, editHeader, deleteHeader
	$scope.addHeaderClick = function(){
		if(confirm(' 헤더정보를 추가 하시겠습니까? ')==false){
			return;
		}		
		$scope.headerProc("http://litual.cafe24.com/dss/proc/header_regist_main.php");			
	}
	// --------------------------------------------------------------
	$scope.editHeaderClick = function(){
		if(confirm(' 헤더정보를 수정저장 하시겠습니까? ')==false){
			return;
		}
		$scope.headerProc("http://litual.cafe24.com/dss/proc/header_modify_main.php");			
	}
	
	// --------------------------------------------------------------
	$scope.deleteHeaderClick = function(){
		if(confirm(' 헤더정보를 삭제 하시겠습니까? ')==false){
			return;
		}
		$scope.headerProc("http://litual.cafe24.com/dss/proc/header_delete_main.php");
	}
	// --------------------------------------------------------------
	$scope.headerProc = function(urls){
		var inParams = {"sn":$scope.data.sn,
		                "header_nm":$scope.data.header_nm,
		                "id":$scope.data.id, 
		                "status":$scope.data.status, 
					    "event":$scope.data.event,
					    "uinfo":$scope.data.uinfo,
					    "context":$scope.data.context,
					    "STB_VER":$scope.data.STB_VER,
					    "stbVersion":$scope.data.stbVersion,
					    "product_id":$scope.data.product_id,
					    "appID":$scope.data.appID,
					    "tm":getUnixTimeStamp()} ;
		jQuery.ajax({
		  url: urls+"?callback=?",
		  dataType : "jsonp",
		  data : inParams
		})
		.done(function( dt ) {
			console.log(dt);
			if(dt=="1"){
				alert(" 처리 되었습니다 ");
			}else{
				alert("처리중 오류가 발생하였습니다");
			}
		});			
	}
	*/
	
	// ============================================================== addTestcase, editTestCase
	$scope.editTestCase = function(testId){
		var sn = "";
		for(var i=0; i<$scope.testCaseList.length; i++){
			var dd = $scope.testCaseList[i].test_id;
			if(dd==testId){
				sn = $scope.testCaseList[i].sn;
				break;
			}
		}	
		var winWidth  = 860;
		var winHeight = 550;
		var winURL  = "testcaseEdit.do?sn="+sn;
		var winName = "testcaseAdd";
		var winPosLeft = (screen.width - winWidth) / 2;
		var winPosTop  = (screen.height - winHeight) / 2;
		var winOpt = "width="+winWidth+",height="+winHeight+",top="+winPosTop+",left="+winPosLeft;
		window.open(winURL, winName, winOpt + ",menubar=no,status=no,scrollbars=no,resizable=no");			
	}
	
	// ---------------------------------------------------------------
	$scope.addTestCase = function(){
		var winWidth  = 860;
		var winHeight = 550;
		var winURL  = "testcaseEdit.do";
		var winName = "testcaseAdd";
		var winPosLeft = (screen.width - winWidth) / 2;
		var winPosTop  = (screen.height - winHeight) / 2;
		var winOpt = "width="+winWidth+",height="+winHeight+",top="+winPosTop+",left="+winPosLeft;
		window.open(winURL, winName, winOpt + ",menubar=no,status=no,scrollbars=no,resizable=no");
	}
	
	// ---------------------------------------------------------------
	$scope.manageTestCase = function(){
		document.location.href = "testcaseManage.do";
	}
	
	// ===============================================================
	// testGroup
	// ===============================================================
	$scope.testgroupManage = function(){
		var winWidth  = 860;
		var winHeight = 550;
		var winURL  = "testgroupEdit.html";
		var winName = "testgroupEdit";
		var winPosLeft = (screen.width - winWidth) / 2;
		var winPosTop  = (screen.height - winHeight) / 2;
		var winOpt = "width="+winWidth+",height="+winHeight+",top="+winPosTop+",left="+winPosLeft;
		window.open(winURL, winName, winOpt + ",menubar=no,status=no,scrollbars=auto,resizable=yes");	
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
