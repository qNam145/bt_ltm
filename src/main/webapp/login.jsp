<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng nhập</title>
</head>
<body>
	<form method="POST" action="CheckLogin">
		<table>
			<caption>Đăng nhập</caption>
			<tr>
				<td><label for="name">Tài khoản:</label></td>
				<td><input type="text" name="username" id="name"></td>
			</tr>
			<tr>
				<td><label for="password">Mật khẩu:</label></td>
				<td><input type="password" name="password" id="password"></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="Đăng nhập">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>