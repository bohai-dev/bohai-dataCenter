<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.activiti.engine.form.FormProperty"%>
<%@page import="org.apache.commons.lang.ObjectUtils"%>
<%@page import="org.activiti.engine.form.FormType"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
    <form action="" method="post">

        <c:forEach items="${startFormData.formProperties}" var="fp">
            <c:set var="fpo" value="${fp}" />
            <%  FormType type = ((FormProperty)pageContext.getAttribute("fpo")).getType();
                String[] keys = {"datePattern"};
                for(String key : keys){
                    pageContext.setAttribute(key, ObjectUtils.toString(type.getInformation(key)));
                }  %>
            
            <c:if test="${fp.type.name == 'string' || fp.type.name == 'long'}">
                <label for="${fp.id}">${fp.name}:</label>
                <input type="text" id="${fp.id}" name="${fp.id}" />
            </c:if>
            <c:if test="${fp.type.name == 'date'} ">
                <label for="${fp.id}">${fp.name}:</label>
                <input type="text" id="${fp.id}" name="${fp.id}" class="datepicker"
                data-date-format="${fn:toLowerCase(datePattern)}">
            </c:if>
        </c:forEach>
        <button type="submit" class="btn" ><i class="icon-play"></i>发起流程</button>
    </form>


</div>