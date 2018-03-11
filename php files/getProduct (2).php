<?php 
	require_once('dbConnect.php');
    
    $id=$_GET['id'];
	$sql = "SELECT * FROM product WHERE id ='$id'";
	mysqli_set_charset($con, 'utf8');

	$r = mysqli_query($con,$sql);

	$result = array();
	
	$row = mysqli_fetch_array($r);
		
	array_push($result,array(
			"id"=>$row['id'],
			"product"=>$row['name'],
			"category"=>$row['category'],
			"price"=>$row['price'],
			"desc"=>$row['description'],
			"image_url"=>$row['image']
		));
	
	
	//Displaying the array in json format 
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>