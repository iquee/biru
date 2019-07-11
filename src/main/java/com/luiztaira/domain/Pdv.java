package com.luiztaira.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = Pdv.COLLECTION_NAME)
public class Pdv implements Serializable {

	public static final String COLLECTION_NAME = "pdvs";
	private static final long serialVersionUID = 1L;	

	@Id
	@EqualsAndHashCode.Include
	private String id;	

	@NotNull private String tradingName;

	@NotNull private String ownerName;
	
	@Indexed(unique = true)
	@NotNull private String document;	
	
	@NotNull private GeoJsonMultiPolygon coverageArea;	

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	@NotNull private GeoJsonPoint address;
}