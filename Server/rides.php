<?php
/*
Our "config.inc.php" file connects to database every time we include or require
it within a php script. 
*/
require("config.inc.php");

//initial query
$query = "Select * FROM ERTable WHERE Type = 2";

//execute query
try {
    $stmt   = $db->prepare($query);
    $result = $stmt->execute($query_params);
}

catch (PDOException $ex) {
    $response["success"] = 0;
    $response["message"] = "Database Error!";
    die(json_encode($response));
}

// Finally, we can retrieve all of the found rows into an array using fetchAll
$rows = $stmt->fetchAll();

if ($rows) {
    $response["success"] = 1;
    $response["message"] = "Post Available!";
    $response["events"]   = array();
    
    foreach ($rows as $row) {
    //Title,Type,Date,Hour,Time, LocNumb, Street, City, ZipCode, State, Description
        $post             	= array();
        $post["Title"] 		= $row["Title"];
        $post["Type"]    	= $row["Type"];
        $post["Date"]    	= $row["Date"];
        $post["Hour"]    	= $row["Hour"];
        $post["Time"]    	= $row["Time"];
        $post["LocNumb"]    = $row["LocNumb"];
        $post["Street"]    	= $row["Street"];
        $post["City"]   	= $row["City"];
        $post["ZipCode"]    = $row["ZipCode"];
        $post["State"]    	= $row["State"];
        $post["Description"]  = $row["Description"];
        
        //update our repsonse JSON data
        array_push($response["events"], $post);
    }
    // echoing JSON response
    echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "No Post Available!";
    die(json_encode($response));
}
?>
