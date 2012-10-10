 /*
 function ActualizarDatos(){
                var punto_id = $('#punto_id').attr('value');
                var nombres = $('#nombres').attr('value');
                var ciudad = $('#ciudad').attr('value'); 
                var alternativas = $("input[@name='alternativas']:checked").attr("value");
                var telefono = $("#telefono").attr("value");
                var fecha_nacimiento = $("#fecha_nacimiento").attr("value");

                $.ajax({
                        url: 'actualizar.php',
                        type: "POST",
                        data: "submit=&nombres="+nombres+"&ciudad="+ciudad+"&alternativas="+alternativas+"&telefono="+telefono+"&fecha_nacimiento="+fecha_nacimiento+"&punto_id="+punto_id,
                        success: function(datos){
                                alert(datos);
                                ConsultaDatos();
                                $("#formulario").hide();
                                $("#tabla").show();
                        }
                });
                return false;
        }
*/
        
        function ConsultaDatos(){
                $.ajax({
                        url: 'consulta.php',
                        cache: false,
                        type: "GET",
                        success: function(datos){
                                $("#tabla").html(datos);
                        }
                });
        }
		
		function ConsultaDatosHistoricos(){
                $.ajax({
                        url: 'consultaHistorico.php',
                        cache: false,
                        type: "GET",
                        success: function(datos){
                                $("#tabla").html(datos);								
                        }
                });		
		}
        
        function EliminarDato(punto_id){
                var msg = confirm("Desea eliminar este dato?")
                if ( msg ) {
                        $.ajax({
                                url: 'eliminar.php',
                                type: "GET",
                                data: "id="+punto_id,
                                success: function(datos){
                                        alert(datos);
                                        $("#fila-"+punto_id).remove();
                                }
                        });
                }
                return false;
        }
        
        function GrabarDatos(){
                var nombres = $('#nombres').attr('value');
                var ciudad = $('#ciudad').attr('value'); 
                var alternativas = $("input[@name='alternativas']:checked").attr("value");
                var telefono = $("#telefono").attr("value");
                var fecha_nacimiento = $("#fecha_nacimiento").attr("value");

                $.ajax({
                        url: 'nuevo.php',
                        type: "POST",
                        data: "submit=&nombres="+nombres+"&ciudad="+ciudad+"&alternativas="+alternativas+"&telefono="+telefono+"&fecha_nacimiento="+fecha_nacimiento,
                        success: function(datos){
                                ConsultaDatos();
                                alert(datos);
                                $("#formulario").hide();
                                $("#tabla").show();
                        }
                });
                return false;
        }
        //esta funcion es para el boton cancelar del form
        function Cancelar(){
                $("#formulario").hide();
                $("#tabla").show();
                return false;
        }