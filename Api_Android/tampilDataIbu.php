<?php
require("config.php");
$perintah = "SELECT * FROM tb_user WHERE level='3' ORDER BY id DESC ";
$eksekusi = mysqli_query($con,$perintah);
$cek = mysqli_affected_rows($con);

if($cek > 0){
    $response["kode"] = 1;
    $response["pesan"]= "Data tersedia";
    $response["data"] = array();
    
    while($ambil = mysqli_fetch_object($eksekusi)){
        $D["id"] = $ambil->id;
        $D["nama"] = $ambil ->nama;
        $D["email"] = $ambil ->email;
        $D["no_telp"] = $ambil ->no_telp;
        
        array_push($response["data"], $D);
    }
}else{
    $response["kode"] = 0;
    $response["pesan"]= "Data tidak tersedia";
}

echo json_encode($response);
mysqli_close($con);

?>