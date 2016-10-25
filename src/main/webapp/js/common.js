/**
 * Common.js
 * @param dom
 */
function jsonParam(dom){
    var d = $("#"+dom).serialize();
    var obj = $.deparam(d)
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