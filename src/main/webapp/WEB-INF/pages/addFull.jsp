<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Redwerk candidate</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>

<table width="400px" align="center">
    <tr>
        <td><img src="http://redwerk.com/wp-content/themes/redwerk-nestor-child/img/redwerk_logo.png"></td>
        <td><h4><a href="/SpringMVC_war_exploded/index" method="post"><b>MAIN</b></a></h4></td>
        <td><h4><a href="/SpringMVC_war_exploded/addTest" method="post"><b>ADD</b></a></h4></td>
    </tr>
</table>
<hr>
<body>
<div class="container">
    <form role="form" enctype="multipart/form-data" class="form-horizontal" action="/SpringMVC_war_exploded/add" method="post">
        <div align="center"><h3>New candidate:</h3></div>
        <table width="400px" align="center">
            <tr>
                <td><b>First name: </b></td>
                <td class="form-group"><input type="text" class="form-control" name="firstName" placeholder="First name"></td>
            </tr>
            <tr>
                <td><b>Last name: </b></td>
                <td class="form-group"><input type="text" class="form-control" name="lastName" placeholder="Last name"></td>
            </tr>
            <tr>
                <td><b>Interview date: </b></td>
                <td class="form-group"><input type="date" class="form-control" name="interwievDate" placeholder="2015.12.31"></td>
            </tr>
        </table>

        <div align="center"><h3>Contact info:</h3></div>

        <table width="400px" align="center">
            <tr>
                <td><b>tel: </b></td>
                <td class="form-group"><input type="text" class="form-control" name="telephone" placeholder="+38 050 123-45-67"></td>
            </tr>
            <tr>
                <td><b>e-mail: </b></td>
                <td class="form-group"><input type="text" class="form-control" name="mail" placeholder="mail@mail.com"></td>
            </tr>
            <tr>
                <td><b>skype: </b></td>
                <td class="form-group"><input type="text" class="form-control" name="skype" placeholder="skype"></td>
            </tr>
            <tr>
                <td class="form-group"><input type="text" class="form-control" name="other1" placeholder="other type"></td>
                <td class="form-group"><input type="text" class="form-control" name="contact1" placeholder="contact"></td>
            </tr>
            <tr>
                <td class="form-group"><input type="text" class="form-control" name="other2" placeholder="other type"></td>
                <td class="form-group"><input type="text" class="form-control" name="contact2" placeholder="contact"></td>
            </tr>
        </table>

        <div align="center"><h3>Tech skills:</h3></div>

        <table width="400px" align="center">
            <tr>
                <td class="form-group"><input type="text" class="form-control" name="skill_1" placeholder="skill"></td>
                <td class="form-group"><input type="text" min="1" max="10" class="form-control" name="rate1" placeholder="rate"></td>
            </tr>
            <tr>
                <td class="form-group"><input type="text" class="form-control" name="skill_3" placeholder="skill"></td>
                <td class="form-group"><input type="text" min="1" max="10" class="form-control" name="rate2" placeholder="rate"></td>
            </tr>
            <tr>
                <td class="form-group"><input type="text" class="form-control" name="skill_3" placeholder="skill"></td>
                <td class="form-group"><input type="text" min="1" max="10" class="form-control" name="rate3" placeholder="rate"></td>
            </tr>
            <tr>
                <td class="form-group"><input type="text" class="form-control" name="type4" placeholder="skill"></td>
                <td class="form-group"><input type="number" min="1" max="10" class="form-control" name="contact4" placeholder="rate"></td>
            </tr>
            <tr>
                <td class="form-group"><input type="text" class="form-control" name="type5" placeholder="skill"></td>
                <td class="form-group"><input type="number" min="1" max="10" class="form-control" name="contact5" placeholder="rate"></td>
            </tr>
            <tr>
                <td class="form-group"><input type="text" class="form-control" name="type6" placeholder="skill"></td>
                <td class="form-group"><input type="number" min="1" max="10" class="form-control" name="contact6" placeholder="rate"></td>
            </tr>
        </table>
        <hr>
        <div class="form-group" align="center"><input type="submit" class="btn btn-primary" value="Add candidate"></div>
    </form>
</div>
</body>
</html>