<?php

//insert_chat.php
//session_start();
include('database_connection.php');
session_start();
$message=$_POST['chat_message'];
$mesarray=explode(" ", $message);
$message=join("$$",$mesarray);
$sql="SELECT key2 from login where user_id='".$_SESSION['user_id']."'";
//$res=mysqli_query($db,$sql);
$key2="";
$result = $db->query($sql);
if ($result->num_rows ==1 ) {
    while($row = $result->fetch_assoc()){
	 $key2=$row['key2'];
	}
}
/*$sql="SELECT key2 from login where user_id=:user_id";
$res=$connect->prepare($sql);
$res->bindParam(':user_id',$_SESSION['user_id'],PDO::PARAM_STR);
$result=$res->execute();
if($result){
	echo "<script>console.log('executed')</script>";
	echo "<script>console.log('".$_SESSION['user_id']."')</script>";
	echo "<script>console.log('".$result."')</script>";
}
//echo  "<script>console.log(". $result. ")</script>";
//echo  "<script>console.log(". $message. ")</script>";
$key2=$result;*/
//echo  "<script>console.log(".$key2.");</script>";
$output = shell_exec('java Blowfish '.$message." ".$key2);
if($output){
echo "<script>console.log('Yes we did it!')</script>";
//$query=mysqli_query($connect,)
$data = array(
	':to_user_id'		=>	$_POST['to_user_id'],
	':from_user_id'		=>	$_SESSION['user_id'],
	':chat_message'		=>	$message,
	':status'			=>	'1'
);

$query = "
INSERT INTO chat_message (to_user_id, from_user_id, chat_message, status) 
VALUES (:to_user_id, :from_user_id, :chat_message, :status)
";

$statement = $connect->prepare($query);
if($statement->execute($data))
{
	echo fetch_user_chat_history($_SESSION['user_id'], $_POST['to_user_id'], $connect);
}
}
else
echo "<script>console.log('Something went wrong!')</script>"
?>