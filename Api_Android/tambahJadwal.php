<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
// json response array
$response = array("error" => FALSE);

if (isset($_POST['nama_jadwal']) && isset($_POST['tanggal']) && isset($_POST['waktu']) ) {
    // menerima parameter POST 
    $nama_jadwal = $_POST['nama_jadwal'];
    $tanggal = $_POST['tanggal'];
    $waktu = $_POST['waktu'];
    $id_jadwal = $db->createIdJadwal();
    // buat user baru
    $user = $db->simpanJadwal($id_jadwal,$nama_jadwal,$tanggal,$waktu);

    if ($user != false) {
        $response["error"] = FALSE;
        $response["uid"] = $user["id_jadwal"];
        $response["user"]["id_bayi"] = $user["id_jadwal"];
        echo json_encode($response);  
    } else {
        // gagal menyimpan user
        $response["error"] = TRUE;
        $response["error_msg"] = "Terjadi kesalahan saat input";
        echo json_encode($response);
    }
}

?>