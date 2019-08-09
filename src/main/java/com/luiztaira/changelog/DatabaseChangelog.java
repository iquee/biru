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
import com.luiztaira.domain.Restaurant;
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

	@ChangeSet(order = "01", id = "loadRestaurants", author = "luiz.taira")
	public void loadRestaurants(final DB db) throws Exception {
		DBCollection col = db.getCollectionFromString(Restaurant.COLLECTION_NAME);

		try {
			File file = ResourceUtils.getFile("classpath:restaurants.json");
			FileReader fileReader = new FileReader(file);						
			Object json = new JSONParser().parse(fileReader);
			JSONObject obj = (JSONObject) json;
			JSONArray restaurrants = (JSONArray) obj.get("restaurants");
			for (int i = 0; i < restaurrants.size(); i++) {
				JSONObject rJson = (JSONObject) restaurrants.get(i);
				Map<String, Object> address = (Map<String, Object>) rJson.get("address");
				Map<String, Object> coverageArea = (Map<String, Object>) rJson.get("coverageArea");

				DBObject r = new BasicDBObject();
				r.put("fantasyName", rJson.get("fantasyName"));
				r.put("ownerName", rJson.get("ownerName"));
				r.put("document", rJson.get("document"));
				r.put("address", address);
				r.put("coverageArea", coverageArea);
				
				col.insert(r);
			}

		} catch (IOException | ParseException e) {
			throw new Exception("File not found or invalid file: " + e.getMessage());
		}
	}
}