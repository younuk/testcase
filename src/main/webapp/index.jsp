<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<% String _rootCtx = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="Expires" content="0"/>
  <meta http-equiv="Pragma" content="no-cache"/>

  <link rel="stylesheet" href="<%=_rootCtx%>/css/cms.css" type="text/css"/>
  <link rel="stylesheet" href="<%=_rootCtx%>/css/login.css" type="text/css"/>
  <script type="text/javascript" src="<%=_rootCtx%>/assets/js/jQuery.js"></script>
  <script>
    function Login(){
        var loginId   = jQuery("#login_id").val();
        var loginPass = jQuery("#login_pass").val();

        if(loginId == ""){
            alert("아이디를 입력해 주세요 ");
            jQuery("#login_id").focus();
            return false;
        }

        if(loginPass == ""){
            alert("비밀번호를 입력해 주세요 ");
            jQuery("#login_pass").focus();
            return false;
        }

        var urls = "<%=_rootCtx%>/loginMain.do";
        $.post(urls, { login_id:loginId, login_pass:loginPass },
            function(dt) {
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

<body>
<div id="login">
    <h1>Login</h1>
    <div id="login_panel">
        <form name="login_form" method="post" >
            <div class="login_fields">
                <div class="field">
                    <label for="adminId">아이디</label>
                    <input type="text" name="login_id" id="login_id" tabindex="1" placeholder="아이디" value="testcase"/>
                </div>

                <div class="field">
                    <label for="adminPassword">비밀번호</label>
                    <input type="password" name="login_pass" id="login_pass" tabindex="2" placeholder="비밀번호" value="testcase1234"/>
                </div>
            </div> <!-- .login_fields -->

            <div class="login_actions" style="text-align:center;">
                <button type="button" class="btn btn-primary" tabindex="3" onclick="Login()">로그인</button>
                <!-- <span class="signup"><a href="adminSignup.html" class="btn btn-small" tabindex="4"><span class="icon-plus"></span>관리자 등록</a></span> -->
            </div>
        </form>
    </div> <!-- #login_panel -->

    <div class="noti">
        <ul class="bullet bullet-blue">
            <li>로그인 후 10분 동안 Admin 사이트 활동이 없을 경우 자동 로그아웃 됩니다.</li>
        </ul>
    </div>
</div> <!-- #login -->
</body>
 </html>