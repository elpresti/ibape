<?php
class DBManager{
        var $conect;
  
        var $BaseDatos;
        var $Servidor;
        var $Usuario;
        var $Clave;
        function DBManager(){
                $this->BaseDatos = "basedatos";
                $this->Servidor = "localhost:7188";
                $this->Usuario = "root";
                $this->Clave = "";
        }

         function conectar() {
                if(!($con=@mysql_connect($this->Servidor,$this->Usuario,$this->Clave))){
						//$arr["Error"] = "Error 1";
						//echo json_encode($arr);
						echo"<h1> [:(] Error al conectar a la base de datos</h1>";      
						return false;
                        //exit();
                }
				
                if (!@mysql_select_db($this->BaseDatos,$con)){
						//$arr["Error"] = "Error 1";
						//echo json_encode($arr);
                        echo "<h1> [:(] Error al seleccionar la base de datos</h1>";  
                        //exit();
						return false;
                }				
                $this->conect=$con;
                return true;
        }
}

?>