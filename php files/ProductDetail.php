<?php 
	require_once('DbConfig.php');
    
    $id=$_GET['Product'];
	$sql = "SELECT * FROM product WHERE id ='$id'";

	$r = mysqli_query($con,$sql);

	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		array_push($result,array(
			"id"=>$row['id'],
			"product"=>$row['name'],
			"category"=>$row['category'],
			"price"=>$row['price'],
			"desc"=>$row['description'],
			"image_url"=>$row['image']
		));
	}
	
	//Displaying the array in json format 
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>