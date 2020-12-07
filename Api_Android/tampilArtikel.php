<?php
require("config.php");
$perintah = "SELECT * FROM tb_artikel";
$eksekusi = mysqli_query($con,$perintah);
$cek = mysqli_affected_rows($con);

if($cek > 0){
    $response["kode"] = 1;
    $response["pesan"]= "Data tersedia";
    $response["data"] = array();
    
    while($ambil = mysqli_fetch_object($eksekusi)){
        $D["id_artikel"] = $ambil->id_artikel;
        $D["judul_artikel"] = $ambil ->judul_artikel;
        $D["isi_artikel"] = $ambil ->isi_artikel;
        $D["penulis"] = $ambil ->penulis;
        
        array_push($response["data"], $D);
    }
}else{
    $response["kode"] = 0;
    $response["pesan"]= "Data tidak tersedia";
}

echo json_encode($response);
mysqli_close($con);

?>