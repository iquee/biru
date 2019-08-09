package com.luiztaira.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.luiztaira.dto.RestaurantRequestDTO;

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
@Document(collection = Restaurant.COLLECTION_NAME)
public class Restaurant implements Serializable {

	public static final String COLLECTION_NAME = "restaurant";
	private static final long serialVersionUID = 1L;	

	@Id
	@EqualsAndHashCode.Include
	private String id;	

	@NotNull private String fantasyName;

	@NotNull private String ownerName;
	
	@Indexed(unique = true)
	@NotNull private String document;
	
	@NotNull private GeoJsonMultiPolygon coverageArea;	

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	@NotNull private GeoJsonPoint address;
    
    public static Restaurant convert(RestaurantRequestDTO dto) {
		Restaurant r = new Restaurant();
		r.setFantasyName(dto.getFantasyName());
		r.setOwnerName(dto.getOwnerName());
		r.setDocument(dto.getDocument());

		Map<String, Object> address = dto.getAddress();
		List<Double> addressCoordinates = (List<Double>) address.get("coordinates");
		
		GeoJsonPoint point = new GeoJsonPoint(addressCoordinates.get(0), addressCoordinates.get(1));
		r.setAddress(point);

		Map<String, Object> coverageArea = dto.getCoverageArea();
		List<GeoJsonPolygon> polygonList = (List<GeoJsonPolygon>) coverageArea.get("coordinates");
		r.setCoverageArea(new GeoJsonMultiPolygon(polygonList));

		return r;
	}
}