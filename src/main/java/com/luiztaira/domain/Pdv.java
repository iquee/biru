package com.luiztaira.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pdvs")
public class Pdv implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	public static final String SEQUENCE_NAME = "pdv_sequence";

	@Id
	private Long id;
	
	private String tradingName;
	
	private String ownerName;
	
	@Indexed(unique = true)
	private String document;	
	
	private GeoJsonMultiPolygon coverageArea;	

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private GeoJsonPoint address;

	public Pdv() {
		super();
	}

	public Pdv(Long id, String tradingName, String ownerName, String document, GeoJsonMultiPolygon coverageArea,
			GeoJsonPoint address) {
		this.tradingName = tradingName;
		this.ownerName = ownerName;
		this.document = document;
		this.coverageArea = coverageArea;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public GeoJsonMultiPolygon getCoverageArea() {
		return coverageArea;
	}

	public void setCoverageArea(GeoJsonMultiPolygon coverageArea) {
		this.coverageArea = coverageArea;
	}

	public GeoJsonPoint getAddress() {
		return address;
	}

	public void setAddress(GeoJsonPoint address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Pdv [id=" + id + ", tradingName=" + tradingName + ", ownerName=" + ownerName + ", document=" + document
				+ ", coverageArea=" + coverageArea + ", address=" + address + "]";
	}
}