package com.luiztaira.utils;

import java.io.IOException;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Class to format GeoJsonMultiPolygon objects
 * 
 * @author taira
 *
 */
public class GeoJsonMultiPolygonSerializer extends JsonSerializer<GeoJsonMultiPolygon> {

	@Override
	public void serialize(GeoJsonMultiPolygon value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		gen.writeStartObject();
		gen.writeStringField("type", value.getType());
		gen.writeArrayFieldStart("coordinates");
		for (GeoJsonPolygon coordinates : value.getCoordinates()) {
			for (GeoJsonLineString line : coordinates.getCoordinates()) {
				gen.writeStartArray();
				for (Point point : line.getCoordinates()) {
					gen.writeObject(new double[] { point.getX(), point.getY() });
				}
			}
			gen.writeEndArray();
		}
		gen.writeEndArray();
		gen.writeEndObject();
	}
}