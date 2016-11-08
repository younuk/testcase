var _cmmFavGenrePOP = "pop_fav_genre.jsp";
var _cmmStatusPOP = "pop_status.jsp";
var _cmmResultXmlPOP = "showLogXML.do";
var _cmmPopStatusW = 465;
var _cmmPopStatusH = 645;
var _cmmPopFavGenreW = 783;
var _cmmPopFavGenreH = 691;
var _cmmXMLW = 750;
var _cmmXMLH = 640;
/**, 
 * Common.js
 * @param dom
 */
function jsonParam(dom){
    var d = $("#"+dom).serialize();
    var obj = $.deparam(d);
    return JSON.stringify(obj);
}

function getUnixTimeStamp(){
	return Math.floor(new Date().getTime() / 1000);
}	

function isString(str, def){
	var ret = def;
	if(str == null || str == undefined || str == ""){
		ret = def;
	}else{
		ret = str;
	}
	return ret;
}

function makePopupOpt(paramW, paramH){
	var winPosTop  = (screen.height - paramH) / 2;
	var winPosLeft = (screen.width - paramW) / 2;
	
	 return winOpt = "width="+paramW+",height="+paramH+",top="+winPosTop+",left="+winPosLeft
	 				  + ",menubar=no,status=no,scrollbars=no,resizable=no,location=no";
}

function getUnixTimeStamp(){
	return Math.floor(new Date().getTime() / 1000);
}

function goPage(url){
	document.location.href = url;
}


// 결과 정보 보여주기 버튼 클릭
function showActionXML(uuid, sn){
	var winURL  = _cmmResultXmlPOP+"?uuid="+uuid+"&sn="+sn;

	window.open(winURL, "pop_showxml", makePopupOpt(750, 570));
}



// ===============================================================
// return data type error count (, split)
// ===============================================================
// ---------------------------------------------------------------
function chkErrorCount(str, ty){
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
	return arrCount - okCount;
}

function cmmFindValueByKey(str, key){
	var ret = "";
	if(str==null || str==undefined){
		str = "";
	}
	if(str != ''){
		var ptrArr  = str.split(",");
		if(ptrArr != undefined){
			for(var k=0; k<ptrArr.length; k++){
				var kyArr = ptrArr[k];
				if(kyArr != undefined){
					var arrVal = kyArr.split(":");
					var kk = arrVal[0];
					var kv = arrVal[1];
					
					if(kk==key){
						ret += kv+",";
					}
				}
			}
		}
	}
	return ret;
};

function cmmPtKeyValue(ptrn, svcId){
	var ptrArr  = ptrn.split(",");
	var errCount = 0;
	if(ptrArr != undefined){
		for(var k=0; k<ptrArr.length; k++){
			var kyArr = ptrArr[k];
			if(kyArr != undefined){
				var arrVal = kyArr.split(":");
				var kk = arrVal[0];
				var kv = arrVal[1];
				var rv = cmmFindValueByKey(svcId, kk);

				errCount += chkErrorCount(rv, kv);
			}
		}
	}
	return errCount;
}

function cmmPtKeyStatic(ptrn, svcId){
	var ptrArr  = ptrn.split(",");
	var errCount = 0;
	if(ptrArr != undefined){
		for(var k=0; k<ptrArr.length; k++){
			var kyArr = ptrArr[k];
			if(kyArr != undefined){
				var arrVal = kyArr.split(":");
				var kk = arrVal[0];
				var kv = arrVal[1];
				if(kv.startsWith("static-")){
					kv = kv.replace(/static-/gi, "");
				}
				var rv = cmmFindValueByKey(svcId, kk);
				if(rv != null){
					rv = rv.substring(0, rv.length-1);
				}

				if(kv != rv){
					errCount += chkErrorCount(rv, kv);
				}
			}
		}
	}
	return errCount;
}

function cmmSearchWordValidityCheck(tcDt, action){
	var ret = "0";
	
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

	if( tcDt.ans_srchword != action.srchWord
			||tcDt.ans_srchopt != action.srchOpt
			||tcDt.ans_srchqry != action.srchQry
			|| tcDt.ans_sword != action.sWord){
		ret = "1";
	}

	return ret;
}

 function cmmPtParam(ptrn, svcId){
	return "EyeChecking";
}

// ---------------------------------------------------------------	 // value_integer
function cmmPtValueInteger(ptrn, svcId){
	return (jQuery.isNumeric(svcId) == true)? "0": "1";
}

// ---------------------------------------------------------------	// value_string	:: not url
function cmmPtValueString(ptrn, svcId){
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
function cmmPtValueUrl(ptrn, svcId){
	return (svcId.startsWith("http:\/\/") == true || svcId.startsWith("https:\/\/") == true)? "0": "1";
}

function cmmPtNa(ptrn, svcId){
	return "NeedEyeChecking";
}

function cmmPtNe(ptrn, svcId){
	return (svcId =='' || svcId ==undefined || svcId==null)? "0": "1";
}

function cmmChkActGroupPerm(inParams, arg){
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
			var uinfo = cmmFindValueByKey(inParams.uinfo, "ID");
			var idx = eval(arg);
			if(idx>0){
				idx = idx-1;
				tt  = uinfo[idx];
			}else{
				tt = "0";
			}
		}
	}
	return tt;
}

// ---------------------------------------------------------------
// serviceIdValidityCheck
// ---------------------------------------------------------------
function cmmServiceIdValidityCheck(tcDt, action){
	var chkMethod = tcDt.chk_method;
	var oriServiceIdTmpl = tcDt.service_id_tmpl;
	var oriServiceIdPtrn = tcDt.service_id_ptrn;
	var oriServiceIdStr  = tcDt.service_id_str;
	var serviceId = action.serviceId;

	// return value is error count
	var ret = "";

	if(chkMethod=="ptrn"){
		if(oriServiceIdPtrn=="param"){					ret = cmmPtParam(oriServiceIdTmpl, serviceId);
		}else if(oriServiceIdPtrn=="value_integer"){	ret = cmmPtValueInteger(oriServiceIdTmpl, serviceId);
		}else if(oriServiceIdPtrn=="value_string"){		ret = cmmPtValueString(oriServiceIdTmpl, serviceId);
		}else if(oriServiceIdPtrn=="value_url"){		ret = cmmPtValueUrl(oriServiceIdTmpl, serviceId);
		}else if(oriServiceIdPtrn=="na"){				ret = cmmPtNa(oriServiceIdTmpl, serviceId);
		}else if(oriServiceIdPtrn=="ne"){				ret = cmmPtNe(oriServiceIdTmpl, serviceId);
		}else if(oriServiceIdPtrn=="key_value"){		ret = cmmPtKeyValue(oriServiceIdTmpl, serviceId);
		}else if(oriServiceIdPtrn=="key_static"){		ret = cmmPtKeyStatic(oriServiceIdTmpl, serviceId);
		}else{
		}

	}else{  // equal
		ret = (action.serviceId == oriServiceIdStr)? "0": "1";
	}

	if(ret == "999"){
		return "Key / Value not exist";
	}else if(ret == "0"){
		return "Good";
	}else {
		return ret;
	}
};
// --------------------------------------------------------------- return cdata value
function cmmGetXMLValue(obj){
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

function cmmS4(){
	return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
}
function cmmUuid(){
	return cmmS4()+cmmS4()+'-'+cmmS4()+'-'+cmmS4()+'-'+cmmS4()+'-'+cmmS4()+cmmS4()+cmmS4();
}
