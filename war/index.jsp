<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory;" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>USE it before become broke!</title>

<style type="text/css">
<!--
body {
	font-family: arial, sans-serif;
	background-color: #fff;
	margin-top: 30px;
}

image {
	vertical-align: middle;
}

td {
	font-family: arial, sans-serif;
}

.c {
	width: 4;
	height: 4;
}

a:link {
	color: #00c;
}

a:visited {
	color: #551a8b;
}

a:active {
	color: #f00;
}

.form-noindent {
	background-color: #fff;
	border: 1px solid #c3d9ff;
}

.integration-text  {
	font-style: italic;
	vertical-align: middle;
	font-size: small;
	text-align: justify;
}

.access-text {
	text-align: center;
	font-size: xx-large;
	color: blue;
	font-weight: bold;
}
-->
</style>

</head>
<body>


<fmt:setLocale value="<%= request.getLocale() %>" />

<fmt:bundle basename="com.u3b.server.i18n.welcomeMessages">

<table width="95%" align="center">
	<tr>
		<td width="70%">

		<div>

			<h2><fmt:message key="fstMessage" /></h2>

			<p><fmt:message key="sndMessage" /></p>

		</div>
			
		<table>
			<tr>
				<td><img src="img/gtalk-with-dialog.png" align="middle" /></td>
				<td class="integration-text">
					<fmt:message key="gtalkMessage" /><br/> <br />
					<fmt:message key="gtalkParameters" />
				</td>
			</tr>
		</table>
		
		</td>

		<td width="30%">

		<div>

		<table width="100%" cellspacing="3" cellpadding="6" border="0"
			class="form-noindent">
			<tbody>
				<tr>
					<td bgcolor="#e8eefa" align="center" style="font-size: 83%;">
					<p><fmt:message key="tryIt" /></p>
					<br />
					<br />
					<p class="access-text"><a href=" <%= UserServiceFactory.getUserService().createLoginURL("/LoginServlet") %> "><fmt:message key="access" /></a></p>
					<br />
					<p><fmt:message key="accessBy" /></p>
					<p><img src="img/google_transparent.gif" align="middle" /> Account</p>

					</td>
				</tr>
			</tbody>
		</table>

		<br />
		<br />

		<table width="100%" cellspacing="3" cellpadding="6" border="0"
			class="form-noindent">
			<tbody>
				<tr>
					<td bgcolor="#e8eefa" align="center" style="font-size: 83%;">
					<b><fmt:message key="haveAcct" /></b><br>
					<a
						href="https://www.google.com/accounts/NewAccount?continue=http%3A%2F%2Fcode.google.com%2Fwebtoolkit%2Fexamples%2F&amp;followup=http%3A%2F%2Fcode.google.com%2Fwebtoolkit%2Fexamples%2F">
					<b><fmt:message key="createAcct" /></b> </a></td>
				</tr>
			</tbody>
		</table>
		</div>

		</td>

	</tr>
</table>

</fmt:bundle>
</body>
</html>