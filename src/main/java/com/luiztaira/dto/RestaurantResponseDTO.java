package com.luiztaira.dto;

import java.io.Serializable;

import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.luiztaira.domain.Restaurant;
import com.luiztaira.utils.GeoJsonMultiPolygonSerializer;
import com.luiztaira.utils.GeoJsonPointSerializer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RestaurantResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "i", example = "5d25f8b95efa9e39005d83d1")
	private String id;

	@ApiModelProperty(notes = "Trading name", example = "Distribuidora TAP")
	private String tradingName;

	@ApiModelProperty(notes = "Nome of owner", example = "José Boêmio")
	private String ownerName;

	@ApiModelProperty(notes = "CNPJ", example = "86.220.897/0001-84")
	private String document;

	@ApiModelProperty(notes = "Coordinates(Latitude and Longitude) values. Single Point", example = "{'type':'Point','coordinates':[-46.57421,-21.785741]}")
	@JsonSerialize(using = GeoJsonPointSerializer.class)
	private GeoJsonPoint address;

	@ApiModelProperty(notes = "Array of Polygon to perform a MultiPolygon", example = "{'type':'MultiPolygon','coordinates':[[[[30,20],[45,40],[10,40],[30,20]]],[[[15,5],[40,10],[10,20],[5,10],[15,5]]]]}")
	@JsonSerialize(using = GeoJsonMultiPolygonSerializer.class)
	private GeoJsonMultiPolygon coverageArea;

	public String getDocument() {
		if (document != null && document.length() == 14)
			document = document.substring(0, 2) + "." + document.substring(2, 5) + "." + document.substring(5, 8) + "/"
					+ document.substring(8, 12) + "-" + document.substring(12, 14);
		return document;
	}
	
	public static RestaurantResponseDTO convert(Restaurant restaurant) {		
		return new RestaurantResponseDTO();
	}
}
