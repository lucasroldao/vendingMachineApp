package br.com.vendingmachine.task;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import br.com.vendingmachine.activity.LoginActivity;
import br.com.vendingmachine.activity.OperacoesActivity;
import br.com.vendingmachine.activity.R;

// http://developer.android.com/reference/android/util/JsonReader.html
// http://stackoverflow.com/questions/5379247/filenotfoundexception-while-getting-the-inputstream-object-from-httpurlconnectio
public class LoginTask extends AsyncTask<Integer, Double, Integer> {

	private Activity context;
	EditText login;
	EditText senha;
	String motivo = null;
	private ProgressDialog pDialog;
	private int codigo;
	private final static int TIME_OUT = 5000;
	private final static String urlServidor = "http://servidorprincipal.net/vendingmachine/rest/login/";
	private final static String urlServidorLocal = "http://192.168.0.105:8080/vendingmachine/rest/login/";

	public LoginTask(Activity context, EditText login, EditText senha) {
		this.context = context;
		this.login = login;
		this.senha = senha;
	}
	@Override
	protected void onPreExecute() {
		pDialog = ProgressDialog.show(context, "Aguarde...", "Validando informações",true,true);
	}
	
	protected Integer doInBackground(Integer... params)  {
		String urlServer = urlServidor + login.getText().toString() + "/" + senha.getText().toString();
		
		HttpURLConnection urlConnection = null;
	    System.setProperty("http.keepAlive", "false");
	    
		try {
	        URL url = new URL(urlServer);
	        urlConnection = (HttpURLConnection) url.openConnection();
	        urlConnection.setConnectTimeout(TIME_OUT);
	        urlConnection.setReadTimeout(TIME_OUT);
	        urlConnection.setRequestMethod("GET");
	        urlConnection.connect();
	        codigo = urlConnection.getResponseCode();
	        
	        // Se o código do erro é 403 significa que o usuário retonado está bloqueado.
	        if(codigo == HttpURLConnection.HTTP_FORBIDDEN) {
		        InputStream inputStream = urlConnection.getErrorStream();
		        
		        if(inputStream != null) {
			        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
				    
			        reader.beginObject();
			        
				    while (reader.hasNext()) {
				    	String name = reader.nextName();
				        	if (name.equals("motivo-bloqueio")) {
				        		motivo = reader.nextString();
				        	}
			        }
			        reader.endObject();
			        reader.close();
			        inputStream.close();
		        }
	        }
	        
	        Log.i("Fez o request para o WS.","Codigo retornado: " + codigo);
	        
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	        Log.e("Erro na MalformedURLException", e.getMessage());
	    } catch (IOException e) {
	    	Log.i("Connection-timeout", "Connection timeout");
	    	codigo = HttpURLConnection.HTTP_CLIENT_TIMEOUT;
	        e.printStackTrace();
	    } finally {
	        if (urlConnection != null) {
	            urlConnection.disconnect();
	        }
	    }
		return codigo;
	}

	protected void onPostExecute(Integer codigo) {
		pDialog.dismiss();
		// Código 200 - Usuário e senha são validos
		if(codigo == HttpURLConnection.HTTP_OK){
			Intent irParaTelaOperacoes = new Intent(context,
					OperacoesActivity.class);
			context.startActivity(irParaTelaOperacoes);
			context.finish();
		} 
		// Codigo 403 - Usuário bloqueado no sistema.
		else if (codigo == HttpURLConnection.HTTP_FORBIDDEN){
			Toast.makeText(context,"Usuário bloquado. \nMotivo: " + motivo, Toast.LENGTH_LONG).show();
		}
		// Codigo 500 - Usuário ou senha inválidos.
		else if (codigo == HttpURLConnection.HTTP_INTERNAL_ERROR){
			Toast.makeText(context,"Informações de login/senha incorretas", Toast.LENGTH_LONG).show();
		}
		// Código 408 - Timeout
		else if (codigo == HttpURLConnection.HTTP_CLIENT_TIMEOUT){
			Toast.makeText(context,"Falha ao acessar servidor. Verifique sua conexão com a internet.", Toast.LENGTH_LONG).show();
		}
	}
}
