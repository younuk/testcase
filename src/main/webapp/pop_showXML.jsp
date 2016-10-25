<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");
        response.setHeader("pragma","no-cache");
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="euc-kr">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script type="text/javascript" src="assets/js/jQuery.js"></script>    
    <title>XML</title>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>    	
</head>	

<body>

	<div class="mainTitleBand">
		LOG XML
	</div>
	 
    <table>
     <tr>
      <td class="td_header">Request Parameters</td>
     </tr>
     <tr>
      <td class="td_left" style="word-wrap:break-word; width:750px;">
      	<pre>${logVO.req}</pre>
      </td>
     </tr>
    </table>	
    
    <br/>
    <table>
     <tr>
      <td class="td_header">Result XML</td>
     </tr>
     <tr>
      <td class="td_left"  style="word-wrap:break-word; width:750px;">
        <xmp>${logVO.xml}</xmp>
      </td>
     </tr>
    </table>

    <br/>
	<table width=100%>
	 <tr>
	  <td align=center>
			<input type=button name="btnOK" id="btnOK" value=" 창닫기 " onclick="self.close()" style="width:100px"/>
	  </td>
	 </tr>
	</table>
	
 
</body>
</html>