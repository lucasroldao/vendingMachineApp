package br.com.vendingmachine.activity;

import java.util.List;

import br.com.vendingmachine.adapter.AlocacaoAdapter;
import br.com.vendingmachine.domain.Alocacao;
import br.com.vendingmachine.domain.Cliente;
import br.com.vendingmachine.domain.Maquina;
import br.com.vendingmachine.task.ListarMaquinasPendentesTask;
import br.com.vendingmachine.util.ListaMaquinaInterface;
import br.com.vendingmachine.util.ListaMaquinaPendenteInterface;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListaMaquinasPendentesActivity extends Activity implements ListaMaquinaPendenteInterface {

	private ListView listaAlocacao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lista_alocacao);
		listaAlocacao = (ListView) findViewById(R.id.lista_alocacoes);
		
		// Task para buscar máquinas pendentes
		ListarMaquinasPendentesTask task = new ListarMaquinasPendentesTask(this,this);
		task.execute();
		
	}
	
	@Override
	public void carregaLista(List<Alocacao> listaalocacao) {
		
		if (listaalocacao.size() > 0) {
			// Coloca as informações da lista na tela
			final AlocacaoAdapter adapterAlocacao = new AlocacaoAdapter(listaalocacao, this);
			
			listaAlocacao.setAdapter(adapterAlocacao);
			
			listaAlocacao.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					Alocacao alocacaoSelecionada = (Alocacao) adapterAlocacao.getItem(position);
					
					if(alocacaoSelecionada != null){
						
						Intent irParaFormularioAlocarMaquina = new Intent(
								ListaMaquinasPendentesActivity.this,
								AlocarMaquinaActivity.class);
						
						irParaFormularioAlocarMaquina.putExtra("Alocacao",alocacaoSelecionada);
		
						startActivity(irParaFormularioAlocarMaquina);
					}

				}
			});
			
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this)
					.setTitle("Aviso")
					.setMessage("Não existem solicitações de alocação pendentes.")
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent irParaOperacoes = new Intent(ListaMaquinasPendentesActivity.this,OperacoesActivity.class);
							startActivity(irParaOperacoes);
							finish();
						}
					});
			builder.create().show();
		}
		
	}
	
}
