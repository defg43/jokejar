import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

class entry {

	public static String fetchJoke(String new_url_string) {
		String joke_url_string = "https://v2.jokeapi.dev/joke/Any?type=single";
		if(new_url_string != null) {
			joke_url_string = new_url_string;
		}
		try {
			URL joke_url = new URL(joke_url_string);
			HttpURLConnection connection = (HttpURLConnection) joke_url.openConnection();
			connection.setRequestMethod("GET");	
			int response_code = connection.getResponseCode();

			if (response_code == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			    StringBuilder response = new StringBuilder();
			    String line;
			    while ((line = reader.readLine()) != null) {
			    	response.append(line);
			    }
			    reader.close();
				connection.disconnect();

				JSONObject jsonObject = new JSONObject(response.toString());
                String joke = jsonObject.getString("joke");
                return "->" + joke;
			} else {
			    System.out.println("Failed to get a response");
			    connection.disconnect();
			    return null;
			}
		}
		catch(Exception e) {
			System.out.println("url: " + joke_url_string + " seems to not be working");
			System.out.println("exiting");
			System.exit(1);
			return null;
		}
	}
		
public static void main(String[] args) {
	Scanner scanner = new Scanner(System.in);
	String user_input;
	Boolean do_loop = true;

	System.out.println("small tool that gets random jokes from the internet");
	System.out.println("input j get a joke and q to quit");
	System.out.println("=================================");

	while(do_loop) {
		user_input = scanner.nextLine();
		switch(user_input) {
			case "q":
				do_loop = false;
				break;
			case "j":
			String to_display = fetchJoke(null);
			if(to_display != null) {
				System.out.println(to_display);
			} else {
				System.out.println("returned string was null");
			}
				break;
			default:
				System.out.println("unrecognized");
		}
	}
	scanner.close();	
}
}
