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
import br.com.vendingmachine.domain.Maquina;
import br.com.vendingmachine.util.ListaMaquinaInterface;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ListaMaquinasTask extends AsyncTask<Void, Void, List<Maquina>> {
	
	private static final int TIME_OUT = 5000;
	private Context context;
	private ListaMaquinaInterface lmi;
	private ProgressDialog pDialog;
	private Cliente cliente;
	private int codigo;
	
	public ListaMaquinasTask(Context context, ListaMaquinaInterface lmi, Cliente cliente) {
		this.context = context;
		this.lmi = lmi;
		this.cliente = cliente;
	}
	
	@Override
	protected void onPreExecute() {
		pDialog = ProgressDialog.show(context, "Aguarde...",
				"Carregando máquinas", true, true);
	}
	
	@Override
	protected List<Maquina> doInBackground(Void... params) {
		String urlServer = "http://servidorprincipal.net/vendingmachine/rest/clientes/" + cliente.getId() + "/maquinas";
		ArrayList<Maquina> maquinas = new ArrayList<Maquina>();
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
	        Gson gson = new Gson();
	        
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
		        Type listType = new TypeToken<ArrayList<Maquina>>() {
                }.getType();
                
                // Converte o JSON recebido em uma lista de maquinas
		        maquinas = gson.fromJson(stringJson, listType);
		        
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
	    
		return maquinas;
	}
	
	@Override
	protected void onPostExecute(List<Maquina> listaMaquinas) {
		pDialog.dismiss();
		if (listaMaquinas.size() > 0) {
			lmi.carregaLista(listaMaquinas);
		}
		else if (codigo == HttpURLConnection.HTTP_INTERNAL_ERROR ){
			AlertDialog.Builder builder = new AlertDialog.Builder(context)
			.setTitle("Erro")
			.setMessage("O cliente selecionado não possui máquinas alocadas")
			.setPositiveButton("OK", null);
			builder.create().show();
		}
		else {
			AlertDialog.Builder builder = new AlertDialog.Builder(context)
					.setTitle("Erro")
					.setMessage("Não foi possível acessar as informações!!")
					.setPositiveButton("OK", null);
			builder.create().show();
			
		}
	}
}
