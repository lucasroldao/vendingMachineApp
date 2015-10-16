package br.com.vendingmachine.converter;

import org.json.JSONException;
import org.json.JSONStringer;

public class LoginConverter {
	
	// Método responsável por converter os dados do login em formato JSON
	public String toJSON(String loginUsuario, String senhaUsuario) {
		JSONStringer js = new JSONStringer();
	
		try {
			
			js.object();
			js.key("login").value(loginUsuario);
			js.key("senha").value(senhaUsuario);
			js.endObject();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return js.toString();
	}
	
	

}
