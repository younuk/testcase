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
	
	
	// ==================================================================================
	// Grid
	// ==================================================================================	
    $scope.gridOptions = { 
            data: 'casetestList',
            enableCellSelection: true,
            enableRowSelection: false,
            enableCellEdit: true,
            columnDefs: [
							{field:'idx', displayName:'(n)', enableCellEdit:true, cellClass:"cellCenter", width:"40px"},			
							{field:'sn', displayName:'(c)', enableCellEdit:true, cellClass:"cellCenter", width:"40px"},			
							{field: 'cat_nm', displayName: '서비스', enableCellEdit:false, cellClass:"cellCenter", width:"100px"}, 
							{field: 'service_domain_name', displayName: '서비스도메인', enableCellEdit:false, cellClass:"cellCenter", width:"100px"},
							{field: 'in_domain', displayName: 'inDomain', enableCellEdit:true, cellClass:"cellCenter", width:"100px"}, 														
							{field: 'exam', displayName: 'exam', enableCellEdit:true,  cellClass:"cellLeft", width:"300px"},
							{field: 'ans_sysAct', displayName: 'sysAct', enableCellEdit:true, cellClass:"cellCenter", width:"90px"}, 														
							{field: 'ans_actType', displayName: 'actType', enableCellEdit:true, cellClass:"cellCenter", width:"90px"}, 
							{field: 'service_id_str', displayName: 'serviceId', enableCellEdit:true, cellClass:"cellLeft", width:"250px"}, 																					
							{field: 'idx', displayName: 'Modify', enableCellEdit:false,  cellClass:"cellCenter", width:"63px", cellTemplate: '<input type="button" ng-click="modifyGridItem(row.rowIndex)" value="Modify"/>'},
							{field: 'idx', displayName: 'Delete', enableCellEdit:false,  cellClass:"cellCenter", width:"63px", cellTemplate: '<input type="button" ng-click="deleteGridItem(row.rowIndex)" value="Delete"/>'}
						]
    };	
    
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
	}    
	
	
	// ==================================================================================
	// Common
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
	
	// ==================================================================================
	// Load Init Data
	// ==================================================================================	
	$scope.loadInitData = function(){
		$scope.showLoading("자료를 불러오고 있습니다");
		
		var exam = jQuery("#edtSearchExam").val();
		var urls = "testcaseListJson.do";
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
				//console.log(dt);
				//setTimeout(function () {
					$scope.$apply(function () {
						$scope.casetestList   = dt.testcaseList;
						$scope.categoryList   = dt.testcaseCategoryList;
						//console.log($scope.casetestList);
					});
				//}, 1000);
	    });		
	}
	$scope.loadInitData();
	
	
	// ==================================================================================	
	// POPUP
	// ==================================================================================		
	$scope.addTestcase = function(){
		var winWidth  = 800;
		var winHeight = 580;
		var winURL  = "casetestEdit.do";
		var winName = "casetestEdit";
		var winPosLeft = (screen.width - winWidth) / 2;
		var winPosTop  = (screen.height - winHeight) / 2;
		var winOpt = "width="+winWidth+",height="+winHeight+",top="+winPosTop+",left="+winPosLeft;
		window.open(winURL, winName, winOpt + ",menubar=no,status=no,scrollbars=no,resizable=no");
	}
	
	$scope.modifyGridItem = function(idx){
		var dt = $scope.casetestList[idx];
		var winWidth  = 800;
		var winHeight = 580;
		var winURL  = "casetestEdit.do?sn="+dt.sn;
		var winName = "casetestEdit";
		var winPosLeft = (screen.width - winWidth) / 2;
		var winPosTop  = (screen.height - winHeight) / 2;
		var winOpt = "width="+winWidth+",height="+winHeight+",top="+winPosTop+",left="+winPosLeft;
		window.open(winURL, winName, winOpt + ",menubar=no,status=no,scrollbars=no,resizable=no");
	}	
});
