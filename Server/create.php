<?php

/*
Our "config.inc.php" file connects to database every time we include or require
it within a php script.  
*/
require("config.inc.php");

//if posted data is not empty
if (!empty($_POST)) {

//create a new event and submit it to the ERTable.
    $query = "INSERT INTO ERTable ( Title, Type, Date, Hour, Time, LocNumb, Street, City, ZipCode, State, Description ) VALUES ( :Title, :Type, :Date, :Hour, :Time, :LocNumb, :Street, :City, :ZipCode, :State, :Description ) ";
    
    //Again, we need to update our tokens with the actual data:
    $query_params = array(
        ':Title' => $_POST['Title'],
        ':Type' => $_POST['Type'],
        ':Date' => $_POST['Date'],
        ':Hour' => $_POST['Hour'],
        ':Time' => $_POST['Time'],
        ':LocNumb' => $_POST['LocNumb'],
        ':Street' => $_POST['Street'],
        ':City' => $_POST['City'],
        ':ZipCode' => $_POST['ZipCode'],
        ':State' => $_POST['State'],
        ':Description' => $_POST['Description'],

    );
    
    //time to run our query, and create the event
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {

        die("Failed to run query: " . $ex->getMessage());

		$response["5"] = 5;
		$response["message"] = "Database Error. Please Try Again!";
		die(json_encode($response));
    }

	//successfully adding an event or ride
	$response["0"] = 0;
	$response["message"] = "Added!";
	echo json_encode($response);
	
	
    
    
} else {
?>
	
	<?php
}

?>