<?php

/*
Our "config.inc.php" file connects to database every time we include or require
it within a php script.  
*/
require("config.inc.php");

//if posted data is not empty
if (!empty($_POST)) {
    //If the Username or Password is empty when the Username submits
    //the form, the page will die.
     
    if (empty($_POST['Username']) || empty($_POST['Password'])) {
		
	
	    // Create some data that will be the JSON response 
	    $response["success"] = 0;
	    $response["message"] = "Please Enter Both a Username and Password.";

		//die will kill the page and not execute any code below
		
        die(json_encode($response));
    }
    
    //if the page hasn't died, we will check with our database to see if there is
    //already a Username with the Username specificed in the form.  ":Username" is just
    //a blank variable that we will change before we execute the query.  We
    //do it this way to increase security, and defend against sql injections
    $query        = " SELECT 1 FROM AccountsTable WHERE Username = :Username";
    //now lets update what :Username should be
    $query_params = array(
        ':Username' => $_POST['Username']
    );
    
    //Now let's make run the query:
    try {
        // These two statements run the query against your database table. 
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
     
        die("Failed to run query: " . $ex->getMessage());

		$response["success"] = 0;
		$response["message"] = "Database Error. Please Try Again!";
		die(json_encode($response));
    }
    
    //fetch is an array of returned data.  
    $row = $stmt->fetch();
    if ($row) {
        die("This Username is already in use");

		$response["success"] = 0;
		$response["message"] = "I'm sorry, this Username is already in use";
		die(json_encode($response));
    }
    
  
    //create a new Username.  Let's setup our new query to create a Username.  
    //Again, to protect against sql injects, Username tokens such as :Username and :pass
    $query = "INSERT INTO AccountsTable ( Username, Password ) VALUES ( :Username, :Password ) ";
    
    //Again, we need to update our tokens with the actual data:
    $query_params = array(
        ':Username' => $_POST['Username'],
        ':Password' => $_POST['Password']
    );
    
    //time to run our query, and create the Username
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        die("Failed to run query: " . $ex->getMessage());

		$response["success"] = 0;
		$response["message"] = "Database Error. Please Try Again!";
		die(json_encode($response));
    }

	//If we have made it this far without dying, we have successfully added
	//a new Username to our database.  

	$response["success"] = 1;
	$response["message"] = "Username Successfully Added!";
	echo json_encode($response);

    
    
} else {
?>
	<h1>Register</h1> 
	<form action="index.php" method="post"> 
	    Username:<br /> 
	    <input type="text" name="Username" value="" /> 
	    <br /><br /> 
	    Password:<br /> 
	    <input type="Password" name="Password" value="" /> 
	    <br /><br /> 
	    <input type="submit" value="Register New Username" /> 
	</form>
	<?php
}

?>
