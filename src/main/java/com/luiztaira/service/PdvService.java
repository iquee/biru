package com.luiztaira.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luiztaira.domain.Pdv;
import com.luiztaira.exception.PdvException;
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
	 * @throws PdvException
	 */
	Long createOrUpdate(PdvRequestDTO dto) throws PdvException;

	/**
	 * Find a pdv by id
	 *
	 * @param id
	 * @return pdv
	 *
	 * @throws PdvException
	 */
	PdvResponseDTO getById(Long id) throws PdvException;

	/**
	 * @param coordinates
	 * @return pdv
	 *
	 * @throws PdvException
	 */
	Pdv search(List<Double> coordinates) throws PdvException;
}
