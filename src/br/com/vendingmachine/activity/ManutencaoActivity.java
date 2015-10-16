package br.com.vendingmachine.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ManutencaoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.manutencao);
		
		String[] motivos = {"Manutenção preventiva","Trocar de peças","Defeito"};
		
		Spinner spinnerMotivos = (Spinner) findViewById(R.id.spinnerMotivos);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, motivos);
		
		spinnerMotivos.setAdapter(adapter);
		
	
		
	}

}
