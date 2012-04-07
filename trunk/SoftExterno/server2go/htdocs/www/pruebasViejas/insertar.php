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


<?
echo "<FORM action=\"".$_SERVER["PHP_SELF"]."\" method=\"POST\" style='margin:30px 5px 30px 5px;'>
<table width=\"500px\">
<tbody>
<tr>
<td>Nombre</td>
<td><input type=\"text\" name=\"nombre\" /></td>
</tr>
<tr>
<td>Provinvia</td>
<td><select name='RE_Prov' id=\"provincia\" poblacioattri=''>
<option value=''>Seleccione provincia</option>";
$B_BUSCAR= mysql_query ("SELECT * FROM provincias order by provincia asc",$link);
$R_BUSCAR=mysql_fetch_assoc($B_BUSCAR);
$C_BUSCAR=mysql_num_rows($B_BUSCAR);
$suma=0;
do{ ++$suma;
echo "<option value='".$R_BUSCAR['id']."'>".$R_BUSCAR['provincia']."</option>";
}while($R_BUSCAR=mysql_fetch_assoc($B_BUSCAR));
echo "</select> <span id='Buscando'></span></td>
</tr>
<tr>
<td>Población</td>
<td><select name='RE_pobla' id=\"poblacionList\">
<option value='' selected='selected'>Seleccionar población</option>
</select></td>
</tr>
<tr>
<td></td>
<td><input type=\"submit\" /></td>
</tr>
</tbody>
</table>
</form>";
?>




<script>
jQuery('#provincia').change(function () {
var numero =document.getElementById("provincia").value;
var poblacio = jQuery(this).attr("poblacioattri");
var to=document.getElementById("Buscando");
to.innerHTML="buscando....";
jQuery.ajax({
type: "POST", 
url: "busqueda.php",
data: 'idnumero='+numero+'&idpobla='+poblacio,
success: function(a) {
jQuery('#poblacionList').html(a);
var to=document.getElementById("Buscando");
to.innerHTML="";
}
});
})
.change();
</script>