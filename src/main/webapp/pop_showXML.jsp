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
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    
    <title>XML</title>
    <link rel="stylesheet" type="text/css" href="css/cms.css"/>
    
    <script type="text/javascript" src="assets/js/jQuery.js"></script>
</head>

<body style="background-color:white;">
<div>
    <div class="layer-header">
        <h2>LOG XML</h2>
    </div>
    <div class="wiget-gbx">
         <h3>Request Parameters</h3>
         <table class="wiget-table tbl_row">
             <tbody>
                  <tr>
                   <td><pre>${logVO.req}</pre></td>
                  </tr>
                  <tr>
                   <td style="word-wrap:break-word;"><xmp style="white-space:pre-wrap; word-wrap:break-word;">${logVO.xml}</xmp></td>
                  </tr>
             </tbody>
         </table>
     </div>
    <div class="divBttm">
        <button class="btn" onclick="self.close()"><span class="icon-x"></span>창닫기</button>
    </div>
</div>
</body>
</html>