<!-- src/main/webapp/WEB-INF/views/home.jsp -->
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <script type="text/javascript">
        function submitForm() {
            var num1 = document.getElementById("num11").value;
            var num2 = document.getElementById("num22").value;
            var url = "/add/" + num1 + "/" + num2;
            window.location.href = url;
            return false; // Prevent actual form submission
        }
    </script>
</head>
<body>
    <h1>Welcome to the Home Page</h1>

    <form action="/processForm" method="get">
        <label for="num1">Number 1:</label>
        <input type="number" id="num1" name="num1" required>
        <br>
        <label for="num2">Number 2:</label>
        <input type="number" id="num2" name="num2" required>
        <br>
        <input type="submit" value="Add">
    </form>

    <form onsubmit="return submitForm();">
        <label for="num1">Number 1:</label>
        <input type="number" id="num11" name="num1" required>
        <br>
        <label for="num2">Number 2:</label>
        <input type="number" id="num22" name="num2" required>
        <br>
        <input type="submit" value="Add">
    </form>
</body>
</html>
