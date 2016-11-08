//var ggid = jQuery{gid};;

ang.controller('customtestManageCtrl', function($scope, $http, $ionicPopup, $ionicLoading, focusForm) {

	$scope.serviceDomainList = [
	                             	{"service_domain":0, "name":"기본", "checked":true},
	                             	{"service_domain":1, "name":"비서"},
	                             	{"service_domain":2, "name":"OTV"},
	                             	{"service_domain":3, "name":"전화"},
	                             	{"service_domain":4, "name":"홈캠"},
	                             	{"service_domain":5, "name":"홈IOT"},
	                             	{"service_domain":6, "name":"지니뮤직"}
	                             ];

	$scope.service_domain_str  = ["기본","비서","OTV","전화","홈캠","홈IOT","지니뮤직"];

	// Header Vars
	$scope.header = {};

	$scope.casetestList = [];
	$scope.testcaseOrgList = [];
    $scope.categoryOrgList = [];

    $scope.upperCategoryList = [];
    $scope.categoryList = [];
	$scope.testcaseGroupList = [];


	// ==================================================================================
	// Common
	// ==================================================================================
	$scope.showLoading = function(msg) {
		$ionicLoading.show({
			template: msg
		});
	};

	$scope.hideLoading = function(){
		$ionicLoading.hide();
	};

	// ==================================================================================
	// Load Init Data
	// ==================================================================================
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

	$scope.loadInitData = function(){
		$scope.showLoading("기초 자료를 불러오고 있습니다");

		jQuery.ajax({
	    	url: "viewTestcaseGroupDetail.do?gid="+_gid
	    	,dataType : "json"
	    })
	    .done(function( dt ) {
	    	$scope.hideLoading();
			setTimeout(function () {
				$scope.$apply(function () {
					$scope.categoryOrgList = dt.catList;
					$scope.testcaseOrgList = dt.testcaseList;
					
					$scope.testcaseGroupList = dt.testcaseGroupList;
					if(isString(_gid, "") != ""){
						for(var i in $scope.testcaseGroupList){
							$scope.testcaseGroupList[i].chk = true;
						}
					}

					//set catetory list
					$scope.upperCategoryList = $scope.categoryOrgList.filter(function(el){ return (el.dept=='0');});
					$scope.categoryList = $scope.categoryList.filter(function(el){ return (el.dept=='1');});

					//set group info
					if(dt.testGroup != null){
						$scope.header = dt.testGroup;

						$scope.serviceDomainList[1].checked = ($scope.header.uinfo_1 == "1")? true: false;
						$scope.serviceDomainList[2].checked = ($scope.header.uinfo_2 == "1")? true: false;
						$scope.serviceDomainList[3].checked = ($scope.header.uinfo_3 == "1")? true: false;
						$scope.serviceDomainList[4].checked = ($scope.header.uinfo_4 == "1")? true: false;
						$scope.serviceDomainList[5].checked = ($scope.header.uinfo_5 == "1")? true: false;
						$scope.serviceDomainList[6].checked = ($scope.header.uinfo_6 == "1")? true: false;
					}
				});
			}, 1000);
	    });
	};
	$scope.loadInitData();

	// ==================================================================================
	// Click Function
	// ==================================================================================
	// 선호장르 보여주기 버튼 클릭
	$scope.showFavGenrePOPClick = function(){
		window.open(_cmmFavGenrePOP, "pop_fav_genre", makePopupOpt(680, 580));
	};

	// 상태 보여주기 버튼 클릭
	$scope.showStatusPOPClick = function(){
		window.open(_cmmStatusPOP, "pop_status", makePopupOpt(450, 570));

	};

	$scope.selSubCatList = function(param){
		$scope.categoryList = $scope.categoryOrgList.filter(function(el){
			if(param == "")
				return (el.dept=='1');
			else
				return (el.dept=='1' && el.gid == param); });
	};

	$scope.selTestCaseList = function(param){
		var selCatList = $scope.testcaseOrgList.filter(function(el){
															if(_gid == "") return (el.cat_id==param);
															else return ((el.gid!=_gid) && (el.cat_id==param));
														  });

		var dup_flag;
		for(var i in selCatList){
			dup_flag = false;

			for(var j in $scope.testcaseGroupList){
				if(selCatList[i].sn == $scope.testcaseGroupList[j].sn){
					dup_flag = true;
					break;
				}
			}

			if(!dup_flag)
				$scope.testcaseGroupList.push(selCatList[i]);
		}
	};

	// save
	$scope.btnSaveClick = function(){
		var groupNm = jQuery("input#group_nm").val();

		if(isString(groupNm, "") == ""){
			jQuery("input#group_nm").focus();
			alert(' 케이스그룹명을 입력해 주세요 ');
			return;
		}

		if(!confirm(' 처리 하시겠습니까? ')){
			return;
		}

		//var gid = jQuery("#gid").val();
		var snList = "";
		var tc_list;
		if($scope.testcaseGroupList != null && $scope.testcaseGroupList.length > 0)
			tc_list = $scope.testcaseGroupList.filter(function(el){ return el.chk; });

		for(var i in tc_list){
			snList += tc_list[i].sn+"-";
		}
		
		if(snList != ""){
			snList = snList.substring(0, snList.length-1);
		}

		var prms = {"snList":snList
					, "gid":(_gid == "")? 0: _gid
					, "group_nm": $scope.header.group_nm
				    , "uinfo_1": ($scope.serviceDomainList[1].checked)? "1": "0"
				    , "uinfo_2": ($scope.serviceDomainList[2].checked)? "1": "0"
					, "uinfo_3": ($scope.serviceDomainList[3].checked)? "1": "0"
					, "uinfo_4": ($scope.serviceDomainList[4].checked)? "1": "0"
					, "uinfo_5": ($scope.serviceDomainList[5].checked)? "1": "0"
					, "uinfo_6": ($scope.serviceDomainList[6].checked)? "1": "0"
	                , "fav_genre":$scope.header.fav_genre
	                , "fav_point":$scope.header.fav_point
				    , "status":$scope.header.status
				    /*
				    , "event":$scope.data.ans_sysAct
				    , "in_domain": jQuery("#").val()
				    , "context":$scope.data.ans_srchword
				    */
				    , "login_id":jQuery("#id").val()
				    , "uinfo":$scope.setUinfo()
				    , "STB_VER":$scope.header.STB_VER
				    , "stbVersion":$scope.header.stbVersion
				    , "product_id":$scope.header.product_id
				    , "appID": $scope.header.appID
				    , "devService_id":$scope.header.devService_id
				    };

		$scope.showLoading('결과를 기다리고 있습니다. 잠시만 기다려 주세요');
		var urls = "setTestcaseGroupDetail.do";
		$http.get(urls, {params: prms})
		  .success(function(dt) {
			  $scope.hideLoading();
			  $scope.loadInitData();
		}).error(function(data) {
			$scope.hideLoading();
		});
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
});

function setHeaderFavGenre(arg){
	angular.element(document.getElementById('customtestManage')).scope().setHeaderFavGenre(arg);
}

function setHeaderStatus(arg){
	angular.element(document.getElementById('customtestManage')).scope().setHeaderStatus(arg);
}

function selSubCatList(arg){
	angular.element(document.getElementById('customtestManage')).scope().selSubCatList(arg);
}
