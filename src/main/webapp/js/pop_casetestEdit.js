angular.module('testEdit', ['ngCookies'])
	.factory('focusForm', function($timeout, $window) {
		return function(id) {
		  $timeout(function() {
			var element = $window.document.getElementById(id);
			console.log(element);
			if(element)
			  element.focus();
		  });
		};
	})
	.directive('myEnter', function () {
		return function (scope, element, attrs) {
			element.bind("keydown keypress", function (event) {
				if(event.which === 13) {
					scope.$apply(function (){
						scope.$eval(attrs.myEnter);
					});

					event.preventDefault();
				}
			});
		};
	})	
		
	.controller('testEdit', function($scope, $location, $http, $timeout, focusForm) {

		//var x2js = new X2JS();

		var d = new Date();
		var curYear  = d.getFullYear();
		var curMonth = d.getMonth() + 1;
		var curDay   = d.getDate();
		if(curMonth < 10){
			curMonth = "0"+curMonth;
		}
		if(curDay < 10){
			curDay = "0"+curDay;
		}
		
		var query_string = {};
		var query = window.location.search.substring(1);
		var vars = query.split("&");
		for (var i=0;i<vars.length;i++) {
			var pair = vars[i].split("=");
			if (typeof query_string[pair[0]] === "undefined") {
				query_string[pair[0]] = decodeURIComponent(pair[1]);
			} else if (typeof query_string[pair[0]] === "string") {
				var arr = [ query_string[pair[0]],decodeURIComponent(pair[1]) ];
				query_string[pair[0]] = arr;
			} else {
				query_string[pair[0]].push(decodeURIComponent(pair[1]));
			}
		} 
		console.log(query_string);
		
		
		// data
		$scope.data = {};
		$scope.data.sn = query_string['sn'];
		$scope.data.chk_method = "equal";
		
		
		// load data if sn exists
		if($scope.data.sn != undefined){
			var urls = "testcaseBySn.do?sn="+$scope.data.sn+"&tm="+getUnixTimeStamp();
			jQuery.ajax({
			  url: urls,
			  dataType : "json",
			  async: false
			})
			.done(function( dt ) {
				console.log(dt);
				setTimeout(function () {
					$scope.$apply(function () {
						$scope.data = dt;
					});
				}, 1000);	
			});	
		}
		
		
		// send
		$scope.btnOKClick = function(){
		
			var catId = jQuery("#cat_id").val();
			if(catId == '' || catId == undefined || catId == null){
				alert(' category 를 선택해 주세요 ');
				return;
			}			
			
			if($scope.data.exam == '' || $scope.data.exam == undefined || $scope.data.exam == null){
				alert('reqmsg 를 입력해 주세요 ');
				focusForm('exam');
				return;
			}
			if($scope.data.service_domain == '' || $scope.data.service_domain == undefined || $scope.data.service_domain == null){
				alert('service domain을 입력해 주세요');
				focusForm('service_domain');
				return;
			}
			if($scope.data.in_domain == '' || $scope.data.in_domain == undefined || $scope.data.in_domain == null){
				alert('내부 domain을 입력해 주세요 ');
				focusForm('in_domain');
				return;
			}	
			if($scope.data.ans_sysAct == '' || $scope.data.ans_sysAct == undefined || $scope.data.ans_sysAct == null){
				alert('sysAct를 입력해 주세요');
				focusForm('ans_sysAct');
				return;
			}		
			if($scope.data.ans_actType == '' || $scope.data.ans_actType == undefined || $scope.data.ans_actType == null){
				alert('actType을 입력해 주세요 ');
				focusForm('ans_actType');
				return;
			}			
			
			
			
			if(confirm(' 처리 하시겠습니까? ')==false){
				return;
			}
			
			
			//document.form.submit();
			//return;
			var urls = "testcaseiom.do";
			var testgroup = "1";
			
			var inParams = {"sn": isString($scope.data.sn,"0"),
				    "gid":testgroup,
				    "cat_id":catId,
	                "exam":$scope.data.exam,
	                "service_domain":$scope.data.service_domain,
				    "in_domain":$scope.data.in_domain,
				    "service_id_str":$scope.data.service_id_str,
				    "ans_sysAct":$scope.data.ans_sysAct,
				    "ans_actType":$scope.data.ans_actType,
				    "ans_srchword":$scope.data.ans_srchword,
				    "ans_srchopt":$scope.data.ans_srchopt,
				    "ans_srchqry":$scope.data.ans_srchqry,
				    "ans_srchword":$scope.data.ans_srchword,
				    "ans_sword": $scope.data.ans_sword,
				    "chk_method":$scope.data.chk_method,
				    "service_id_tmpl":$scope.data.service_id_tmpl,				   
				    "service_id_ptrn":$scope.data.service_id_ptrn,
				    "p_status":$scope.data.p_status,
				    "p_event":$scope.data.p_event,
				    "p_uinfo":$scope.data.p_uinfo,
				    "p_context":$scope.data.p_context};
					
			jQuery.ajax ( {
				url: urls,
				dataType: "json",
				data: inParams,
				async: false,
				success: function( dt ) {
					if(dt.result == "1"){
						alert(" 처리되었습니다 ");
						self.close();
					}else{
						alert(" 처리중 오류가 발생하였습니다");
					}
				}
			});						
		
		}
	});

    function catGroupChange(){
    	var gid = $("#cat_gid").val();
    	var len = categoryList.length;
    	jQuery("#cat_id").find('option').remove();
		jQuery("#cat_id").append("<option value=''>--선택--</option>");    	
    	for(var i=0; i<len; i++){
    		var d = categoryList[i];
    		if( d.gid == gid && d.dept>0 ){
    			jQuery("#cat_id").append("<option value='"+d.cat_id+"'>"+d.nm+"</option>");
    		}
    	}
    }