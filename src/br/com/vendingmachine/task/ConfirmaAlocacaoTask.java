package br.com.vendingmachine.task;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.vendingmachine.domain.Alocacao;
import br.com.vendingmachine.util.OperacaoInterface;

public class ConfirmaAlocacaoTask extends AsyncTask<Void, Void, Integer> {

	private Context context;
	private ProgressDialog pDialog;
	private Alocacao alocacao;
	private OperacaoInterface ai;
	private final static int TIME_OUT = 5000;
	
	public ConfirmaAlocacaoTask(Context context, OperacaoInterface ai, Alocacao alocacao) {
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
			urlConnection.setConnectTimeout(TIME_OUT);
			urlConnection.setReadTimeout(TIME_OUT);
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
		} 
		else if(codigo == HttpURLConnection.HTTP_INTERNAL_ERROR){
			AlertDialog.Builder builder = new AlertDialog.Builder(context)
			.setTitle("Erro")
			.setMessage("Erro ao confirmar alocação")
			.setPositiveButton("OK", null);
			builder.create().show();
		} 
		else {
			Toast.makeText(context, "Erro ao confirmar alocação.", Toast.LENGTH_LONG).show();
		}
	}

}
