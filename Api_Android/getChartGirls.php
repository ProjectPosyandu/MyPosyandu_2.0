<?php
require "config.php";

$id_bayi = $_POST["id_bayi"];

$query = "SELECT * FROM tb_usia
			JOIN tb_kriteria_bb_pr ON tb_usia.id_usia=tb_kriteria_bb_pr.id_usia
			JOIN tb_detail_bayi ON tb_usia.id_usia=tb_detail_bayi.id_usia WHERE id_bayi = '$id_bayi'";

// $query2 = "SELECT * FROM detail_bayi WHERE id_bayi = '$id_bayi'";

	$hasil = mysqli_query($con,$query);
	if (mysqli_num_rows($hasil)>0) {
		$response = array(); 
		while ($row = mysqli_fetch_assoc($hasil)) {
		 	$tampil['bb'] = $row['berat_bayi'];
		 	$tampil['usia'] = $row['usia'];
		 	$tampil['kurang'] = $row['kurang'];
		 	$tampil['ideal_bawah'] = $row['ideal_bawah'];
		 	$tampil['ideal'] = $row['ideal'];
		 	$tampil['ideal_atas'] = $row['ideal_atas'];
		 	$tampil['lebih'] = $row['lebih'];
	        $response[] = $tampil;
	    }
    echo json_encode(array("data" => $response) );
		
	}

	// $hasil2 = mysqli_query($con,$query2);
	// if (mysqli_num_rows($hasil2)>0) {
	// 	$response2 = array();
	// 	 if(mysqli_num_rows($hasil2) > 0){  
	// 		 while ($row2 = mysqli_fetch_assoc($hasil2)) {
	// 		 	$tampil2['bb'] = $row2['berat_bayi'];
	// 		 	$response2[] = $tampil2;
	// 	    }
	//     echo json_encode(array("data2" => $response2) );
	// 	}
	// }

?>

