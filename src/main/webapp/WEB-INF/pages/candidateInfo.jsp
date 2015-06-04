<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Candidate info</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<table width="80%" align="center">
    <tr>
        <td align="left"><a title="Main page" href="/com_company/"><img src="http://redwerk.com/wp-content/themes/redwerk-nestor-child/img/redwerk_logo.png"></a></td>
        <td align="right"><a title="Delete this candidate" href="/com_company/delete?id=${candidate.id}"><img src="http://a.dryicons.com/images/icon_sets/grungy_aesthetica_icons_set/png/128x128/user_remove.png" width="64"></a></td>
    </tr>
</table>
<hr>
 <div class="container">
     <c:set var="candidate" value="${candidate}" scope="page"/>
     <table align="center" class="table table-condensed" style="width: 400px;">
         <thead>
         <tr><th colspan="2"><center><h3><b>Candidate info:</b></h3></center></th></tr>
         </thead>

         <tr>
             <td><b>First name:</b></td>
             <td>${candidate.firstName}</td>
         </tr>
         <tr>
             <td><b>Last name: </b></td>
             <td>${candidate.lastName}</td>
         </tr>
         <tr>
             <td><b>Interview date: </b></td>
             <jsp:useBean id="beanNow" class="java.util.Date" />
             <fmt:formatDate value="${candidate.interviewDate}" var="interview" pattern="dd.MM.YYYY" />
             <td>${interview}</td>
         </tr>

         <tr><th colspan="2"><center><h3><b>Contact info:</b></h3></center></th></tr>

         <c:forEach items="${candidate.getSortedContacts()}" var="contact">
             <tr>
                 <td><b>${contact.description}</b></td>
                 <td>${contact.contact}</td>
             </tr>
         </c:forEach>

         <tr><th colspan="2"><center><h3><b>Tech skills:</b></h3></center></th></tr>

         <c:forEach items="${candidate.getSortedSkills()}" var="skill">
             <tr>
                 <td><b>${skill.description}</b></td>
                 <td>${skill.rate}</td>
             </tr>
         </c:forEach>
     </table>
     <hr>
    </div>
</div>
</body>
</html>
