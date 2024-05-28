<html>
<body>

<h2>HTML Forms</h2>

<div>${message}</div><br/><br/>

<form action="/login" method="post">
  <label for="username">User name:</label><br>
  <input type="text" id="username" name="username"><br>
  <label for="passwd">Password:</label><br>
  <input type="password" id="passwd" name="passwd"><br>
  <label for="dob">DoB:</label><br>
  <input type="date" id="dob" name="dob"><br><br>   
  <input type="submit" value="Login">
</form> 

<p>If you click the "Login" button, the form-data will be sent to login-servlet.</p>

</body>
</html>