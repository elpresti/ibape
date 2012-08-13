function handleRuler(event)
{
        // measPoint is set to 0 when not in use, 1 when I enable the ruler and 2 for the endpoint.
        if (measPoint == 1)
        {
                //create a placemark for the points
                        measStart = ge.createPlacemark('');
                        var point = ge.createPoint('');
                        point.setLatitude(event.getLatitude());
                        point.setLongitude(event.getLongitude());
                        measStart.setGeometry(point);

                        measStart.setName("0");

                        ge.getFeatures().appendChild(measStart);

                        measPoint = 2;

        }
        // Point 1 added, most of the work is done in point 2:
        else if (measPoint == 2)
        {
                measEnd = ge.createPlacemark('');
                var point = ge.createPoint('');
                point.setLatitude(event.getLatitude());
                point.setLongitude(event.getLongitude());
                measEnd.setGeometry(point);

                ge.getFeatures().appendChild(measEnd);

        // Add a LineString placemark 
                measStringMark = ge.createPlacemark('');
                measString = ge.createLineString('');
                measStringMark.setGeometry(measString);
        // Add the start and endpoints to the string
                measString.getCoordinates().pushLatLngAlt(measStart.getGeometry().getLatitude(), measStart.getGeometry().getLongitude(), 0);
                measString.getCoordinates().pushLatLngAlt(measEnd.getGeometry().getLatitude(), measEnd.getGeometry().getLongitude(), 0);

        // Measure distance and angle
                geoStart = new google.maps.LatLng (measStart.getGeometry().getLatitude(), measStart.getGeometry().getLongitude());
                geoEnd = new google.maps.LatLng (measEnd.getGeometry().getLatitude(), measEnd.getGeometry().getLongitude());

                geoDist = google.maps.geometry.spherical.computeDistanceBetween(geoStart, geoEnd);
                geoAngle = google.maps.geometry.spherical.computeHeading(geoStart, geoEnd);

        // Personally, I don't use the alert, but it's useful for testing:
                // alert ("Distance: " + geoDist + "\nAngle: " + geoAngle);

        // Optional: Convert to KMs
                if (geoDist > 1000)
                {
                        var distUnits = "KMs";
                        geoDist /= 1000;
                }

                else
                {
                        var distUnits = "Ms";
                }

        // Make the linestring follow the ground
                measString.setTessellate(true);
                measString.setAltitudeMode(ge.ALTITUDE_CLAMP_TO_GROUND);

                measStringMark.setStyleSelector(ge.createStyle(''));
                var lineStyle = measStringMark.getStyleSelector().getLineStyle();
                lineStyle.setWidth(6);
                lineStyle.getColor().set('7700ffff'); //aabbggrr -- Opacity, Blue, Green, Red in hex (00-FF)

        // Set the description so you can get more precise calculations by clicking on the string information 
                measStringMark.setDescription("Distance: " + geoDist.toPrecision(7) + " " + distUnits + "<br/>Angle: " + geoAngle.toPrecision(5));

                ge.getFeatures().appendChild(measStringMark);

                measPoint = 0;

        // Set the name of the endpoint, change the precision as needed
                measEnd.setName(geoDist.toPrecision(5) + " " + distUnits + "," + geoAngle.toPrecision(4) + '°');

                try 
                {
                // I remove the handler and create it each time I enable the ruler mode, performance doesn't suffer one bit and it prevents GE from trying to handle the click each time
                        google.earth.removeEventListener(ge.getWindow(), 'click', handleRuler);
                }
                catch (err)
                {
                        alert ("Error: " + err.description);
                }
        }
}