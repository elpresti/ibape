<?php 

$base = new PDO( 'sqlite2:/www/testtt.db'); 


$query = "CREATE TABLE $mytable(
            ID bigint(20) NOT NULL PRIMARY KEY,
            post_author bigint(20) NOT NULL,            
            post_date datetime,
            post_content longtext,
            post_title text,
            guid VARCHAR(255)            
            )";
            
//$results = $base->queryexec($query);
$results = sqlite_exec($base,$query);

$number = 1;
$title="My first post";
$content="The content of my post...";
$date = strftime( "%b %d %Y %H:%M", time());
$author=1;
$url = "http://www.scriptol.com/sql/sqlite-tutorial.php";

$query = "INSERT INTO $mytable(ID, post_title, post_content, post_author, post_date, guid) 
                VALUES ('$number', '$title', '$content', '$author', '$date', '$url')";
$results = $base->queryexec($query);




//-$dbhandle = sqlite_open('testtt2.db');
//$query = sqlite_exec($dbhandle, "SELECT * FROM people", $error);
//-$query="SELECT * FROM people";
//-$result = sqlite_query($dbhandle,$query);


/*
if (!$query) {
    exit("Error in query: '$error'");
} else {
    //echo 'Number of rows modified: ', sqlite_changes($dbhandle);
}¨
*/


while ($row = sqlite_fetch_array($results)) {
    print("Name: "+ print_r($row[0]) + " || " + print_r($row[1])+"\n"+"<br>");  
}

//print_r(sqlite_fetch_array($result));


?>