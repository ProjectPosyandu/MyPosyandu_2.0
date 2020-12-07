<?php
require "config.php";

$query = "SELECT * FROM tb_user WHERE level = '3' ";
			
	$hasil = mysqli_query($con,$query);
	$response = array();
	 if(mysqli_num_rows($hasil) > 0){  
		 while ($row = mysqli_fetch_assoc($hasil)) {
		 	$tampil['no_telp'] = $row['no_telp'];
	        $response[] = $tampil;
	    }
    echo json_encode(array("data" => $response) );
	}

?>