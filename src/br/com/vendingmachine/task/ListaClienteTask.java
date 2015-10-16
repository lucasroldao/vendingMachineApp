package br.com.vendingmachine.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import br.com.vendingmachine.domain.Cliente;
import br.com.vendingmachine.util.ListaInterface;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ListaClienteTask extends AsyncTask<Void, Void, List<Cliente>> {

	private static final int TIME_OUT = 5000;
	private ProgressDialog pDialog;
	private Context context;
	private ListaInterface li;
	private int codigo;

	public ListaClienteTask(Context context, ListaInterface li) {
		this.context = context;
		this.li = li;
	}

	@Override
	protected void onPreExecute() {
		pDialog = ProgressDialog.show(context, "Aguarde...",
				"Carregando clientes", true, true);
	}

	@Override
	protected List<Cliente> doInBackground(Void... params) {
		
		String urlServer = "http://servidorprincipal.net/vendingmachine/rest/clientes/listar";
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		HttpURLConnection urlConnection = null;
	    System.setProperty("http.keepAlive", "false");
	    
		try {
	        URL url = new URL(urlServer);
	        urlConnection = (HttpURLConnection) url.openConnection();
	        urlConnection.setConnectTimeout(TIME_OUT);
	        urlConnection.setReadTimeout(TIME_OUT);
	        urlConnection.setRequestMethod("GET");
	        urlConnection.setRequestProperty("Accept", "application/json");
	        urlConnection.connect();
	        codigo = urlConnection.getResponseCode();
	        InputStream inputStream = urlConnection.getInputStream();
	        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	        
	        if(inputStream != null && codigo == HttpURLConnection.HTTP_OK) {
		        
	        	//Le a resposta do WS
	        	BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		        StringBuilder sb = new StringBuilder();
		        String output;
		         
		        // Lê cada linha do JSON que será concatenado a string
		        while ((output = br.readLine()) != null) {
		        	sb.append(output);
		        }
		         String stringJson = sb.toString();
		        
		        // Define o tipo que será utilizado na API Gson
		        Type listType = new TypeToken<ArrayList<Cliente>>() {
                }.getType();
                
                // Converte o JSON recebido em uma lista de clientes
		        clientes = gson.fromJson(stringJson, listType);
		        
	        }
	        
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	        Log.e("Erro na url requisitada", e.getMessage());
	    } catch (IOException e) {
	    	e.printStackTrace();
	        Log.e("Erro ao acessar web service", e.getMessage());
	    } finally {
	        if (urlConnection != null) {
	            urlConnection.disconnect();
	        }
	    }

		return clientes;
	}

	@Override
	protected void onPostExecute(List<Cliente> listaClientes) {
		pDialog.dismiss();
		li.carregaLista(listaClientes);
	}
}
