<?php
require('punto.class.php');
$objPunto=new Punto;
$consulta=$objPunto->mostrar_puntos();
?>
<!--
        <table border="1" style="font-size:10px;">
                <tr>
                    <th>Fecha</th>
					<th>Latitud</th>
					<th>Longitud</th>
					<th>Velocidad</th>
					<th>Profundidad</th>
					<th>Objeto</th>
					<th>Leido</th>					
					<th>Comentarios</th>

				</tr>
-->
<?php
if($consulta) {
		$i =0;
		while( $punto = mysql_fetch_array($consulta) ){							
			$i++;
        ?>        
		<!--
                <tr id="fila-<?php echo $punto['ID'] ?>">
                          <td><?php echo $punto['FECHA'] ?></td>
                          <td><?php echo $punto['LATITUD'] ?></td>
                          <td><?php echo $punto['LONGITUD'] ?></td>
                          <td><?php echo $punto['VELOCIDAD'] ?></td>
                          <td><?php echo $punto['PROFUNDIDAD'] ?></td>						  
						  <td><?php echo $punto['OBJETO'] ?></td>
						  <td><?php echo $punto['LEIDO'] ?></td>
                          <td><?php echo $punto['COMENTARIOS'] ?></td>
                </tr>
		-->	
        <?php
				//lo marcamos como leido en la base
				$objPunto->punto_leido($punto['ID']);
				if ($i == 1) {
					//echo '<br>LastID:'
					echo '<input type="hidden" size="50" id="campoLastID" value='.$punto['ID'].'>';
					//echo '<br>LastLatitud:'
					echo '<input type="hidden" size="50" id="campoLastLatitud" value='.$punto['LATITUD'].'>';
					//echo '<br>LastLongitud:'
					echo '<input type="hidden" size="50" id="campoLastLongitud" value='.$punto['LONGITUD'].'>';
					echo '<input type="hidden" size="50" id="campoLastKML" value="'.htmlentities($punto['KML']).'">';
					echo '<input type="hidden" id="campoLastObjeto" value="'.$punto['OBJETO'].'">';
					echo '<input type="hidden" id="campoLastFecha" value="'.$punto['FECHA'].'">';
				}						
        }
}
?>
<!--
		</table>
-->