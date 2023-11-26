<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2023/11/25
  Time: 上午 11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="box">

    <form method="post" action="/users/login" autocomplete="off">

        <h2>登 入</h2>
        <div class="inputBox">
            <input type="text" name="userName" required>
            <span>123</span>
            <i></i>
        </div>
        <div class="inputBox">
            <input type="password" name="password" required>
            <span>密碼</span>
            <i></i>
        </div>
        <div class="links">
            <a href="#">忘記密碼?</a>
            <a href="/users/register">註冊</a>
        </div>
        <div class="boxInput">
            <input type="submit" value="登入">
        </div>
    </form>
</div>
</body>
</html>
