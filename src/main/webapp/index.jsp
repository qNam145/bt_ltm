<%@ page import="java.util.ArrayList,model.bean.video" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Video Compression</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
	<style>
		/* Additional styling for layout fixes */
		.main-container {
			margin: 50px auto;
			max-width: 900px;
			background: #fff;
			border-radius: 10px;
			padding: 20px;
			box-shadow: 0 5px 10px rgba(0, 0, 0, 0.3);
		}

		h2 {
			text-align: center;
			color: #333;
			margin-bottom: 20px;
		}

		form {
			display: flex;
			flex-direction: column;
			align-items: center;
			margin-bottom: 30px;
		}

		form input[type="file"] {
			margin-bottom: 15px;
		}

		form input[type="submit"] {
			color: #fff;
			background: #009579;
			font-size: 1.1rem;
			font-weight: 500;
			padding: 10px 20px;
			border: none;
			border-radius: 5px;
			cursor: pointer;
			transition: 0.4s;
		}

		form input[type="submit"]:hover {
			background: #006653;
		}

		table {
			width: 100%;
			border-collapse: collapse;
			margin-top: 20px;
		}

		table caption {
			font-size: 1.2rem;
			font-weight: bold;
			margin-bottom: 10px;
		}

		table th, table td {
			border: 1px solid #ddd;
			padding: 15px; /* Increase padding for better spacing */
			text-align: center;
			vertical-align: middle; /* Align text to middle for better visuals */
		}

		table th {
			background: #009579;
			color: white;
			font-weight: bold;
			text-transform: uppercase;
		}

		table tr:nth-child(even) {
			background: #f9f9f9;
		}

		table tr:hover {
			background: #f1f1f1;
		}

		/* Fix alignment for long text like descriptions */
		td {
			word-wrap: break-word; /* Ensure text wraps properly */
			max-width: 300px; /* Limit description width */
		}

		/* Download link styling */
		a {
			color: #009579;
			text-decoration: none;
			font-weight: bold;
		}

		a:disabled, a[disabled] {
			color: gray;
			pointer-events: none;
			text-decoration: line-through;
		}
	</style>
</head>
<body>
<%
	// Redirect to login page if not logged in
	if (request.getSession().getAttribute("username") == null) {
		response.sendRedirect(request.getContextPath() + "/auth");
		return;
	}
%>
<div class="main-container">
	<h2>Video Compression</h2>

	<!-- File Upload Form -->
	<form method="POST" action="CompressVideo" enctype="multipart/form-data">
		<input type="file" name="videoFile" accept="video/mp4,video/x-m4v,video/*" required>
		<input type="submit" value="Upload Video">
	</form>

	<%
		ArrayList<video> list = (ArrayList<video>) request.getAttribute("videos");
		if (list == null || list.size() == 0) {
	%>
	<h2>No files found</h2>
	<%
	} else {
	%>
	<!-- Video Status Table -->
	<table>
		<caption>View All File Status</caption>
		<thead>
		<tr>
			<th>Name</th>
			<th>Complete</th>
			<th>Download</th>
			<th>File Size(KB)</th>
			<th>Description</th>
		</tr>
		</thead>
		<tbody>
		<% for (video v : list) { %>
		<tr>
			<td><%= v.getName() %></td>
			<td><%= v.getIsDone() ? "Yes" : "No" %></td>
			<td>
				<a href="<%= v.getFileLocation() %>" <%= v.getIsDone() ? "" : "disabled" %>>Download</a>
			</td>
			<td><%= v.getFilesize()/1024 %></td>
			<td><%= v.getDesc() %></td>
		</tr>
		<% } %>
		</tbody>
	</table>
	<% } %>
</div>
</body>
</html>
