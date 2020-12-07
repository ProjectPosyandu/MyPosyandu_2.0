<?php
require("config.php");
$status = $_POST['status'];
$perintah = "SELECT * FROM tb_jadwal WHERE status = '$status' ";
$eksekusi = mysqli_query($con,$perintah);
$cek = mysqli_affected_rows($con);

if($cek > 0){
    $response["kode"] = 1;
    $response["pesan"]= "Data tersedia";
    $response["data"] = array();
    
    while($ambil = mysqli_fetch_object($eksekusi)){
        $D["id_jadwal"] = $ambil->id_jadwal;
        $D["nama_imunisasi"] = $ambil ->nama_imunisasi;
        $D["tgl_imunisasi"] = $ambil ->tgl_imunisasi;
        $D["waktu"] = $ambil ->waktu;
        $D["status"] = $ambil->status;
        array_push($response["data"], $D);
    }
}else{
    $response["kode"] = 0;
    $response["pesan"]= "Data tidak tersedia";
}

echo json_encode($response);
mysqli_close($con);

?>