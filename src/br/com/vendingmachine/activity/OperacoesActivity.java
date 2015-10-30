package br.com.vendingmachine.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OperacoesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.operacoes);
		
		Button botaoGerenciar = (Button) findViewById(R.id.botaoGerenciar);
		Button botaoAlocar = (Button) findViewById(R.id.botaoAlocar);
		Button botaoDesalocar = (Button) findViewById(R.id.botaoDesalocar);
		
		botaoGerenciar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent irParaListaclientes = new Intent(OperacoesActivity.this,ListaClienteActivity.class);
				startActivity(irParaListaclientes);
				
			}
		});
		
		botaoAlocar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent irParaListaMaquinasNaoAlocadas = new Intent(OperacoesActivity.this,ListaMaquinasPendentesActivity.class);
				startActivity(irParaListaMaquinasNaoAlocadas);
				
			}
		});
		
		botaoDesalocar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent irParaListaMaquinasDesalocadas = new Intent(OperacoesActivity.this,ListaMaquinasPendentesDesalocaActivity.class);
				startActivity(irParaListaMaquinasDesalocadas);
				
			}
		});
		
	}
	
	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}

}
