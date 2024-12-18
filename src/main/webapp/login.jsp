<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Auth</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="container">
    <input type="checkbox" id="check">
    <div class="login form">
        <header>Login</header>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <h4 name="error" style="color: red; text-align: center"><%= request.getAttribute("error")%></h4>
            <br>
            <input type="text" placeholder="Enter your username" name="username">
            <input type="password" placeholder="Enter your password" name="password">
            <input type="submit" class="button" value="Login">
        </form>
        <div class="signup">
        <span class="signup">Don't have an account?
         <label for="check">Signup</label>
        </span>
        </div>
    </div>
    <div class="registration form">
        <header>Signup</header>
        <form action="${pageContext.request.contextPath}/register" method="post" id="register-form">
            <input type="text" placeholder="Enter your username" name="username">
            <input type="password" placeholder="Create a password" name="reg-password">
            <input type="password" placeholder="Confirm your password" name="confirm-password">
            <input type="submit" class="button" value="Signup">
        </form>
        <div class="signup">
        <span class="signup">Already have an account?
         <label for="check">Login</label>
        </span>
        </div>
    </div>
</div>
</body>
<script>
    document.querySelector('#register-form').addEventListener('submit', function(event) {
        var password = document.querySelector('input[name="reg-password"]').value;
        var confirmPassword = document.querySelector('input[name="confirm-password"]').value;
        if (password !== confirmPassword) {
            event.preventDefault();
            alert('Passwords do not match.' + password + ' ' + confirmPassword);
        }
    });
</script>
</html>
