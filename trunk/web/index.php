<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Mapa de Navegaci&oacuten - IBaPE v1.0</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="jquery.functions.js" type="text/javascript"></script>


 
    <script src="http://www.google.com/jsapi?key=ABQIAAAAOtousWKrL8vd1IswNyu1ehRGEPZC4_vF9r_VtS7sm9R3s5IEWRRGeCJGgyvIj3TyAjFPctCee28OOA"></script>        
    <script type="text/javascript">
      var ge;
      google.load("earth", "1");

      function init() {
         google.earth.createInstance('map3d', initCB, failureCB);
      }

      function initCB(instance) {
         ge = instance;
         ge.getWindow().setVisibility(true);         
		 //configura parametros
		 ge.getNavigationControl().setVisibility(ge.VISIBILITY_AUTO);// add a navigation control
		 ge.getOptions().setStatusBarVisibility(true);
		 ge.getOptions().setScaleLegendVisibility(true);
      }
      function failureCB(errorCode) {
          alert("Error"+errorCode+". No se pudo crear una instancia de GE");
      }
      google.setOnLoadCallback(init);	  

    </script>

<script type="text/javascript">
function consultaYmuestra() {
	ConsultaDatos();	
	//pasaUltimoPuntoAmapa();
	var inputLastID = document.getElementById('campoLastID');
	var inputLastObjeto = document.getElementById('campoLastObjeto');
	if ((inputLastID != null) && (inputLastID.value >= 0)){
		if ((inputLastObjeto != null) && (inputLastObjeto.value == "vaciarMapa()")){
			vaciarMapa();
		}
		else{ cargaUltimoKml(); }
		
	}
}

function vaciarMapa(){
//remove all features from map
  var features = ge.getFeatures();
  while (features.getLastChild() != null)
  {
    features.removeChild(features.getLastChild());
  }
}

function pasaUltimoPuntoAmapa() {
    var inputLastLatitud = document.getElementById('campoLastLatitud');
    var inputLastLongitud = document.getElementById('campoLastLongitud');
	var inputLastID= document.getElementById('campoLastID');
	if ((!(inputLastLongitud == null)) && (!(inputLastLatitud == null))) {
		var lastLatitud = inputLastLatitud.value;
		var lastLongitud = inputLastLongitud.value;
		var lastID = inputLastID.value;
		crearMarca(parseFloat(lastLatitud),parseFloat(lastLongitud),lastID);
	}
}

function levantaInputsYdispara() {
    var inputLatitud = document.getElementById('campoLatitud');
    var latitud = inputLatitud.value;
    var inputLongitud = document.getElementById('campoLongitud');
    var longitud = inputLongitud.value;
	crearMarca(parseFloat(latitud),parseFloat(longitud),"MANUAL");
}

function cargaUltimoKml() {
	var inputLastObjeto = document.getElementById('campoLastObjeto');
/*
	if ((!(inputLastObjeto == null)) && (inputLastObjeto.value == 'BARCO')) {
	  // Define a custom icon.
	  var icon = ge.createIcon('');
	  icon.setHref('http://localhost:4001/imgs/icono_barco3.png');
	  var style = ge.createStyle(''); //create a new style
	  style.getIconStyle().setIcon(icon); //apply the icon to the style
	  placemark.setStyleSelector(style); //apply the style to the placemark
	}
*/	
	var inputLastKML = document.getElementById('campoLastKML');
	if (!(inputLastKML == null)) {
		var kmlString = inputLastKML.value;

var kmlString2 = ''
//              + 'xml version="1.0" encoding="UTF-8"'
//              + '<kml xmlns="http://www.opengis.net/kml/2.2">'
              + '<kml xmlns="http://www.opengis.net/kml/2.2">'			     
              + '<Document>'
              + '  <Camera>'
              + '    <longitude>-56.854178</longitude>'
              + '    <latitude>-37.116732</latitude>'

              + '    <altitude>300</altitude>'
//              + '    <altitudeMode>clampToGround</altitudeMode>'			  			  
              + '    <heading>0.0</heading>'
              + '    <tilt>0</tilt>'			  			  
              + '  </Camera>'
              + '  <Placemark>'
              + '    <name>Placemark from KML string</name>'
              + '    <Point>'
              + '      <coordinates>-56.854178,-37.116732,0</coordinates>'
              + '    </Point>'
              + '  </Placemark>'

              + '</Document>'
              + '</kml>';					  
		
		var kmlObject = ge.parseKml(kmlString);
		ge.getFeatures().appendChild(kmlObject);
		if (kmlObject.getAbstractView())
		   ge.getView().setAbstractView(kmlObject.getAbstractView());		
	}
}


