<?
function Conectarse()
{
if (!($link=mysql_connect("localhost","root","")))
{
echo "Error conectando a la base de datos.";
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

$B_BUSCAR= mysql_query ("SELECT * FROM tabla",$link);
$R_BUSCAR=mysql_fetch_assoc($B_BUSCAR);
$C_BUSCAR=mysql_num_rows($B_BUSCAR);
if($C_BUSCAR){
do{
echo $R_BUSCAR['id']." - ".htmlentities($R_BUSCAR['poblacio'])."<br>";

}while($R_BUSCAR=mysql_fetch_assoc($B_BUSCAR));
}else{
echo "<option value=''>".htmlentities("Seleccionar población")."</option>";
}
mysql_close($link);
?>