package br.com.vendingmachine.task;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import br.com.vendingmachine.activity.AlocarMaquinaActivity;
import br.com.vendingmachine.activity.ListaMaquinasPendentesActivity;
import br.com.vendingmachine.activity.OperacoesActivity;
import br.com.vendingmachine.domain.Alocacao;
import br.com.vendingmachine.domain.Produto;
import br.com.vendingmachine.util.AlocacaoInterface;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class ConfirmaAlocacaoTask extends AsyncTask<Void, Void, Integer> {

	private Context context;
	private ProgressDialog pDialog;
	private Alocacao alocacao;
	private AlocacaoInterface ai;
	
	public ConfirmaAlocacaoTask(Context context, AlocacaoInterface ai, Alocacao alocacao) {
		this.context = context;
		this.alocacao = alocacao;
		this.ai = ai;
	}
	
	@Override
	protected void onPreExecute() {
		pDialog = ProgressDialog.show(context, "Aguarde...",
				"Confirmando alocação da máquina.", true, true);
	}
	
	@Override
	protected Integer doInBackground(Void... params) {
		String urlServer = "http://servidorprincipal.net/vendingmachine/rest/alocacoes/" + alocacao.getAlocacaoId() + "/alocar";
		HttpURLConnection urlConnection = null;
	    System.setProperty("http.keepAlive", "false");
		Integer codigo = null;
		
		try {
			
			URL url = new URL(urlServer);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.connect();
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
		if (codigo == HttpURLConnection.HTTP_OK) {
			Toast.makeText(context, "Alocação confirmada com sucesso!", Toast.LENGTH_LONG).show();
			ai.carregaTelaOperacoes();
		} else {
			Toast.makeText(context, "Erro ao confirmar alocação.", Toast.LENGTH_LONG).show();
		}
	}

}