function crearMarca(latitud,longitud,nroMarca) {
	// Create the placemark.
	var placemark = ge.createPlacemark('');
	placemark.setName("Punto "+nroMarca);

	// Define a custom icon.
	var icon = ge.createIcon('');
	icon.setHref('http://localhost:4001/www/icono_barco3.png');
	var style = ge.createStyle(''); //create a new style
	style.getIconStyle().setIcon(icon); //apply the icon to the style
	placemark.setStyleSelector(style); //apply the style to the placemark

	// Set the placemark's location.  
	var point = ge.createPoint('');
	point.setLatitude(latitud);
	point.setLongitude(longitud);
	//point.setAltitude(altitud);
	placemark.setGeometry(point);

	// Add the placemark to Earth.
	ge.getFeatures().appendChild(placemark);
	
	// Mueve camara al punto creado
	mueveCamaraApunto(latitud,longitud);	
}

function mueveCamaraApunto(latitud,longitud) {
	// Get the current view
	var lookAt = ge.getView().copyAsLookAt(ge.ALTITUDE_RELATIVE_TO_GROUND);

	// Set new latitude and longitude values
	lookAt.setLatitude(latitud);
	lookAt.setLongitude(longitud);
	lookAt.setRange(300); //default is 0.0
	//lookAt.setTilt(lookAt.getTilt() + 15.0);	
	//lookAt.setRange(lookAt.getRange() * 2.0);// Zoom out to twice the current range	
	
	// Update the view in Google Earth
	ge.getView().setAbstractView(lookAt);

}

/*
function addKmlFromString(kmlString) {
  var kmlObject = ge.parseKml(kmlString);
  ge.getFeatures().appendChild(kmlObject);
}

function cargaKmlAhora() {
var textoKml=
/*  'xml version="1.0" encoding="UTF-8"' +  */
/*  '<kml xmlns="http://www.opengis.net/kml/2.2">' +
  '  <Placemark>' +
  '    <name>Test Placemark</name>' +
  '    <Point>' +
  '      <coordinates>' +
  '        -56.85454,-37.11628' +
  '      </coordinates>' +
  '    </Point>' +
  '  </Placemark>' +
  '</kml>';
addKmlFromString(textoKml);
}
*/
</script>
    
    <script type="text/javascript">
        var currentKmlObject = null;

        function fetchKmlFromInput() {
          // remove the old KML object if it exists
          //if (currentKmlObject) {
          //  ge.getFeatures().removeChild(currentKmlObject);
          //  currentKmlObject = null;
          //}

          var kmlUrlBox = document.getElementById('kml-url');
          var kmlUrl = kmlUrlBox.value;

          google.earth.fetchKml(ge, kmlUrl, finishFetchKml);
        }

        function finishFetchKml(kmlObject) {
          // check if the KML was fetched properly
          if (kmlObject) {
            // add the fetched KML to Earth
            currentKmlObject = kmlObject;
            ge.getFeatures().appendChild(currentKmlObject);
          } else {
            // wrap alerts in API callbacks and event handlers
            // in a setTimeout to prevent deadlock in some browsers
            setTimeout(function() {
              alert('Bad or null KML.');
            }, 0);
          }
        }    
       
    </script>  

	
 
</head>

<body onLoad="setInterval('consultaYmuestra()',3000);">

<div id="map3d" style="height: 100%; width: 100%"></div>  
<div id="botonardi">

<form action="#" method="get" onsubmit="return false;">
    Latitud:<input type="text" id="campoLatitud" size="50" value="latitud"/><br/>
	Longitud:<input type="text" id="campoLongitud" size="50" value="longitud"/><br/>
    <input type="submit" onclick="levantaInputsYdispara()" value="Carga Punto!"/>
	<!-- <button name="elBoton" onClick="cargaKmlAhora();" text="CargaKML" value="CargaKMLvalue">CargaKmlEntre</button>   -->
</form>

</div>
<div id="contenedor">

    <div id="tabla">
    <?php 
		require('consulta.php');
	?>
    </div>
</div>	
			
</body>
</html>