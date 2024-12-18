<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Index</title>
</head>
<body>
	<h2>Video compress</h2>
	<form method ="POST" action="CompressVideo" enctype="multipart/form-data">
		<input type="file" name="videoFile" required>
		<input type="submit">
	</form>
</body>
</html>