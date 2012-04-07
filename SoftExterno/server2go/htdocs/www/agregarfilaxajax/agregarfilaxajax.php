<?
require_once('xajax/xajax.inc.php'); //incluimos la librelia xajax

function eliminarFila($id_campo, $cant_campos){
	$respuesta = new xajaxResponse();
	$respuesta->addRemove("rowDetalle_$id_campo"); //borro el detalle que indica el parametro id_campo
	-- $cant_campos; //Resto uno al numero de campos y si es cero borro todo
	if($cant_campos == 0){
		$respuesta->addRemove("rowDetalle_0");
		$respuesta->addAssign("num_campos", "value", "0"); //dejo en cero la cantidad de campos para seguir agregando si asi lo desea el usuario
		$respuesta->addAssign("cant_campos", "value", "0");
	}
    $respuesta->addAssign("cant_campos", "value", $cant_campos);    
	return $respuesta;
}

function cancelar(){  //elimina todo el contenido de la tabla y vuelve a cero los contadores
    
    $respuesta = new xajaxResponse();

    $respuesta->addRemove("tbDetalle"); //vuelve a crear la tabla vacia
    $respuesta->addCreate("tblDetalle", "tbody", "tbDetalle");
    $respuesta->addAssign("num_campos", "value", "0");
	$respuesta->addAssign("cant_campos", "value", "0");
    return $respuesta;
}

function agregarFila($formulario){
    $respuesta = new xajaxResponse();    
	extract($formulario);	
	$id_campos = $cant_campos = $num_campos+1;
	$str_html_td1 = "$txtNombre" ;
    $str_html_td2 = "$txtEdad";
    $str_html_td3 = "$txtDireccion";
    $str_html_td4 = "$selSexo";
    $str_html_td5 = "$selEstCivil";
    $str_html_td6 = '<img src="images/delete.png" width="16" height="16" alt="Eliminar" onclick="if(confirm(\'Realmente desea eliminar este detalle?\')){xajax_eliminarFila('. $id_campos .', proyecto.cant_campos.value);}"/>';

	if($num_campos == 0){ // creamos un encabezado de lo contrario solo agragamos la fila
		$respuesta->addCreate("tbDetalle", "tr", "rowDetalle_0");
        $respuesta->addCreate("rowDetalle_0", "th", "tdDetalle_01");    //creamos los campos
        $respuesta->addCreate("rowDetalle_0", "th", "tdDetalle_02");
        $respuesta->addCreate("rowDetalle_0", "th", "tdDetalle_03");
        $respuesta->addCreate("rowDetalle_0", "th", "tdDetalle_04");
        $respuesta->addCreate("rowDetalle_0", "th", "tdDetalle_05");
        $respuesta->addCreate("rowDetalle_0", "th", "tdDetalle_06");

        $respuesta->addAssign("tdDetalle_01", "innerHTML", "Nombre");   //asignamos el contenido
        $respuesta->addAssign("tdDetalle_02", "innerHTML", "Edad");
        $respuesta->addAssign("tdDetalle_03", "innerHTML", "Direccion");
        $respuesta->addAssign("tdDetalle_04", "innerHTML", "Sexo");
        $respuesta->addAssign("tdDetalle_05", "innerHTML", "Estado Civil");
        $respuesta->addAssign("tdDetalle_06", "innerHTML", "Eliminar");
	}
    $idRow = "rowDetalle_$id_campos";
    $idTd = "tdDetalle_$id_campos";
	$respuesta->addCreate("tbDetalle", "tr", $idRow);
    $respuesta->addCreate($idRow, "td", $idTd."1");     //creamos los campos
    $respuesta->addCreate($idRow, "td", $idTd."2");
    $respuesta->addCreate($idRow, "td", $idTd."3");
    $respuesta->addCreate($idRow, "td", $idTd."4");
    $respuesta->addCreate($idRow, "td", $idTd."5");
    $respuesta->addCreate($idRow, "td", $idTd."6");
/*
 *     Esta parte podria estar dentro de algun ciclo iterativo  */
    
    $respuesta->addAssign($idTd."1", "innerHTML", $str_html_td1);   //asignamos el contenido
    $respuesta->addAssign($idTd."2", "innerHTML", $str_html_td2);
    $respuesta->addAssign($idTd."3", "innerHTML", $str_html_td3);
    $respuesta->addAssign($idTd."4", "innerHTML", $str_html_td4);
    $respuesta->addAssign($idTd."5", "innerHTML", $str_html_td5);
    $respuesta->addAssign($idTd."6", "innerHTML", $str_html_td6);

/*  aumentamos el contador de campos  */

	$respuesta->addAssign("num_campos","value", $id_campos);
	$respuesta->addAssign("cant_campos" ,"value", $id_campos);    
	return $respuesta;
}

