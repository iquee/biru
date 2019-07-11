package com.luiztaira.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.stereotype.Component;

import com.luiztaira.domain.Pdv;
import com.luiztaira.exception.PdvNotFoundException;
import com.luiztaira.exception.PdvServerException;
import com.luiztaira.repository.PdvRepository;
import com.luiztaira.service.PdvService;
import com.luiztaira.web.rest.dto.PdvRequestDTO;
import com.luiztaira.web.rest.dto.PdvResponseDTO;

@Component
public class PdvServiceImpl implements PdvService {

	@Autowired
	PdvRepository pdvRepository;

	@Override
	public String create(PdvRequestDTO dto) throws PdvServerException {
		Pdv pdv = convert(dto);
		try {			
			return pdvRepository.save(pdv).getId();
		} catch (PdvServerException e) {
			throw new PdvServerException("Error in create pdv: " + e.getMessage());
		}
	}

	@Override
	public PdvResponseDTO getById(String id) {
		Optional<Pdv> optional = pdvRepository.findById(id);
		if(optional.isPresent())
			return new PdvResponseDTO(optional.get());
		throw new PdvNotFoundException("No PDV found for id: " + id.toString());		
	}

	@Override
	public PdvResponseDTO search(List<Double> coordinates) throws PdvServerException {
		// check if location is inside any coverage area
		List<Pdv> pdvs = pdvRepository.searchIfInsideCoverageArea(coordinates.get(0), coordinates.get(1));
		if(!pdvs.isEmpty()) {
			// if inside, search the nearest pdv
			List<Pdv> nearest = pdvRepository.searchNearestPdv(coordinates.get(0), coordinates.get(1));			
			return new PdvResponseDTO(nearest.get(0));
		} else {
			throw new PdvNotFoundException("No PDV found in coverage area");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Pdv convert(PdvRequestDTO dto) {
		Pdv pdv = new Pdv();
		pdv.setTradingName(dto.getTradingName());
		pdv.setOwnerName(dto.getOwnerName());
		pdv.setDocument(dto.getDocument());

		Map<String, Object> address = dto.getAddress();
		List<Double> addressCoordinates = (List<Double>) address.get("coordinates");
		
		GeoJsonPoint point = new GeoJsonPoint(addressCoordinates.get(0), addressCoordinates.get(1));
		pdv.setAddress(point);

		Map<String, Object> coverageArea = dto.getCoverageArea();
		List<GeoJsonPolygon> polygonList = (List<GeoJsonPolygon>) coverageArea.get("coordinates");
		pdv.setCoverageArea(new GeoJsonMultiPolygon(polygonList));

		return pdv;
	}

}