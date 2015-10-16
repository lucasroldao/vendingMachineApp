package br.com.vendingmachine.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import br.com.vendingmachine.domain.Alocacao;
import br.com.vendingmachine.task.ConfirmaAlocacaoTask;
import br.com.vendingmachine.util.AlocacaoInterface;

public class AlocarMaquinaActivity extends Activity implements AlocacaoInterface {

	private TextView textNomeClienteAloc;
	private TextView textEnderecoClienteAloc;
	private TextView textNumero;
	private TextView textTelefone;
	private TextView codigoMaquinaAloc;
	private TextView modeloMaquina;
	private TextView dataAlocacao;
	private Alocacao alocacao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.alocar_maquina);
		
		textNomeClienteAloc = (TextView) findViewById(R.id.textNomeClienteAloc);
		textEnderecoClienteAloc = (TextView) findViewById(R.id.textEnderecoClienteAloc);
		textNumero = (TextView) findViewById(R.id.textNumero);
		textTelefone = (TextView) findViewById(R.id.textTelefone);
		codigoMaquinaAloc = (TextView) findViewById(R.id.descricaoMaquinaAloc);
		modeloMaquina = (TextView) findViewById(R.id.modeloMaquina);
		dataAlocacao = (TextView) findViewById(R.id.dataAlocacao);
		
		Intent intent = getIntent();
		alocacao = (Alocacao) intent.getSerializableExtra("Alocacao");
		
		textNomeClienteAloc.setText(alocacao.getCliente().getNomeFantasia());
		textEnderecoClienteAloc.setText(alocacao.getCliente().getLogradouro());
		textNumero.setText(alocacao.getCliente().getNumero().toString());
		textTelefone.setText(alocacao.getCliente().getTelefone());
		codigoMaquinaAloc.setText(alocacao.getMaquina().getCodigo());
		modeloMaquina.setText(alocacao.getMaquina().getModelo());
		dataAlocacao.setText(alocacao.getDataSolicitacao());
		
		Button confirmarAlocacao = (Button) findViewById(R.id.confirmaAlocacaoMaquina);
		
		confirmarAlocacao.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				ConfirmaAlocacaoTask task = new ConfirmaAlocacaoTask(AlocarMaquinaActivity.this,AlocarMaquinaActivity.this,alocacao);
				task.execute();
			}
		});

	}

	@Override
	public void carregaTelaOperacoes() {
		//Carrega tela de operações
		Intent irParaOperacao = new Intent(this,OperacoesActivity.class);
		startActivity(irParaOperacao);
	}
}
