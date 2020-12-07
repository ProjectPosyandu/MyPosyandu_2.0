<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['id'])) {
    
    $id = $_POST['id'];
    // menerima parameter POST ( email dan password )
    // get the user by email and password
    // get user berdasarkan email dan password
    $user = $db->getDataPerIbu($id);
 
    if ($user != false) {
        // user ditemukan
        $response["error"] = FALSE;
        // $response["uid"] = $user["id_user"];
        $response["user"]["id"] = $user["id"];
        $response["user"]["nama"] = $user["nama"];
        $response["user"]["email"] = $user["email"];
        $response["user"]["no_telp"] = $user["no_telp"];
        echo json_encode($response);
    } else {
        // user tidak ditemukan password/email salah
        $response["error"] = TRUE;
        $response["error_msg"] = "Gagal ambil data";
        echo json_encode($response);
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Parameter ada yang kurang";
    echo json_encode($response);
}
?>