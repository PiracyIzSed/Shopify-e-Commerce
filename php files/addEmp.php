<?php 
	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Getting values
		$name = $_POST['name'];
		$pass = $_POST['pass'];
		$email = $_POST['email'];
		$phone = $_POST['phone'];
		$address = $_POST['address'];

		
		//Creating an sql query
		$sql = "INSERT INTO account_details (name,pass,email,phone,address) VALUES ('$name','$pass','$email','$phone','$address')";
		
		//Importing our db connection script
		require_once('dbConnect.php');
		
		//Executing query to database
		if(mysqli_query($con,$sql)){
			echo 'Account Added Successfully';
		}else{
			echo 'Could Not Add Account';
		}
		
		//Closing the database 
		mysqli_close($con);
	}
	?>