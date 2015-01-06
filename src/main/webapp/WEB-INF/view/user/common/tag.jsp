<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="basepath" value="${request.contextPath}"/>
<c:if test="${basepath == '/'}">
    <c:set var="basepath" value=""/>
</c:if>