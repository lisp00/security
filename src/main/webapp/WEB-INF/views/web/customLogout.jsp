<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Logout Page</h1>
	
	<p>principal : <sec:authentication property="principal"/></p>
	<p>MemberVO : <sec:authentication property="principal.member"/></p>
	<p>username : <sec:authentication property="principal.username"/></p>
	<p>userid : <sec:authentication property="principal.member.userid"/></p>
	<p>name : <sec:authentication property="principal.member.name"/></p>
	<p>password : <sec:authentication property="principal.member.password"/></p>
	<p>code : <sec:authentication property="principal.member.agencyCode"/></p>
	<p>service : <sec:authentication property="principal.member.service"/></p>
	<p>address: <sec:authentication property="principal.member.address"/></p>
	<p>addressDetail : <sec:authentication property="principal.member.addressDetail"/></p>
	<p>enabled : <sec:authentication property="principal.member.enabled"/></p>
	<p>authorization: <sec:authentication property="principal.member.authorization"/></p>
	<p>auth : <sec:authentication property="principal.member.authList"/></p>	

	<!-- POST 방식으로 동작을 하면 CSRF 토큰을 전달 / security_context에서 토큰 만료시킴. 로그인으로 redirect-->
	<form method="post" action="/web/customLogout">
		<input type='hidden' name='${_csrf.parameterName }' value='${_csrf.token }'>
		<button>로그아웃</button>
	</form>
</body>
</html>