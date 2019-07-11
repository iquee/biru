package com.luiztaira.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luiztaira.domain.Pdv;
import com.luiztaira.exception.PdvServerException;
import com.luiztaira.utils.Converter;
import com.luiztaira.web.rest.dto.PdvRequestDTO;
import com.luiztaira.web.rest.dto.PdvResponseDTO;

/**
 * All services to manipulate entity {@link Pdv}
 *
 * @author taira
 *
 */
@Service
public interface PdvService extends Converter<PdvRequestDTO, Pdv> {

	/**
	 * Create or update a single pdv
	 *
	 * @param dto
	 * @return pdv id
	 *
	 * @throws PdvServerException
	 */
	String create(PdvRequestDTO dto);

	/**
	 * Find a pdv by id
	 *
	 * @param id
	 * @return PdvResponseDTO
	 *
	 * @throws PdvServerException
	 */
	PdvResponseDTO getById(String id);

	/**
	 * @param coordinates
	 * @return PdvResponseDTO
	 *
	 * @throws PdvServerException
	 */
	PdvResponseDTO search(List<Double> coordinates);
}
