<?php 
include_once("conexion.class.php");

class Punto{
 //constructor  
        var $con;
        function Punto(){
                $this->con=new DBManager;
        }

        function insertar($campos){
                if($this->con->conectar()==true){
                        return mysql_query("INSERT INTO PUNTOS (nombres, ciudad, sexo, telefono, fecha_nacimiento) VALUES ('".$campos[0]."', '".$campos[1]."','".$campos[2]."','".$campos[3]."','".$campos[4]."')");
                }
        }
        
        function actualizar($campos,$id){
                if($this->con->conectar()==true){
                        return mysql_query("UPDATE PUNTOS SET nombres = '".$campos[0]."', ciudad = '".$campos[1]."', sexo = '".$campos[2]."', telefono = '".$campos[3]."', fecha_nacimiento = '".$campos[4]."' WHERE id = ".$id);
                }
        }
        
        function mostrar_punto($id){
                if($this->con->conectar()==true){
                        return mysql_query("SELECT * FROM PUNTOS WHERE id=".$id);
                }
        }
		
		function punto_leido($id) {
		       if($this->con->conectar()==true){
			            return mysql_query("UPDATE PUNTOS SET LEIDO=true WHERE ID=".$id);
			   }
		}

		function punto_historico_leido($id) {
		       if($this->con->conectar()==true){
			            return mysql_query("UPDATE PuntosHistoricos SET LEIDO=true WHERE ID=".$id);
			   }
		}
		
        function mostrar_puntos(){
				if($this->con->conectar()==true){				
						return mysql_query("SELECT * FROM PUNTOS WHERE LEIDO=false ORDER BY ID DESC");
				}
        }		

        function mostrar_puntos_historicos(){
				if($this->con->conectar()==true){				
						return mysql_query("SELECT * FROM PuntosHistoricos WHERE LEIDO=false ORDER BY ID DESC LIMIT 0,1");
				}
        }
		
        function eliminar($id){
                if($this->con->conectar()==true){
                        return mysql_query("DELETE FROM PUNTOS WHERE id=".$id);
                }
        }
}

/*
$.ajax({
  url: url,
  data: data,
  success: success,
  dataType: dataType
});
*/
?>