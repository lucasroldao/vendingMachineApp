package br.com.vendingmachine.task;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.vendingmachine.activity.OperacoesActivity;
import br.com.vendingmachine.domain.Maquina;
import br.com.vendingmachine.domain.Produto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;


public class EnviaDespesaTask extends AsyncTask<Void, Void, Integer> {
	
	private Context context;
	private List<Produto> produtos;
	private ProgressDialog pDialog;
	private Maquina maquinaParaAbertura;
	
	public EnviaDespesaTask(Context context, List<Produto> produtos, Maquina maquinaParaAbertura) {
		this.context = context;
		this.produtos = produtos;
		this.maquinaParaAbertura = maquinaParaAbertura;
	}
	
	@Override
	protected void onPreExecute() {
		pDialog = ProgressDialog.show(context, "Aguarde...",
				"Processando abertura da máquina", true, true);
	}
	

	@SuppressWarnings("null")
	@Override
	protected Integer doInBackground(Void... params) {
		String urlServer = "http://servidorprincipal.net/vendingmachine/rest/despesas/cadastrar/" + maquinaParaAbertura.getId();
		HttpURLConnection urlConnection = null;
	    System.setProperty("http.keepAlive", "false");
		Integer codigo = null;
		String json;
		
		try {
			
			URL url = new URL(urlServer);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("PUT");
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setDoOutput(true);
			
	        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			// Define o tipo que será utilizado na API Gson
			Type listType = new TypeToken<ArrayList<Produto>>() {
			}.getType();
			
			if(produtos.size() > 1){
				// Converte a lista de produtos em uma lista de objetos JSON
				JsonElement jsonProdutos = gson.toJsonTree(produtos,listType);
				json = jsonProdutos.toString();
				
			} else {
				json = gson.toJson(produtos);
			}
			
			OutputStreamWriter out = new OutputStreamWriter(
					urlConnection.getOutputStream());
				out.write(json);
				out.close();
				urlConnection.getInputStream();
				
			codigo = urlConnection.getResponseCode();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return codigo;
	}
	
	@Override
	protected void onPostExecute(Integer codigo) {
		pDialog.dismiss();
		if (codigo == HttpURLConnection.HTTP_OK){
			Toast.makeText(context,"Operação de abertura realizada com sucesso!", Toast.LENGTH_LONG).show();
			Intent irParaTelaOperacoes = new Intent(context,
					OperacoesActivity.class);
			context.startActivity(irParaTelaOperacoes);
		}
		else if (codigo == HttpURLConnection.HTTP_INTERNAL_ERROR){
			Toast.makeText(context,"Não foi possivel realizar a operação de abertura da máquina.", Toast.LENGTH_LONG).show();
		}
	}

}
