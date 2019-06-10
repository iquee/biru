package com.luiztaira.pdv;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import com.luiztaira.exception.PdvException;
import com.luiztaira.service.PdvService;
import com.luiztaira.web.rest.dto.PdvRequestDTO;
import com.luiztaira.web.rest.dto.PdvResponseDTO;

@SpringBootTest
public class PdvTest {

	private static PdvService pdvService;
	private static JSONObject obj;

	@Autowired
	public PdvTest(PdvService pdvService) {
		PdvTest.pdvService = pdvService;
	}

	@BeforeAll
	public static void loadFileToTest() {

		// parse file pdvs.json
		// file imported from: https://github.com/ZXVentures/code-challenge/edit/master/files/pdvs.json
		// pdv ID's removed from file. New ones will be created
		Object file;
		try {
			ClassLoader classLoader = PdvTest.class.getClassLoader();
			URL resource = classLoader.getResource("pdvs.json");
			FileReader fileReader = new FileReader(new File(resource.getFile()));
			file = new JSONParser().parse(fileReader);
		} catch (IOException | ParseException e) {
			throw new PdvException("File not found or invalid file: " + e.getMessage());
		}
		PdvTest.obj = (JSONObject) file;
	}

	// checked tests
	@Test
	public void savePdvs() {
		// getting pdvs and save at mongo
		JSONArray pdvs = (JSONArray) PdvTest.obj.get("pdvs");
		int size = pdvs.size();
		int created = 0;
		for (int i = 0; i < pdvs.size(); i++) {
			JSONObject pdv = (JSONObject) pdvs.get(i);
			Map<String, Object> address = (Map<String, Object>) pdv.get("address");
			Map<String, Object> coverageArea = (Map<String, Object>) pdv.get("coverageArea");
			pdvService.createOrUpdate(new PdvRequestDTO(pdv.get("tradingName").toString(),
					pdv.get("ownerName").toString(), pdv.get("document").toString(), address, coverageArea));
			created++;
		}
		
		assertThat(size, equalTo(created));
	}
	@Test
	public void testCreatePdv() throws Exception {
		Long id = pdvService.createOrUpdate(buildPdv("Boteco Legal", "Zé Sorriso", "29.165.498/0001-24"));
		
		assertNotNull(id);
	}

	@Test
	public void testGetPdvById() throws Exception {
		Long id = pdvService.createOrUpdate(buildPdv("Bar da Alegria", "João Risada", "25.221.259/0001-93"));
		PdvResponseDTO pdv = pdvService.getById(id);
		
		assertThat(pdv.getTradingName(), equalTo("Bar da Alegria"));
		assertThat(pdv.getOwnerName(), equalTo("João Risada"));
	}

	@Test
	public void testSearchNearestPdv() throws Exception {
		List<Double> coordinates = new ArrayList<>();
		coordinates.add(-49.379279);
		coordinates.add(-20.816612);		
		PdvResponseDTO pdv = pdvService.search(coordinates);
		
		assertThat(pdv.getTradingName(), equalTo("Bar Nem Tanto"));		
	}

	// fail tests
	@Test
	public void testSavePdvExistedDocument() throws PdvException {
		DuplicateKeyException thrown = assertThrows(DuplicateKeyException.class,
				() -> pdvService.createOrUpdate(buildPdv("Bar da Alegria", "João Risada", "25.221.259/0001-93")),
				"Duplicate document");
		assertTrue(thrown.getMessage().contains("25221259"));
	}
	
	@Test
	public void testPdvNotFound() throws PdvException {
		PdvException thrown = assertThrows(PdvException.class,
				() -> pdvService.getById(-1L), "No pdv found for id: -1");
		assertTrue(thrown.getMessage().contains("No pdv found for id: -1"));
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
