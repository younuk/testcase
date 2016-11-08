ang.controller('casetestManageCtrl', function($scope, $http, $ionicPopup, $ionicLoading, focusForm) {

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
	$scope.categoryList = [];
	$scope.casetestList = [];


	// afterCellEdit
	$scope.$on('ngGridEventEndCellEdit', function(data) {
		 var idx = data.targetScope.row.rowIndex;
		 var sn = $scope.casetestList[idx].sn;
		 var field = data.targetScope.col.field;
		 var value = data.targetScope.row.entity[field];

		 var urls = "testcaseUpdateField.do";
		 var inParams = {"sn":sn, "field":field, "value":value, "tm":getUnixTimeStamp()} ;
		 $http.get(urls, {params: inParams})
			.success(function(dt) {
				console.log("testcaseUpdateFieldValue result="+dt);
			})
			.error(function(data) {
		 });
	});

	// itemDelete
	$scope.deleteGridItem = function(rowIndex){
		var dt = $scope.casetestList[rowIndex];
		var confirmPopup = $ionicPopup.confirm({
				title: '삭제확인',
				template: '선택하신 정보를 삭제 하시겠습니까?'
		});
		confirmPopup.then(function(res) {
			if(res) {

				var urls = "testcaseDelete.do";
				var inParams = {"sn":dt.sn, "tm":getUnixTimeStamp()} ;
				$http.get(urls, {params: inParams})
					.success(function(dt) {
						console.log("delete result="+dt);
						if(dt=="1"){
							$scope.casetestList.splice(rowIndex, 1);
						}
					})
					.error(function(data) {
				});
			}
		});
	};

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
	$scope.loadInitData = function(){
		$scope.showLoading("자료를 불러오고 있습니다");

		var exam = jQuery("#edtSearchExam").val();
		var urls = "testcaseMngBaseData.do";
		if(exam != ""){
			urls += "?exam="+exam;
		}
		console.log(urls);
	    jQuery.ajax({
	    	url: urls,
	    	dataType : "json"
	    })
	    .done(function( dt ) {
	    		$scope.hideLoading();
				//setTimeout(function () {
					$scope.$apply(function () {
						$scope.casetestList   = dt.testcaseList;
						$scope.categoryList   = dt.testcaseCategoryList;
					});
				//}, 1000);
	    });
	};
	$scope.loadInitData();


	// ==================================================================================
	// POPUP
	// ==================================================================================
	$scope.addTestcase = function(){
		window.open("testcaseEdit.do", "casetestEdit", makePopupOpt(800, 600));
	};

	$scope.modifyGridItem = function(idx){
		window.open("testcaseEdit.do?sn="+idx, "casetestEdit", makePopupOpt(800, 600));
	};
});

function loadInitData(){
	angular.element(document.getElementById('casetestManage')).scope().loadInitData();
}