$xajax=new xajax();         // Crea un nuevo objeto xajax
$xajax->setCharEncoding("iso-8859-1"); // le indica la codificación que debe utilizar
$xajax->decodeUTF8InputOn();            // decodifica los caracteres extraños
$xajax->registerFunction("agregarFila"); //Registramos la función para indicar que se utilizará con xajax.
$xajax->registerFunction("cancelar");
$xajax->registerFunction("eliminarFila");
$xajax->processRequests();
?>

<html>
<meta http-equiv="Pragma"content="no-cache">
<meta http-equiv="expires"content="0">
<head>
<?php $xajax->printJavascript("xajax"); //imprime el codigo javascript necesario para que funcione todo. ?>
<link href="CSS/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<form name="proyecto" id="proyecto" action="" method="post">
    <input type="hidden" id="num_campos" name="num_campos" value="0" />
    <input type="hidden" id="cant_campos" name="cant_campos" value="0" />
<div id="cont" class="container">
<fieldset>
	<legend>El formulario</legend>
	<div class="top">
	<label class="label" for="txtNombre">Nombre:</label>
        <div class="div_texbox"><input type="text" id="txtNombre" name="txtNombre" value="" class="textbox" /></div>
        <label class="label" for="txtEdad">Edad:</label>
        <div class="div_texbox"><input type="text" id="txtEdad" name="txtEdad" value="" class="textbox txtUser" /></div>
        <label class="label" for="txtDireccion">Direccion:</label>
        <div class="div_texbox"><input type="text" id="txtDireccion" name="txtDireccion" value="" class="textbox txtCmt" /></div>
        <label class="label" for="selSexo">Sexo:</label>
        <div class="div_texbox">
            <select id="selSexo" name="selSexo" class="textbox txtFec">
                <option value="-">Seleccione</option>
                <option value="Hombre">Hombre</option>
                <option value="Mujer">Mujer</option>
            </select>
        </div>
        <label class="label" for="selEstCivil">Estado Civil:</label>
        <div class="div_texbox">
            <select id="selEstCivil" name="selEstCivil" class="textbox txtFec">
                <option value="-">Seleccione</option>
                <option value="Soltero">Soltero</option>
                <option value="Casado">Casado</option>
                <option value="Viudo">Viudo</option>
            </select>
        </div>
	</div>
</fieldset>
<div class="button_div">    
    <input type="reset" id="btnCancel" name="btnCancel" value="Cancelar" class="buttons_CANCEL" onclick="xajax_cancelar();" />
    <input type="button" id="btnAgregar" name="btnAgregar" value="Agregar Persona" class="buttons_aplicar" onclick="xajax_agregarFila(xajax.getFormValues('proyecto'));" />
</div>
<fieldset class="fieldset">
    <legend class="legend">
        Detalle de Personas
    </legend>
    <div class="clear"></div>
    <div id="form3" class="form-horiz">
        <table width="100%" id="tblDetalle" class="listado"><tbody id="tbDetalle"></tbody></table>
    </div>
</fieldset>
</div>
</form>
</body>
</html>