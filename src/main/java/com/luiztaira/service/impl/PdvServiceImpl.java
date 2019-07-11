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
import com.luiztaira.service.SequenceGeneratorService;
import com.luiztaira.utils.NormalizeDocument;
import com.luiztaira.web.rest.dto.PdvRequestDTO;
import com.luiztaira.web.rest.dto.PdvResponseDTO;

@Component
public class PdvServiceImpl implements PdvService {

	@Autowired
	PdvRepository pdvRepository;

	@Autowired
	SequenceGeneratorService seqGenerator;

	@Override
	public Long createOrUpdate(PdvRequestDTO dto) throws PdvServerException {
		Pdv pdv = convert(dto);
		try {
			if (pdv.getId() == null) {
				// new pdv
				pdv.setId(seqGenerator.generateSequence(Pdv.SEQUENCE_NAME));
			} else {
				// update pdv: not necessary right now
			}
			return pdvRepository.save(pdv).getId();
		} catch (PdvServerException e) {
			throw new PdvServerException("Error in create pdv: " + e.getMessage());
		}
	}

	@Override
	public PdvResponseDTO getById(Long id) {
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

	/**
	 * Method to validate fields
	 * 
	 * @throws PdvServerException
	 */
	@Override
	public void validate(PdvRequestDTO dto) {
		if(dto.getTradingName() == null || dto.getTradingName() == "")
			throw new PdvServerException("Trading name must not be null");
		
		if(dto.getOwnerName() == null || dto.getOwnerName() == "")
			throw new PdvServerException("Owner name must not be null");
		
		if(dto.getDocument() == null || dto.getDocument() == "")
			throw new PdvServerException("Document must not be null");
		
		if(dto.getAddress() == null)
			throw new PdvServerException("Address must not be null");		
		
		if(dto.getCoverageArea() == null)
			throw new PdvServerException("Coverage area must not be null");
	}

	@Override
	@SuppressWarnings("unchecked")
	public Pdv convert(PdvRequestDTO dto) {
		// before create final object, check if all fields are correct
		validate(dto);

		Pdv pdv = new Pdv();
		pdv.setTradingName(dto.getTradingName());
		pdv.setOwnerName(dto.getOwnerName());
		pdv.setDocument(NormalizeDocument.normnalizeToStore(dto.getDocument()));

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