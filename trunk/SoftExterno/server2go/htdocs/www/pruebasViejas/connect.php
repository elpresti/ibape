<?
function Conectarse(){
if (!($link=mysql_connect("localhost","root","")))
{echo "Error conectando a la base de datos.";
exit();
}
if (!mysql_select_db("desarrolloweb",$link))
{
echo "Error seleccionando la base de datos.";
exit();
}
return $link;
}
$link=Conectarse();

echo "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">
<html xmlns=\"http://www.w3.org/1999/xhtml\">
<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\" />
<script src=\"http://code.jquery.com/jquery-latest.js\"></script>
</head>

<body>";
?>