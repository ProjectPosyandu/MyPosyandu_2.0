<?php
 
    require_once 'include/DB_Functions.php';
    $db = new DB_Functions();

// json response array
$response = array("error" => FALSE);
 
if ($_SERVER['REQUEST_METHOD']=='POST') {

    $id_jadwal =$_POST['id_jadwal'];

    // buat user baru
    $user = $db->updateJadwal($id_jadwal);
    if ($user) {
        // simpan user berhasil
        $response["error"] = FALSE;
        $response["uid"] = $user["id_jadwal"];
        $response["user"]["id"] = $user["id_jadwal"];
        echo json_encode($response);
    } else {
        // gagal menyimpan user
        $response["error"] = TRUE;
        $response["error_msg"] = "Daftar Gagal. Ada kesalahan";
        echo json_encode($response);
    }
    
} else {    
    $response["error"] = TRUE;
    $response["error_msg"] = "File belum dipilih atau beberapa field belum diisi";
    echo json_encode($response);
}
?>