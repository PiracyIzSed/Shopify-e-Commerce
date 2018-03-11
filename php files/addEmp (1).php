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
		
	    $stmt = mysqli_prepare($con,$sql);
	
	   if(mysqli_stmt_execute($stmt)){
				echo "Registered Successfully";
		}else{
			echo "Error in Registration";
		}
		
		//Closing the database 
		mysqli_close($con);
	}
	?>