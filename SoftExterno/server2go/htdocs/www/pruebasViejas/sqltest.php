<?php

$host            = 'localhost:3307'; // Using 'localhost' php attempts to open sockets/named pipes
$user            = 'root';
$password        = 'password';  //Default MicroPHP password is password
$database        = 'mysql';
$table           = 'help_topic';
$query           = 'SELECT * FROM '.$table;	//A simple query

echo 'Attempting Connection as '.$user.' with password '.$password.'<p>';

// Connect to the Database
$link = mysql_connect($host, $user, $password) or die('Euuurgh!: Could not connect: ' .mysql_error());

echo 'Connected to database <b>'.$database.'</b> successfully'.'<p>';

// Select The Database
mysql_select_db($database) or die('Could not select database');

// Perform a Simple SQL query
$result = mysql_query($query) or die('Query failed: ' . mysql_error());

echo "Query on <b>".$table."</b> succeeded";

// Printing results in HTML
echo "<table>\n";
while ($line = mysql_fetch_array($result, MYSQL_ASSOC)) 
{
  echo "\t<tr>\n";
  foreach ($line as $col_value) 
  {
    echo "\t\t<td>$col_value</td>\n";
  }
  echo "\t</tr>\n";
}
echo "</table>\n";
// Free resultset
mysql_free_result($result);

// Closing connection
mysql_close($link);

?>