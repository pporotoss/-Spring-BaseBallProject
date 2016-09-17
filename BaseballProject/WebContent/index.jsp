<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="toDay" class="java.util.Date"/>
<fmt:formatDate value="${toDay }" pattern="yyyyMMdd" var="day"/>
<jsp:forward page="/view/team/result/${day }" />