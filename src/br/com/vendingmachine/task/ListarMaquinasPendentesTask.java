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

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import br.com.vendingmachine.activity.OperacoesActivity;
import br.com.vendingmachine.domain.Alocacao;
import br.com.vendingmachine.util.ListaMaquinaPendenteInterface;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ListarMaquinasPendentesTask extends AsyncTask<Void, Void, ArrayList<Alocacao>> {

	private static final int TIME_OUT = 5000;
	private Context context;
	private ListaMaquinaPendenteInterface lmpi;
	private ProgressDialog pDialog;
	private int codigo;

	public ListarMaquinasPendentesTask(Context context, ListaMaquinaPendenteInterface lmpi) {
		this.context = context;
		this.lmpi = lmpi;
	}
	
	@Override
	protected void onPreExecute() {
		pDialog = ProgressDialog.show(context, "Aguarde...",
				"Carregando alocações pendentes", true, true);
	}

	@Override
	protected ArrayList<Alocacao> doInBackground(Void... params) {
		String urlServer = "http://servidorprincipal.net/vendingmachine/rest/alocacoes/listarpendentes";
		ArrayList<Alocacao> alocacoes = new ArrayList<Alocacao>();
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
		        Type listType = new TypeToken<ArrayList<Alocacao>>() {
                }.getType();
                
                // Converte o JSON recebido em uma lista de alocacoes pendentes
                alocacoes = gson.fromJson(stringJson, listType);
		        
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
	    
		return alocacoes;
	}
	
	protected void onPostExecute(ArrayList<Alocacao> listaalocacao) {
		pDialog.dismiss();
		if(codigo == HttpURLConnection.HTTP_OK || codigo == HttpURLConnection.HTTP_FORBIDDEN){
			lmpi.carregaLista(listaalocacao);
		} 
		else if (codigo == HttpURLConnection.HTTP_INTERNAL_ERROR){
			AlertDialog.Builder builder = new AlertDialog.Builder(context)
			.setTitle("Erro")
			.setMessage("Não foi possível carregar as solicitações pendentes.")
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(context,OperacoesActivity.class);
					context.startActivity(intent);
				}
			});
			builder.create().show();
		} 
		else if(codigo == HttpURLConnection.HTTP_CLIENT_TIMEOUT){
			AlertDialog.Builder builder = new AlertDialog.Builder(context)
			.setTitle("Erro")
			.setMessage("Não foi possível acessar o servidor. Verifique sua conexão.")
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(context,OperacoesActivity.class);
					context.startActivity(intent);
				}
			});
			builder.create().show();
		} 
	}
}
