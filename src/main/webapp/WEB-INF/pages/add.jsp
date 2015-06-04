<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Redwerk candidate</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>

<table width="80%" align="center">
    <tr>
        <td align="left"><a title="Main page" href="/com_company/"><img src="http://redwerk.com/wp-content/themes/redwerk-nestor-child/img/redwerk_logo.png"></a></td>
    </tr>
</table>
<hr>
<body>
    <div class="container">
        <form role="form"  class="form-horizontal" action="/com_company/addCandidate" method="post">
            <table width="400px" align="center">
                <thead><tr><th colspan="2"><center><h3><b>New candidate:</b></h3></center></th></tr></thead>
                <tr>
                    <td><b>First name:* </b></td>
                    <td class="form-group"><input type="text" class="form-control" name="firstName" required placeholder="First name"></td>
                </tr>
                <tr>
                    <td><b>Last name:* </b></td>
                    <td class="form-group"><input type="text" class="form-control" name="lastName" required placeholder="Last name"></td>
                </tr>
                <tr>
                    <td><b>Interview date:* </b></td>
                    <td class="form-group"><input type="date" class="form-control" name="interviewDate" required placeholder="2015.12.31"></td>
                </tr>

                <tr><th colspan="2"><center><h3><b>Contact info:</b></h3></center></th></tr>

                <tr>
                    <td><b>tel:* </b></td>
                    <td class="form-group"><input type="text" class="form-control" name="telephone" required placeholder="+38 050 123-45-67"></td>
                </tr>
                <tr>
                    <td><b>e-mail:* </b></td>
                    <td class="form-group"><input type="email" class="form-control" name="mail" required placeholder="mail@mail.com"></td>
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

                <tr><th colspan="2"><center><h3><b>Tech skills:</b></h3></center></th></tr>

                <tr>
                    <td class="form-group"><input type="text" class="form-control" name="skill_1" placeholder="skill"></td>
                    <td class="form-group"><input type="number" min="1" max="10" class="form-control" name="rate1" placeholder="rate 1-10"></td></tr>
                <tr>
                    <td class="form-group"><input type="text" class="form-control" name="skill_2" placeholder="skill"></td>
                    <td class="form-group"><input type="number" min="1" max="10" class="form-control" name="rate2" placeholder="rate 1-10"></td>
                </tr>
                <tr>
                    <td class="form-group"><input type="text" class="form-control" name="skill_3" placeholder="skill"></td>
                    <td class="form-group"><input type="number" min="1" max="10" class="form-control" name="rate3" placeholder="rate 1-10"></td>
                </tr>
                <tr>
                    <td class="form-group"><input type="text" class="form-control" name="skill_4" placeholder="skill"></td>
                    <td class="form-group"><input type="number" min="1" max="10" class="form-control" name="rate4" placeholder="rate 1-10"></td>
                </tr>
                <tr>
                    <td class="form-group"><input type="text" class="form-control" name="skill_5" placeholder="skill"></td>
                    <td class="form-group"><input type="number" min="1" max="10" class="form-control" name="rate5" placeholder="rate 1-10"></td></tr>
                <tr>
                    <td class="form-group"><input type="text" class="form-control" name="skill_6" placeholder="skill"></td>
                    <td class="form-group"><input type="number" min="1" max="10" class="form-control" name="rate6" placeholder="rate 1-10"></td>
                </tr>
                <tr>
                    <td class="form-group"><input type="text" class="form-control" name="skill_7" placeholder="skill"></td>
                    <td class="form-group"><input type="number" min="1" max="10" class="form-control" name="rate7" placeholder="rate 1-10"></td>
                </tr>
                <tr>
                    <td class="form-group"><input type="text" class="form-control" name="skill_8" placeholder="skill"></td>
                    <td class="form-group"><input type="number" min="1" max="10" class="form-control" name="rate8" placeholder="rate 1-10"></td>
                </tr>
                <tr>
                    <td class="form-group"><input type="text" class="form-control" name="skill_9" placeholder="skill"></td>
                    <td class="form-group"><input type="number" min="1" max="10" class="form-control" name="rate9" placeholder="rate 1-10"></td></tr>
                <tr>
                    <td class="form-group"><input type="text" class="form-control" name="skill_10" placeholder="skill"></td>
                    <td class="form-group"><input type="number" min="1" max="10" class="form-control" name="rate10" placeholder="rate 1-10"></td>
                </tr>
            </table>
            <hr>
            <div class="form-group" align="center"><input type="submit" class="btn btn-primary" value="Submit"></div>
        </form>
    </div>
</body>
</html>