package com.luiztaira.dto;

import java.io.Serializable;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RestaurantRequestDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Fantasy name", required = true, example = "Restaurant TAP")
	private String fantasyName;

	@ApiModelProperty(notes = "Owner full name", required = true, example = "José Boêmio")
	private String ownerName;

	@ApiModelProperty(notes = "CNPJ", required = true, example = "86.220.897/0001-84")
	private String document;
	
	@ApiModelProperty(notes = "Coordinates(Latitude and Longitude) values. Single Point", required = true, example = "{'type':'Point','coordinates':[-46.57421,-21.785741]}")
	private Map<String, Object> address;

	@ApiModelProperty(notes = "Polygons array to perform a MultiPolygon", required = true, example = "{'type':'MultiPolygon','coordinates':[[[[30,20],[45,40],[10,40],[30,20]]],[[[15,5],[40,10],[10,20],[5,10],[15,5]]]]}")
	private Map<String, Object> coverageArea;

	public String getDocument() {
		if (document != null && !document.equals(""))
			document = document.replaceAll("\\.", "").replaceAll("/", "").replaceAll("\\-", "");
		return document;
	}
}
