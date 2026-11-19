<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Patient</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body class="container mt-5">

<h2 class="mb-4 text-center text-primary">Search Patient</h2>

<form action="SearchPatientServlet" method="get" class="d-flex justify-content-center" autocomplete="off">
    <input type="text" name="query" id="query" class="form-control w-50"
           placeholder="Type patient name..." list="suggestions" required>
    <datalist id="suggestions"></datalist>
    <button type="submit" class="btn btn-primary ms-2">Search</button>
</form>

<script>
$(document).ready(function(){
    $("#query").on("input", function(){
        var input = $(this).val();
        if(input.length > 0){
            $.ajax({
                url: "SearchPatientServlet",
                type: "POST",
                data: { query: input },
                success: function(data){
                    $("#suggestions").html(data);
                }
            });
        }
    });
});
</script>

</body>
</html>
