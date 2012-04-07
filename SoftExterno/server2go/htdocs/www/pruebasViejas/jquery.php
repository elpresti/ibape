var poblacio = jQuery(this).attr("poblacioattri");

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



if($_POST["nombre"]){
$nombre=addslashes($_POST["nombre"]);
$RE_Prov=addslashes($_POST["RE_Prov"]);
$RE_pobla=addslashes($_POST["RE_pobla"]);

$mod=mysql_query("UPDATE clientes SET nombre='$nombre',provincia='$RE_Prov',poblacion='$RE_pobla' WHERE id=".$_POST["MOD_ID"]."",$link);
if ($mod==1){echo "<script>alert(\"Se ha Modificado.\");</script>";}else{echo "<script>alert(\"Error!!!!.\");</script>";}
}




</body>
</html>