<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
// json response array
$response = array("error" => FALSE);

if (isset($_POST['judul']) && isset($_POST['isi']) && isset($_POST['penulis']) ) {
    // menerima parameter POST 
    $judul = $_POST['judul'];
    $isi = $_POST['isi'];
    $penulis = $_POST['penulis'];
    $id_artikel = $db->createIdArtikel();
    // buat user baru
    $user = $db->simpanArtikel($id_artikel,$judul,$isi,$penulis);

    if ($user != false) {
        $response["error"] = FALSE;
        $response["uid"] = $user["id_artikel"];
        $response["user"]["id_artikel"] = $user["id_artikel"];
        echo json_encode($response);  
    } else {
        // gagal menyimpan user
        $response["error"] = TRUE;
        $response["error_msg"] = "Terjadi kesalahan saat input";
        echo json_encode($response);
    }
}

?>