package com.luiztaira.pdv;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.luiztaira.exception.PdvException;
import com.luiztaira.service.PdvService;
import com.luiztaira.web.rest.dto.PdvRequestDTO;
import com.luiztaira.web.rest.dto.PdvResponseDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class PdvTest {

	@Autowired
	PdvService pdvService;

	// checked tests
	@Test
	void createPdv() throws Exception {
		Long id = pdvService.createOrUpdate(buildPdv("Boteco Legal", "Zé Sorriso", "29.165.498/0001-24"));
		assertNotNull(id);
	}

	@Test
	void getPdvById() throws Exception {
		Long id = pdvService.createOrUpdate(buildPdv("Bar da Alegria", "João Risada", "25.221.259/0001-93"));
		PdvResponseDTO pdv = pdvService.getById(id);
		assertThat(pdv.getTradingName(), equalTo("Bar da Alegria"));
		assertThat(pdv.getOwnerName(), equalTo("João Risada"));
	}

	// fail tests
	@Test
	public void testSavePdvExistedDocument() throws PdvException {
		DuplicateKeyException thrown = assertThrows(DuplicateKeyException.class,
				() -> pdvService.createOrUpdate(buildPdv("Bar da Alegria", "João Risada", "25.221.259/0001-93")),
				"Duplicate document");
		assertTrue(thrown.getMessage().contains("25221259"));
	}

	private PdvRequestDTO buildPdv(String tradingName, String ownerName, String document) {
		Map<String, Object> address = new HashMap<String, Object>();
		address.put("type", "Point");
		address.put("coordinates", Arrays.asList(-46.57421, -21.785741));

		List<List<Double>> pList1 = new ArrayList<List<Double>>();
		pList1.add(Arrays.asList(-43.521893, -23.00843));
		pList1.add(Arrays.asList(-43.52361, -23.029285));
		pList1.add(Arrays.asList(-43.43469, -23.051086));
		pList1.add(Arrays.asList(-43.367397, -23.019491));
		pList1.add(Arrays.asList(-43.366367, -22.973665));
		pList1.add(Arrays.asList(-43.388683, -22.973034));
		pList1.add(Arrays.asList(-43.393833, -22.949324));
		pList1.add(Arrays.asList(-43.508846, -22.961021));
		pList1.add(Arrays.asList(-43.521893, -23.00843));

		List<Object> multiPolygon = new ArrayList<Object>();
		multiPolygon.add(pList1);

		Map<String, Object> coverageArea = new HashMap<String, Object>();
		coverageArea.put("type", "MultiPolygon");
		coverageArea.put("coordinates", Arrays.asList(multiPolygon));

		PdvRequestDTO pdvRequestDTO = new PdvRequestDTO(tradingName, ownerName, document, address, coverageArea);

		return pdvRequestDTO;
	}

}
