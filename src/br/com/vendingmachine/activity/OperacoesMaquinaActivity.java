package br.com.vendingmachine.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.vendingmachine.domain.Maquina;

public class OperacoesMaquinaActivity extends Activity{

	private Maquina maquina;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.operacoesmaquina);
		
		// Recupera a maquina selecionada
		Intent intent = getIntent();
		maquina = (Maquina) intent.getSerializableExtra("Maquina");
		
		
		// Abrir a máquina
		Button botaoAbrir = (Button) findViewById(R.id.botaoAbrir);

		botaoAbrir.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent IrParaAberturaMaquina = new Intent(OperacoesMaquinaActivity.this, AbrirMaquinaActivity.class);
				IrParaAberturaMaquina.putExtra("AbreMaquina", maquina);
				startActivity(IrParaAberturaMaquina);
			}
		});

		Button botaoFechar = (Button) findViewById(R.id.botaoFechar);

		botaoFechar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent irParaFecharMaquina = new Intent(OperacoesMaquinaActivity.this,FecharMaquinaActivity.class);
				
				startActivity(irParaFecharMaquina);

			}
		});

		Button botaoManutencao = (Button) findViewById(R.id.botaoManutencao);

		botaoManutencao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent irParaFormularioManutencao = new Intent(OperacoesMaquinaActivity.this,ManutencaoActivity.class);
				startActivity(irParaFormularioManutencao);
			}
		});
	}

}
