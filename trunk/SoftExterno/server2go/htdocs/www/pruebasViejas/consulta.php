<?php
require('clases/cliente.class.php');
$objCliente=new Cliente;
$consulta=$objCliente->mostrar_clientes();
?>

        <table>
			<tr>
				<th>Nombres</th>
				<th>Ciudad</th>
				<th>Sexo</th>
				<th>Telefono</th>
				<th>Fecha Nacimiento</th>
			</tr>
<?php
if($consulta) {
        while( $cliente = mysql_fetch_array($consulta) ){
        ?>
        
                  <tr id="fila-<?php echo $cliente['id'] ?>">
                          <td><?php echo $cliente['nombres'] ?></td>
                          <td><?php echo $cliente['ciudad'] ?></td>
                          <td><?php echo $cliente['sexo'] ?></td>
                          <td><?php echo $cliente['telefono'] ?></td>
                          <td><?php echo $cliente['fecha_nacimiento'] ?></td>
                  </tr>
        <?php
        }
}
?>
    </table>