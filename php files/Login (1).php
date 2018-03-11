<?php 
    if($_SERVER['REQUEST_METHOD']=='POST')
	{
		$email = $_POST['email'];
        $password = $_POST['password'];
 

		require_once('DbConfig.php');

		$sql = "SELECT * FROM register WHERE email='$email' AND password='$password'";

        $query=mysqli_query($con, $sql);
		$result = array();
	
	$row = mysqli_fetch_array($query);
	
	array_push($result,array(
			"id"=>$row['id'],
			"name"=>$row['name'],
			"email"=>$row['email'],
			"mobile"=>$row['mobile']
		));

	echo json_encode(array('result'=>$result));
		
		mysqli_close($con);
	}	

	?>