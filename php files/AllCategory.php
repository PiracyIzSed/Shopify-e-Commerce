<?php 
	require_once('DbConfig.php');

	$sql = "SELECT * FROM category";

	$r = mysqli_query($con,$sql);

	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		array_push($result,array(
			"id"=>$row['id'],
			"category"=>$row['category'],
			"image_url"=>$row['image_url']
		));
	}
	
	//Displaying the array in json format 
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>