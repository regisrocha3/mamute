<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@attribute name="title" type="java.lang.String" required="true"%>
<%@attribute name="description" type="java.lang.String" required="false"%>
<%@attribute name="facebookMetas" type="java.lang.Boolean" required="false"%>

<!DOCTYPE html>
<html lang="${env.get('locale')}">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><c:out value="${title}" escapeXml="true" /></title>

<c:set var="metaDescription" value="${description}" scope="request" />
<c:set var="facebookMetas" value="${facebookMetas}" scope="request" />
<tags:brutal-include value="metas" />

<link rel="stylesheet" href="${contextPath}/css/mamute.9ed6758a.css"/>

<link rel="stylesheet" href="${contextPath}/css/deps/custom.css">

<!--[if lt IE 9]>
	<script src="<c:url value="/js/html5shiv.js"/>"></script>
<![endif]-->

<!--[if lte IE 8]>
	<script type="text/javascript">
		var htmlshim='abbr,article,aside,audio,canvas,details,figcaption,figure,footer,header,mark,meter,nav,output,progress,section,summary,time,video'.split(',');
		var htmlshimtotal=htmlshim.length;
		for(var i=0;i<htmlshimtotal;i++) document.createElement(htmlshim[i]);
	</script>
<![endif]-->

<tags:brutal-include value="headJavascripts" />

<link rel="canonical" href="${currentUrl}"/>
</head>
<body>
	<tags:brutal-include value="bodyTopJavascripts" />

	<tags:brutal-include value="header" />

	<div class="container">
		<tags:messages messagesList="${loginRequiredMessages}" />
		<tags:messages messagesList="${mamuteMessages}" />
		<tags:messages messagesList="${errors}" />