<?php

function openDatabase() {
    $dbFile = realpath('music.db');
    echo $dbFile . "\n";
    $needDbCreate = !file_exists($dbFile);
    $db = new SQLiteDatabase($dbFile) or die((file_exists($dbFile)) ? "Unable to open" : "Unable to create");
    if($needDbCreate) {
        createTables($db);
        addArtist($db, 'Pink Floyd');
        addArtist($db, 'Beatles');
        addArtist($db, 'The Band');
    }
    return $db;
}



function addArtist($db, $name) { $db->queryExec ("INSERT INTO artists(artist_name) VALUES ('" . $name . "')"); }

 
function createTables($db) {
    $db->queryExec ("CREATE TABLE artists(artist_id INTEGER PRIMARY KEY, artist_name TEXT)");
}


function showArtists($db) {
    $sql = "SELECT artist_id, artist_name FROM artists ORDER BY artist_name";
    if ($result = $db->query($sql)) {
        if ($result->numRows() > 0) {
            while($row = $result->fetch()) {
                echo $row[0] . ":" . $row[1] . "\n";
            }
        } else {
            echo "No records matching your query were found.";
        }
    } else {
        echo "ERROR: Could not execute $sql. " . sqlite_error_string($db->lastError());
    }
}


























// Crear un fichero en el temporal 
// directorio de archivos utilizando sys_get_temp_dir()
//$temp_file = tempnam(sys_get_temp_dir(), 'Tux');

//echo $temp_file;
//echo sys_get_temp_dir()+'<br>';

//$ruta=sys_get_temp_dir()+'myBBDD.sqlite';
//echo $ruta;
$ruta='dbDesdeJava2.db';
?>


<?php
    //Create database

//    $db = new SQLiteDatabase('myBBDD.sqlite', 0666);
    $db = new SQLiteDatabase($ruta, 0666);

?>


<?php
/*
//Create table

if ($db = new SQLiteDatabase($ruta)) {
	$db->queryExec('CREATE TABLE "matriz" ("id" INTEGER PRIMARY KEY NOT NULL , "block" BOOL)');
}
*/
?>

<?php
/*
//Insert

if ($db = new SQLiteDatabase($ruta)) {
	for($i=1;$i<=25;$i++){
		$db->queryExec('INSERT INTO "matriz" ("id","block") VALUES('.$i.',1)');
	}
}
*/
?>



<?php
/*
//Select

if($result = $db->query("SELECT * FROM matriz", SQLITE_BOTH, $error))
{
	echo '<table border="1"><tr><td>Id</td><td>Block</td></tr>';
	while($row = $result->fetch())
	{
		echo '<tr><td>'.$row['id'].'</td><td>'.$row['block'].'</td></tr>';
	}
	echo '</table>';
}
else
{
  die($error);
}
*/
?>



<?php
/*
//Select

if($result = $db->query("SELECT * FROM people", SQLITE_BOTH, $error))
{
	echo '<table border="1"><tr><td>Id</td><td>Block</td></tr>';
	while($row = $result->fetch())
	{
		echo '<tr><td>'.$row['name'].'</td><td>'.$row['occupation'].'</td></tr>';
	}
	echo '</table>';
}
else
{
  die($error);
}
*/
?>