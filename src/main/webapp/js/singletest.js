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
	//$scope.header.id            = _LOGIN_ID;
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

	// data list
	$scope.headerList = [];				// header list
	$scope.actTypeList = [];            // act type list

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
		$scope.header.appID = jQuery("#appID").val();
	};


	// ==================================================================================
	// 버튼 클릭
	// ==================================================================================
	// 선호장르 보여주기 버튼 클릭
	$scope.showFavGenrePOPClick = function(){
		window.open(_cmmFavGenrePOP, "pop_fav_genre", makePopupOpt(_cmmPopFavGenreW, _cmmPopFavGenreH));
	};

	// 상태 보여주기 버튼 클릭
	$scope.showStatusPOPClick = function(){
		window.open(_cmmStatusPOP, "pop_status", makePopupOpt(_cmmPopStatusW, _cmmPopStatusH));

	};
	
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
				setTimeout(function () {
					$scope.$apply(function () {
						$scope.actTypeList       = dt.actTypeList;
					});
				}, 1000);
	    });
	};
	$scope.loadInitData();


	// ==================================================================================
	// single send from title click
	// ==================================================================================
	$scope.sendSingleFormForce = function(){
		$scope.selTestItem = {};
		$scope.testCaseResultSingle = [];
		$scope.testCaseResult = [];

		$scope.logXML = "";
		var urls  = "getActionGetBrokerUTF.do";
		var rm    = jQuery("#reqmsg").val();
		var ev    = jQuery("#event").val();

		if(rm == '' && ev == ''){
			alert(' 발화 또는 이벤트를 선택해 주세요');
			jQuery("#reqmsg").focus();
			return;
		}

		var inParams = {"i":"0",
		            "id":$scope.header.id,
		            "status":$scope.header.status,
					"reqmsg":encodeURIComponent(rm),
					"event":ev,
					"uinfo":$scope.header.uinfo,
					"context":$scope.header.context,
					"STB_VER":$scope.header.STB_VER,
					"stbVersion":$scope.header.stbVersion,
					"product_id":$scope.header.product_id,
					"appID":$scope.header.appID,
					"tm":getUnixTimeStamp()} ;

		jQuery.ajax ( {
			url: urls,
			dataType: "xml",
			data: inParams,
			async: false,
			success: function( dt ) {
				console.log(dt);
				if(dt != null){
					var xmlText = new XMLSerializer().serializeToString(dt);
					$scope.logXML = xmlText;
					$scope.tcDt = {};
					$scope.tcDt.exam = rm;
					$scope.searchMainResult(-1, inParams, $scope.tcDt, dt);
				}
			}
		});
	};


	$scope.searchMainResult = function(idx, inParams, tcDt, dt){
		idx = inParams.i;
		var erK = 0;

		var jStr = xml2json(dt,"");
		jStr = jStr.replace(/#cdata/gi, "cdata");
		var utter = JSON.parse(jStr);

		if(erK > 0){
			console.log("query error");

		}else{
			if(utter != undefined){

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

							var actInArr = action[j];
							actInArr.i = idx;
							actInArr.result_idx  = (j+1)+"/"+actionLen;
							actInArr.uuid = cmmUuid();
							actInArr.p_sysAct = sysAct;
							actInArr.p_uword  = uword;
							actInArr.baseTcDt = tcDt;
							actInArr.xml = dt;

							// strip cdata
							actInArr.mesg      = cmmGetXMLValue(actInArr.mesg);
							actInArr.serviceId = cmmGetXMLValue(actInArr.serviceId);

							// actTypeString
							actInArr.actTypeStr = scope.findActNameByActType(actInArr.actType);
							actInArr.search_check_result = "";

							// actGroup check
							actInArr.actGroup_str = $scope.service_domain_str[actInArr.actGroup];

							// add action result
							$scope.testCaseResult = $scope.testCaseResult.concat(actInArr);
						}

					}else if(prefix=="{"){

						var actInArr = action;
						actInArr.i = idx;
						actInArr.result_idx  = "1/1";
						actInArr.uuid = cmmUuid();
						actInArr.p_sysAct = sysAct;
						actInArr.p_uword  = uword;
						actInArr.baseTcDt = tcDt;
						actInArr.xml = dt;

						actInArr.mesg      = cmmGetXMLValue(actInArr.mesg);
						actInArr.serviceId = cmmGetXMLValue(actInArr.serviceId);

						// actTypeString
						actInArr.actTypeStr = $scope.findActNameByActType(actInArr.actType);

						// actGroup check
						actInArr.actGroup_str = $scope.service_domain_str[actInArr.actGroup];

						// add action result
						$scope.testCaseResult = $scope.testCaseResult.concat(actInArr);
					}
				}

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
