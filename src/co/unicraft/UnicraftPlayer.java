package co.unicraft;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UnicraftPlayer {
	private final Unicraft plugin;
	
	private String username;
	private String email;
	
	public UnicraftPlayer(String username, String email, Unicraft plugin) {
		this.username = username;
		this.email = email;
		this.plugin = plugin;
	}
	
	public boolean isActive() {
		return isActive(this.plugin.getConfig().getString("server"));
	}
	
	public boolean isActive(String host) {
		try {
			String server = host;
			URL url = new URL(server+"/api/minecraft_players/"+username+"?api_key="+getAPIKey());
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String status = reader.readLine();
			reader.close();
			
			return status.equals("OK");
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	public int create() {
		return create(this.plugin.getConfig().getString("server"));
	}
	
	public int create(String host) {
		try {
			String server = host;
			URL url = new URL(server+"/api/minecraft_players");
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false); 
			connection.setRequestMethod("POST"); 
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
			connection.setRequestProperty("charset", "utf-8");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(encoded_create_parameters().getBytes().length));
			connection.setUseCaches (false);
			
			DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
			wr.writeBytes(encoded_create_parameters());
		    wr.flush();
		    wr.close();
		    connection.disconnect();
		    
		    return connection.getResponseCode();
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	private String encoded_create_parameters() throws UnsupportedEncodingException {
		String api_key = getAPIKey();
		return "minecraft_player[username]=" + URLEncoder.encode(username, "UTF-8") +
		        "&minecraft_player[email]=" + URLEncoder.encode(email, "UTF-8") + "&api_key="+api_key;
	}
	
	private String getAPIKey() {
		try {
			return this.plugin.getConfig().getString("api_key");
		} catch(NullPointerException e) {
			return "secret";
		}
	}
}
