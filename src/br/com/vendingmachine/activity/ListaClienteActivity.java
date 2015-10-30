package br.com.vendingmachine.activity;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.com.vendingmachine.domain.Cliente;
import br.com.vendingmachine.task.ListaClienteTask;
import br.com.vendingmachine.util.ListaInterface;

public class ListaClienteActivity extends Activity implements ListaInterface {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lista_cliente);
		
		ListaClienteTask listaClientetask = new ListaClienteTask(this,this);
		listaClientetask.execute();
		
	}
	
	public void carregaLista(List<Cliente> lista){
		ListView listaCliente = (ListView) findViewById(R.id.lista_cliente);
		if (lista.size() > 0) {
			final ArrayAdapter<Cliente> adapterCliente = new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, lista);
			listaCliente.setAdapter(adapterCliente);
			
			listaCliente.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					Cliente clienteSelecionado = adapterCliente.getItem(position);
					
					if(clienteSelecionado != null){
						Intent irParaListarMaquinas = new Intent(
								ListaClienteActivity.this, ListaMaquinaActivity.class);
						
						irParaListarMaquinas.putExtra("Cliente",clienteSelecionado);
						startActivity(irParaListarMaquinas);
					}
	
				}
			});
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this)
			.setTitle("Aviso")
			.setMessage("Não existem máquinas alocadas para nenhum cliente.")
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent irParaOperacoes = new Intent(ListaClienteActivity.this,OperacoesActivity.class);
					startActivity(irParaOperacoes);
					finish();
				}
			});
			builder.create().show();
		}
	}

}
