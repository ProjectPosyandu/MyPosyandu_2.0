<?php
require("config.php");
$id = $_POST['id'];
$perintah = "SELECT * FROM tb_bayi WHERE id = '$id' ORDER BY id_bayi DESC";
$eksekusi = mysqli_query($con,$perintah);
$cek = mysqli_affected_rows($con);

if($cek > 0){
    $response["kode"] = 1;
    $response["pesan"]= "Data tersedia";
    $response["data"] = array();
    
    while($ambil = mysqli_fetch_object($eksekusi)){
        $D["id_bayi"] = $ambil->id_bayi;
        $D["nama_bayi"] = $ambil ->nama_bayi;
        $D["jenis_kelamin"] = $ambil ->jenis_kelamin;
        $D["tgl_lahir"] = $ambil ->tgl_lahir;
        $D["foto_bayi"] = $ambil ->foto_bayi;
        $D["id"] = $ambil ->id;
        
        array_push($response["data"], $D);
    }
}else{
    $response["kode"] = 0;
    $response["pesan"]= "Data tidak tersedia";
}

echo json_encode($response);
mysqli_close($con);

?>