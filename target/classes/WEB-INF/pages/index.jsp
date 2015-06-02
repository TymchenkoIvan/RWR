<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Redwerk interview list</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<table width="80%" align="center">
    <tr>
        <td align="left"><a href="/com_company/"><img src="http://redwerk.com/wp-content/themes/redwerk-nestor-child/img/redwerk_logo.png"></a></td>
        <td align="right"><a href="/com_company/add"><img src="http://c.dryicons.com/images/icon_sets/grungy_aesthetica_icons_set/png/128x128/user_add.png" width="64"></a></td>
    </tr>
</table>
<hr>
    <div class="container">
        <table align="center">
            <tr>
                <td><form class="form-inline" role="form" action="/com_company/search" method="post">
                    <input type="text" class="form-control" name="pattern" placeholder="Search">
                    <input type="submit" class="btn btn-default" value="Search"></form>
                </td>
                <td><h4><a href="/com_company">by date</a></h4></td>
                <td><h4><a href="/com_company/sortByName">by name</a></h4></td>
                <div class="btn-group" role="form" aria-label="...">
                    <button type="button" class="btn btn-default" action="/com_company" method="post">By date</button>
                    <button type="button" class="btn btn-default" action="/com_company/sortByName" method="post">By name</button>
                </div>
            </tr>
        </table>

        <table class="table table-striped">
            <thead>
            <tr>
                <td><b>Candidate</b></td>
                <td><b>Date</b></td>
                <td></td>
                <td><b>Best skills</b></td>
            </tr>
            </thead>
            <c:forEach items="${candidates}" var="candidate">
                <tr>
                    <td><b><a href="/com_company/candidateInfo?id=${candidate.id}">${candidate.lastName} ${candidate.firstName}</a></b></td>
                    <jsp:useBean id="beanNow" class="java.util.Date" />
                    <fmt:formatDate value="${candidate.interviewDate}" var="interview" pattern="dd.MM.YYYY" />
                    <td>${interview}</td>
                    <td><c:if test="${candidate.skills.size() > 0}">${candidate.getSortedSkills().get(0)}</c:if></td>
                    <td><c:if test="${candidate.skills.size() > 1}">${candidate.skills.get(1)}</c:if></td>
                    <td><c:if test="${candidate.skills.size() > 2}">${candidate.skills.get(2)}</c:if></td>
                    <td><a href="/com_company/delete?id=${candidate.id}"><img src="http://s1.iconbird.com/ico/2013/10/464/w24h241380984608delete9.png"></a></td>
                </tr>
            </c:forEach>
        </table>

        <div align="center">
            <ul class="pagination">
                <c:forEach begin="1" end="${pages}" var="i">
                    <c:choose>
                        <c:when test="${page == i}">
                            <li class="active"><a href="/com_company/paging?page=${i}">${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="/com_company/paging?page=${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </div>
    </div>
</body>
</html>
