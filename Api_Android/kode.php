<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
// json response array
$response = array("error" => FALSE);

$kode_x = $db->createIdBayi();
// $nourut = substr($kode_x["idbayi"], 3, 4);
// $kode = "BY".($nourut + 1);

echo $kode_x;

?>