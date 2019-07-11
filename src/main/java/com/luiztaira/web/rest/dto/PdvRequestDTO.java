package com.luiztaira.web.rest.dto;

import java.io.Serializable;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;

public class PdvRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Trading name", required = true, example = "Distribuidora TAP")
	private String tradingName;

	@ApiModelProperty(notes = "Nome of owner", required = true, example = "José Boêmio")
	private String ownerName;

	@ApiModelProperty(notes = "CNPJ", required = true, example = "86.220.897/0001-84")
	private String document;

	@ApiModelProperty(notes = "Coordinates(Latitude and Longitude) values. Single Point", required = true, example = "{\"type\":\"Point\",\"coordinates\":[-46.57421,-21.785741]}")
	private Map<String, Object> address;

	@ApiModelProperty(notes = "Array of Polygon to perform a MultiPolygon", required = true, example = "{\"type\":\"MultiPolygon\",\"coordinates\":[[[[30,20],[45,40],[10,40],[30,20]]],[[[15,5],[40,10],[10,20],[5,10],[15,5]]]]}")
	private Map<String, Object> coverageArea;

	public PdvRequestDTO(String tradingName, String ownerName, String document, Map<String, Object> address,
			Map<String, Object> coverageArea) {
		super();
		this.tradingName = tradingName;
		this.ownerName = ownerName;
		this.document = document;
		this.address = address;
		this.coverageArea = coverageArea;
	}	

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getDocument() {
		return normnalizeDocument(document);
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public Map<String, Object> getAddress() {
		return address;
	}

	public void setAddress(Map<String, Object> address) {
		this.address = address;
	}

	public Map<String, Object> getCoverageArea() {
		return coverageArea;
	}

	public void setCoverageArea(Map<String, Object> coverageArea) {
		this.coverageArea = coverageArea;
	}
	
	public static String normnalizeDocument(String document) {
		if (document != null && !document.equals(""))
			document = document.replaceAll("\\.", "").replaceAll("/", "").replaceAll("\\-", "");
		return document;
	}
}
