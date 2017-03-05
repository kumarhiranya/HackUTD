import facebook4j.*;
import facebook4j.auth.AccessToken;
import facebook4j.internal.http.RequestMethod;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

import java.io.*;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Recommender {
	public static void main(String args[]) throws IOException, FacebookException, JSONException {
		Facebook facebook = new FacebookFactory().getInstance();
		String appId = "1269210026501990";
		String appSecret = "59e7d2c83f2c92ca177bf12a66e3b539";
		facebook.setOAuthAppId(appId, appSecret);
		String accessToken = "EAACEdEose0cBAEcyao5wOnmUUL8zd6ZABKKl7P6sBV77MYWeKBdiopPgJVZA2ucoCXb7bTXsrVRWI7qvqNGfxzh4U3DHxLnYJXeTwnWpW7YOoF40fzjb2sZB8NHLPaCBCTDI7aIQVY7eNeQLsqcBA03GIZAKBkWOMWNbAIufxZBq1kBMuRwUv";
		facebook.setOAuthAccessToken(new AccessToken(accessToken, null));

		BatchRequests<BatchRequest> batch = new BatchRequests<BatchRequest>();

		batch.add(new BatchRequest(RequestMethod.GET, "me?fields=name,location,birthday,events"));
		batch.add(new BatchRequest(RequestMethod.GET, "me/friends?fields=name,birthday"));
		batch.add(new BatchRequest(RequestMethod.GET, "me/events?fields=place"));
		
		List<BatchResponse> results = facebook.executeBatch(batch);

		BatchResponse result1 = results.get(0);
		BatchResponse result1_friend = results.get(1);
		BatchResponse place = results.get(2);
		
		JSONObject object = result1.asJSONObject();
		JSONObject object_friend = result1_friend.asJSONObject();
		JSONObject dataplace = place.asJSONObject();

		JSONObject locationObject = (JSONObject) object.get("location");
	
		String name = (String) object.get("name");
		String birthdaystring;

		if (object.has("birthday")) {
			birthdaystring = (String) object.get("birthday");
		} else {
			birthdaystring = "Birthday is not available";
		}
		String location = (String) locationObject.get("name");

		JSONObject event = (JSONObject) object.get("events");
		JSONArray events = (JSONArray) event.get("data");

		String des, st, id = null, et, lat = null, lon = null, zip = null, place_name = null, eventname, rsvp,
				city = null, country = null, state = null, event_data;

		File file = new File("D:/sasp-1.0.5/tempus/file.txt");

		if (!file.exists()) {
			file.createNewFile();
		}

		BufferedWriter bw = null;
		FileWriter fw = null;

		String friendname = null, friendbirthday = null;
		JSONArray data = (JSONArray) object_friend.get("data");
		int year = Integer.parseInt(birthdaystring.split("/")[2]);
		int currentYear = 2017;
		int age = currentYear - year;
		String content = null, content1 = null;

		content = "'" + name + "'" + "." + "\r\n" + "'" + location + "'" + "." + "\r\n" + "'" + birthdaystring + "'"
				+ "." + "\r\n" + "'" + age + "'" + ".";

		try {

			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(content);

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

		for (int i = 0; i < data.length(); i++) {

			JSONObject data1 = (JSONObject) data.get(i);

			if (data1.has("birthday")) {

				friendname = (String) data1.get("name");
				System.out.println(friendname);
				friendbirthday = (String) data1.get("birthday");

				System.out.println(friendbirthday);

				content1 = "\r\n" + "'" + friendname + "'" + "." + "\r\n" + "'" + friendbirthday + "'" + ".";

				try {

					fw = new FileWriter(file,true);
					bw = new BufferedWriter(fw);
					bw.write(content1);

				} catch (IOException e) {

					e.printStackTrace();

				} finally {

					try {

						if (bw != null)
							bw.close();

						if (fw != null)
							fw.close();

					} catch (IOException ex) {

						ex.printStackTrace();

					}

				}

			}
		}

		for (int i = 0; i < events.length(); i++) {

			JSONObject e = (JSONObject) events.get(i);

			if (e.has("description")) {

				des = (String) e.get("description");
			} else {
				des = "descirption not available";
			}
			if (e.has("name"))
				eventname = (String) e.get("name");
			else
				eventname = "Name not available";
			if (e.has("start_time"))
				st = (String) e.get("start_time");
			else
				st = "Start time not available";
			if (e.has("end_time"))
				et = (String) e.get("end_time");
			else
				et = "end_time not available";
			if (e.has("rsvp_status"))
				rsvp = (String) e.get("rsvp_status");
			else
				rsvp = "rsvp not available";
			if (e.has("id")) {
				id = (String) e.get("id");
			} else {
				id = "Id not available";
			}


			event_data = "\r\n" + "'" + des + "'" + "." + "\r\n" + "'" + eventname + "'" + "." + "\r\n" + "'" + st + "'"
					+ "." + "\r\n" + "'" + et + "'" + "." + "\r\n" + "'" + rsvp + "'" + ".";
			try {

				fw = new FileWriter(file,true);
				bw = new BufferedWriter(fw);
				bw.write(event_data);
				

			} catch (IOException e1) {

				e1.printStackTrace();

			} finally {

				try {

					if (bw != null)
						bw.close();

					if (fw != null)
						fw.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}

			}

		}
		
		System.out.println(dataplace.toString());
		
	}

}
