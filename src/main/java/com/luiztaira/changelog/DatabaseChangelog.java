package com.luiztaira.changelog;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.ResourceUtils;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.luiztaira.domain.Pdv;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * Using mongobee, load initial data for tests
 * 
 * @author taira
 *
 */
@ChangeLog
public class DatabaseChangelog {

	@ChangeSet(order = "01", id = "loadPdvs", author = "luiz.taira")
	public void loadPdvs(final DB db) throws Exception {
		DBCollection col = db.getCollectionFromString(Pdv.COLLECTION_NAME);

		try {
			File file = ResourceUtils.getFile("classpath:pdvs.json");
			FileReader fileReader = new FileReader(file);						
			Object json = new JSONParser().parse(fileReader);
			JSONObject obj = (JSONObject) json;
			JSONArray pdvs = (JSONArray) obj.get("pdvs");
			for (int i = 0; i < pdvs.size(); i++) {
				JSONObject pdvJson = (JSONObject) pdvs.get(i);
				Map<String, Object> address = (Map<String, Object>) pdvJson.get("address");
				Map<String, Object> coverageArea = (Map<String, Object>) pdvJson.get("coverageArea");

				DBObject pdv = new BasicDBObject();
				pdv.put("tradingName", pdvJson.get("tradingName"));
				pdv.put("ownerName", pdvJson.get("ownerName"));
				pdv.put("document", pdvJson.get("document"));
				pdv.put("address", address);
				pdv.put("coverageArea", coverageArea);
				
				col.insert(pdv);
			}

		} catch (IOException | ParseException e) {
			throw new Exception("File not found or invalid file: " + e.getMessage());
		}
	}
}