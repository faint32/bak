<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>用户登录</title>
<link href="images/login.css" rel="stylesheet" type="text/css" />
<script>
function doSub()
{
     form1.submit()
}
</script>
</head>
<body>
<form action="infoAction.do?method=checkLogin" method="post" name="form1" id="form1" >

    <div id="login">
	
	     <div id="top">
		      <div id="top_left"><img src="images/login_03.gif" /></div>
			  <div id="top_center"></div>
		 </div>
		 
		 <div id="center">
		      <div id="center_left"></div>
			  <div id="center_middle">
			       <div id="user">用 户
				   
			         <input name="username" type="text" id="username" />
			       </div>
				   <div id="password">密   码
				     <input type="password" name="password" />
				   </div>
				   <div id="btn"><a href="javascript:doSub();">登录</a><a href="form1.reset">清空</a></div>
			 
			  </div>
			  <div id="center_right"></div>		 
		 </div>
		 <div id="down">
		      <div id="down_left">
			      <div id="inf">
                       <span class="inf_text">Email:</span>
					   <span class="copyright">走自己的路，让别人跟着我走</span>
			      </div>
			  </div>
			  <div id="down_center"></div>		 
		 </div>

	</div>
	 </form>
</body>
</html>
