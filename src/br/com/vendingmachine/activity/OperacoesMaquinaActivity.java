package br.com.vendingmachine.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.vendingmachine.domain.Cliente;
import br.com.vendingmachine.domain.Maquina;

public class OperacoesMaquinaActivity extends Activity{

	private static Maquina maquina;
	private Cliente cliente;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.operacoesmaquina);
		
		// Recupera a maquina e o cliente selecionados
		Intent intent = getIntent();
		maquina = (Maquina) intent.getSerializableExtra("Maquina");
		cliente = (Cliente) intent.getSerializableExtra("Cliente");
		
		// Abrir a máquina
		Button botaoAbrir = (Button) findViewById(R.id.botaoAbrir);

		botaoAbrir.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent IrParaAberturaMaquina = new Intent(OperacoesMaquinaActivity.this, AbrirMaquinaActivity.class);
				IrParaAberturaMaquina.putExtra("AbreMaquina", maquina);
				IrParaAberturaMaquina.putExtra("Cliente", cliente);
				startActivity(IrParaAberturaMaquina);
			}
		});

		Button botaoFechar = (Button) findViewById(R.id.botaoFechar);

		botaoFechar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent irParaFecharMaquina = new Intent(OperacoesMaquinaActivity.this,FecharMaquinaActivity.class);
				irParaFecharMaquina.putExtra("FechaMaquina", maquina);
				irParaFecharMaquina.putExtra("Cliente", cliente);
				startActivity(irParaFecharMaquina);

			}
		});

		Button botaoManutencao = (Button) findViewById(R.id.botaoManutencao);

		botaoManutencao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent irParaInformarRecolhimento = new Intent(OperacoesMaquinaActivity.this,InformarRecolhimentoActivity.class);
				irParaInformarRecolhimento.putExtra("RecolhimentoMaquina", maquina);
				irParaInformarRecolhimento.putExtra("Cliente", cliente);
				startActivity(irParaInformarRecolhimento);
			}
		});
	}
	
	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}

}
