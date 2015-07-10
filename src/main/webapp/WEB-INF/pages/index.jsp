<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Interview list</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<table width="80%" align="center">
    <tr>
        <td align="left"><a title="Main page" href="../"><img src="https://upload.wikimedia.org/wikipedia/commons/a/a2/Example_logo.jpg"></a></td>
        <td><form class="form-inline" role="form" action="../search" method="post">
            <input type="text" class="form-control" name="pattern" placeholder="Search">
            <input type="submit" class="btn btn-default" value="Search"></form>
        </td>
        <td align="right"><a title="Create new candidate" href="../add"><img src="http://c.dryicons.com/images/icon_sets/grungy_aesthetica_icons_set/png/128x128/user_add.png" width="64"></a></td>
    </tr>
</table>
<hr>
    <div class="container">
        <p align="center" style="color:rgba(134, 3, 1, 0.73); font-size:15px">${error}</p>
        <table class="table table-striped">
            <thead>
            <tr>
                <td><a title="Sort by name" href="../sortByName"><h3><b>Candidate:</b></h3></a></td>
                <td><a title="Sort by interview date" href="../"><h3><b>Interview date:</b></h3></a></td>
            </tr>
            </thead>
            <c:forEach items="${candidates}" var="candidate">
                <tr>
                    <td><b><a title="To candidate page" href="../candidateInfo?id=${candidate.id}">${candidate.lastName} ${candidate.firstName}</a></b></td>
                    <jsp:useBean id="beanNow" class="java.util.Date" />
                    <fmt:formatDate value="${candidate.interviewDate}" var="interview" pattern="dd.MM.YYYY" />
                    <td>${interview}</td>
                    <td><c:if test="${candidate.skills.size() > 0}">${candidate.getSortedSkills().get(0)}</c:if></td>
                    <td><c:if test="${candidate.skills.size() > 1}">${candidate.skills.get(1)}</c:if></td>
                    <td><c:if test="${candidate.skills.size() > 2}">${candidate.skills.get(2)}</c:if></td>
                    <td><c:if test="${candidate.skills.size() > 3}">${candidate.skills.get(3)}</c:if></td>
                </tr>
            </c:forEach>
        </table>
        <hr>
        <div align="center">
            <ul class="pagination">
                <c:forEach begin="1" end="${pages}" var="i">
                    <c:choose>
                        <c:when test="${page == i}">
                            <li class="active"><a href="../paging?page=${i}">${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="../paging?page=${i}&sort=${sort}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </div>
    </div>
</body>
</html>
