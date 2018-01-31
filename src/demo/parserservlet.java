package demo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@WebServlet("/parserservlet")
public class parserservlet extends HttpServlet {
	static PrintWriter out;
	private static final long serialVersionUID = 1L;

	public parserservlet() {
		super();
	}

	public static void incrementValue(JSONObject obj) {
		@SuppressWarnings("unchecked")
		Set<String> keys = obj.keySet();
		for (String key : keys) {
			Object ob = obj.get(key);
			String keyStr = (String) key;

			System.out.println(keyStr);
			out.println(keyStr);
			if (!(ob instanceof JSONObject)) {
				System.out.println(ob);
			}
			if (ob instanceof JSONObject) {
				incrementValue((JSONObject) ob);
			}

			else if (ob instanceof JSONArray) {
				JSONArray arr = (JSONArray) ob;
				System.out.println(ob);
				for (int i = 0; i < arr.size(); i++) {
					Object arrObj = arr.get(i);
					if (arrObj instanceof JSONObject) {
						incrementValue((JSONObject) arrObj);

					}
				}
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		JSONParser parser = new JSONParser();
		try {
			Object object = parser.parse(new FileReader("D:/Bineet_Resume.json"));

			// convert Object to JSONObject
			JSONObject jo1 = (JSONObject) object;
			JSONObject jo2 = (JSONObject) jo1.get("Resume");

			JSONObject jo4 = (JSONObject) jo2.get("StructuredXMLResume");
			JSONObject jo5 = (JSONObject) jo4.get("ContactInfo");
			JSONObject jo6 = (JSONObject) jo5.get("PersonName");

			out.println(
					"<body style='width: 100%; background-image: url(images/back.jpg); background-repeat: no-repeat; background-size: cover;'>");
			out.println("<h2 class='fs-title'>Create Account</h2>"
					+ "<h3 class='fs-subtitle'>Please enter your basic details for account creation.</h3><hr>");
			for (Object key : jo6.keySet()) {
				String keyStr = (String) key;
				Object keyvalue = jo6.get(keyStr);

				out.println("<div class='col-md-2'>" + "<label>" + keyStr + "</label></div>"
						+ "<div class='col-md-4'><input type='text' name='fname' value= " + keyvalue + " /></div>");
			}
			JSONArray ja1 = (JSONArray) jo5.get("ContactMethod");
			for (int i = 0; i < ja1.size(); i++) {
				JSONObject jo7 = (JSONObject) ja1.get(i);
				incrementValue(jo7);

			}

		}

		catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
