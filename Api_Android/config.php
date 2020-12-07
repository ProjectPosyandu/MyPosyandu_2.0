<?php
$host = "localhost";
$user = "root";
$password = "";
$db = "latihan1";

define("DB_HOST", "localhost");
define("DB_USER", "root");
define("DB_PASSWORD", "");
define("DB_DATABASE", "latihan1");


$con = mysqli_connect($host,$user,$password,$db);

if(mysqli_connect_errno()){
	echo "Failed to connect".mysqli_connect_error();
}else{
	// echo "connect";
}
?>