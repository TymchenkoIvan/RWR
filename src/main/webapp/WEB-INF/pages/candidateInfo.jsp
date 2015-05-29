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
<table width="400px" align="center">
    <tr>
        <td><img src="http://redwerk.com/wp-content/themes/redwerk-nestor-child/img/redwerk_logo.png"></td>
        <td><h4><a href="/com_company/"><b>MAIN</b></a></h4></td>
        <td><h4><a href="/com_company/add"><b>ADD</b></a></h4></td>
    </tr>
</table>
<hr>
 <div class="container">
     <c:set var="candidate" value="${candidate}" scope="page"/>
     <table align="center" class="table table-condensed" style="width: 400px;">
         <thead><tr><th colspan="2"><center><h3>Candidate info:</h3></center></th></tr></thead>

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

         <tr><th colspan="2"><center><h3>Contact info:</h3></center></th></tr>

         <c:forEach items="${candidate.getSortedContacts()}" var="contact">
             <tr>
                 <td><b>${contact.description}</b></td>
                 <td>${contact.contact}</td>
             </tr>
         </c:forEach>

         <tr><th colspan="2"><center><h3>Tech skills:</h3></center></th></tr>

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
