package br.com.vendingmachine.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.vendingmachine.adapter.MaquinaAdapter;
import br.com.vendingmachine.domain.Cliente;
import br.com.vendingmachine.domain.Maquina;
import br.com.vendingmachine.task.ListaMaquinasTask;
import br.com.vendingmachine.util.ListaMaquinaInterface;

public class ListaMaquinaActivity extends Activity implements ListaMaquinaInterface {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lista_maquina);
		
		// Recupera o cliente selecionado
		Intent intent = getIntent();
		Cliente cliente = (Cliente) intent.getSerializableExtra("Cliente");
		
		
		//Dispara a task
		ListaMaquinasTask task = new ListaMaquinasTask(this,this,cliente);
		task.execute();
		
	}

	@Override
	public void carregaLista(List<Maquina> listaMaquina) {
		ListView listaMaquinas = (ListView) findViewById(R.id.lista_maquinas);
		
		final MaquinaAdapter adapterMaquina = new MaquinaAdapter(listaMaquina, this);
		
		listaMaquinas.setAdapter(adapterMaquina);
		
		listaMaquinas.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Maquina maquinaSelecionada = adapterMaquina.getItem(position);
				
				Intent irParaOperacoes = new Intent(ListaMaquinaActivity.this, OperacoesMaquinaActivity.class);
				irParaOperacoes.putExtra("Maquina",maquinaSelecionada);
				
				startActivity(irParaOperacoes);
				
			}
		});
		
	}
	
}
