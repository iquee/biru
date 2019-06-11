package com.luiztaira.pdv;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.javafaker.Faker;

@AutoConfigureMockMvc
@WebAppConfiguration
public class PdvRestTest {	

	private static MockMvc MOCK;	
	private static String API_PREFIX = "/api/pdv/";
	private static Faker FAKER;
	
	private static WebApplicationContext ctx;
	
	@Autowired
	public PdvRestTest(WebApplicationContext ctx) {
		PdvRestTest.ctx = ctx;
		FAKER = new Faker();
		MOCK = MockMvcBuilders.webAppContextSetup(this.ctx).build();
	}
	
	//@Test
	public void doCreatePdv() throws Exception {		
		JSONObject jsonObject = buildPdvRequestDTO();
		MOCK.perform(put(API_PREFIX)
				.content(jsonObject.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());		
	}	

	@SuppressWarnings("unchecked")
	private JSONObject buildPdvRequestDTO() {
		String tradingName = FAKER.beer().name();
		String ownerName = FAKER.name().fullName();		
		String document = FAKER.number().digits(14);
		
		JSONObject address = new JSONObject();
		GeoJsonPoint point = new GeoJsonPoint(-53.0588579, -22.5798974);
		address.put("type", "Point");
		address.put("coordinates", point);
		
		JSONObject coverageArea = new JSONObject();		
		List<GeoJsonPolygon> polygons = new ArrayList<>();
		List<Point> points = new ArrayList<>();
		points.add(new Point(-53.0592442,-22.5660277));
		points.add(new Point(-53.0693293,-22.5885355));
		points.add(new Point(-53.0576563,-22.59436));
		points.add(new Point(-53.0449533,-22.5712191));
		points.add(new Point(-53.0592442,-22.5660277));		
		GeoJsonPolygon polygon = new GeoJsonPolygon(points);		
		polygons.add(polygon);
		GeoJsonMultiPolygon multipolygon = new GeoJsonMultiPolygon(polygons);
		coverageArea.put("type", "MultiPolygon");
		coverageArea.put("coordinates", multipolygon);
		
		JSONObject pdv= new JSONObject();
		pdv.put("tradingName", tradingName);
		pdv.put("ownerName", ownerName);
		pdv.put("document", document);
		pdv.put("address", address);
		pdv.put("coverageArea", coverageArea);
		
		return pdv;
	}

}
