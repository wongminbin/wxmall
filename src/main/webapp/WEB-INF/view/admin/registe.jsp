<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/admin/common/header.jsp" %>

<div class="registe">
	<form method="post" onsubmit="return registeIn()">
		用户名:<input type="text" placeholder="用户名" id="login_name" name="name"/><br/>
		密码:<input type="password" placeholder="密码" id="login_password" name="password"/><br/>
		<input type="submit" value="注册">
	</form>
</div>


<script type="text/javascript">
var modulus = "${modulus}";
var exponent = "${exponent}";
</script>
<script type="text/javascript" src="${basepath}/static/js/RSAUtil.js"></script>
<script type="text/javascript" src="${basepath}/static/js/admin.js"></script>
<%@include file="/WEB-INF/view/admin/common/footer.jsp" %>