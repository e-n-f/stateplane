stateplane
==========

old code to convert CA State Plane 3 to WGS84

I had no idea what I was doing at the time.
You should probably use GDAL and http://www.spatialreference.org/ref/epsg/2872/ instead.

@migurski says that the correct way to convert is

    ogr2ogr -s_srs EPSG:2872 -t_srs EPSG:4326 -f GeoJSON…
