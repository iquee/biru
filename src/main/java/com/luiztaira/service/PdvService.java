package com.luiztaira.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luiztaira.domain.Pdv;
import com.luiztaira.exception.PdvServerException;
import com.luiztaira.utils.Converter;
import com.luiztaira.utils.Validator;
import com.luiztaira.web.rest.dto.PdvRequestDTO;
import com.luiztaira.web.rest.dto.PdvResponseDTO;

/**
 * All services to manipulate entity {@link Pdv}
 *
 * @author taira
 *
 */
@Service
public interface PdvService extends Validator<PdvRequestDTO>, Converter<PdvRequestDTO, Pdv> {

	/**
	 * Create or update a single pdv
	 *
	 * @param dto
	 * @return pdv id
	 *
	 * @throws PdvServerException
	 */
	Long createOrUpdate(PdvRequestDTO dto) throws PdvServerException;

	/**
	 * Find a pdv by id
	 *
	 * @param id
	 * @return PdvResponseDTO
	 *
	 * @throws PdvServerException
	 */
	PdvResponseDTO getById(Long id) throws PdvServerException;

	/**
	 * @param coordinates
	 * @return PdvResponseDTO
	 *
	 * @throws PdvServerException
	 */
	PdvResponseDTO search(List<Double> coordinates) throws PdvServerException;
}
