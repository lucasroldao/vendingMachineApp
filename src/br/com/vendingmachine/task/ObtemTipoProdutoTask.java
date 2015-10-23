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
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import br.com.vendingmachine.activity.OperacoesActivity;
import br.com.vendingmachine.activity.OperacoesMaquinaActivity;
import br.com.vendingmachine.domain.Maquina;
import br.com.vendingmachine.domain.Produto;
import br.com.vendingmachine.util.MaquinaInterface;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ObtemTipoProdutoTask extends AsyncTask<Void, Void, ArrayList<Produto>> {
	
	private static final int TIME_OUT = 5000;
	private Maquina maquina;
	private MaquinaInterface ami;
	private Context context;
	private Dialog pDialog;
	private int codigo;

	public ObtemTipoProdutoTask(Context context,
			MaquinaInterface ami,
			Maquina maquina) {
		this.context = context;
		this.ami = ami;
		this.maquina = maquina;
	}
	
	@Override
	protected void onPreExecute() {
		pDialog = ProgressDialog.show(context, "Aguarde...",
				"Carregando informações da máquina", true, true);
	}
	@Override
	
	protected ArrayList<Produto> doInBackground(Void... params) {
		String urlServer = "http://servidorprincipal.net/vendingmachine/rest/maquinas/" + maquina.getId() + "/produtos";
		ArrayList<Produto> produtos = new ArrayList<Produto>();
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
		        Type listType = new TypeToken<ArrayList<Produto>>() {
                }.getType();
                
                // Converte o JSON recebido em uma lista de maquinas
                produtos = gson.fromJson(stringJson, listType);
		        
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
	    
		return produtos;
	}
	
	@Override
	protected void onPostExecute(ArrayList<Produto> listaProdutos) {
		pDialog.dismiss();
		if (listaProdutos.size() > 0) {
			ami.carregaFormularioProduto(listaProdutos);
		} 
		else if(codigo == HttpURLConnection.HTTP_CLIENT_TIMEOUT){
			AlertDialog.Builder builder = new AlertDialog.Builder(context)
			.setTitle("Erro")
			.setMessage("Não foi possível acessar o servidor. Verifique sua conexão.")
			.setPositiveButton("OK", null);
			builder.create().show();
		} 
		else {
			AlertDialog.Builder builder = new AlertDialog.Builder(context)
					.setTitle("Erro")
					.setMessage("Não foi possível carregar as informações!!")
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(context,OperacoesMaquinaActivity.class);
							intent.putExtra("Maquina", maquina);
							context.startActivity(intent);
						}
					});
			builder.create().show();
		}
	}

}
