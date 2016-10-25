<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<% String _rootCtx = request.getContextPath(); %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="Expires" content="0"/> 
  <meta http-equiv="Pragma" content="no-cache"/>
  <link rel="stylesheet" href="<%=_rootCtx%>/css/style.css" type="text/css"/>
  <script type="text/javascript" src="<%=_rootCtx%>/assets/js/jQuery.js"></script>
  <script>
  	function Login(){
  		
  		var loginId   = jQuery("#login_id").val();
  		var loginPass = jQuery("#login_pass").val();
  		
  		if(loginId == ""){
  			alert("아이디를 입력해 주세요 ");
  			jQuery("#login_id").focus();
  			return;
  		}
  		
  		if(loginPass == ""){
  			alert("비밀번호를 입력해 주세요 ");
  			jQuery("#login_pass").focus();
  			return;
  		}	
  		
  		
  		var urls = "<%=_rootCtx%>/loginMain.do";
  		$.post(urls, { login_id:loginId, login_pass:loginPass },
  			function(dt) {
  				console.log(dt+":"+result);
  				var result = $.trim(dt);
		    	if(result=="1"){
		    		document.location.href = "<%=_rootCtx%>/casetest.do";
		    	}else{
		    		alert(" 아이디, 비밀번호를 확인해 주세요 ");
		    	}
  			}
  		);

  	}
  </script>
  <title>kt 지능형감성대화서버 Service Test</title>
</head>
<body topmargin=0 leftmargin=0>
  
    <div align=center style='border:none'>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        
        <table border=0 cellspacing=0 cellpadding=0>
         <tr>
          <td width=701 height=396 align=center>
            <br/>
            <br/>
            <img src="<%=_rootCtx%>/images/login_bg.jpg"/>
            <br/>        
            <br/>
			
			<table border="0" cellspacing="0" cellpadding="3">
			 <form name='login_form' method='post' action="testcase/loginMain.do">
			 <tr>
			  <td>아이디 : <input type=text name='login_id' id='login_id' value='testcase' style='width:100px'/></td>
			  <td>비밀번호 : <input type=password name='login_pass' id='login_pass' value='testcase1234' style='width:100px'/></td>
			  <td>
			     <input type=button name="btnLogin" id="btnLogin" value=" 로그인 "  onclick="Login()"/>
			  </td>
			 </tr>
			 </form>
			</table>
			<br/>	
			${errMsg}
		</td>
	   </tr>
	  </table>
	  
		
	</div>
 
  </body>
 </html>