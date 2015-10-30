package br.com.vendingmachine.activity;

import java.util.ArrayList;
import java.util.List;

import br.com.vendingmachine.adapter.ProdutoAdapter;
import br.com.vendingmachine.domain.Cliente;
import br.com.vendingmachine.domain.Maquina;
import br.com.vendingmachine.domain.Produto;
import br.com.vendingmachine.service.ProdutoService;
import br.com.vendingmachine.task.GeraFinanceiroTask;
import br.com.vendingmachine.task.ObtemTipoProdutoTask;
import br.com.vendingmachine.util.MaquinaInterface;
import br.com.vendingmachine.util.OperacaoInterface;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class FecharMaquinaActivity extends Activity implements MaquinaInterface,OperacaoInterface {
	
	ListView listaProdutos;
	Produto produto;
	Maquina maquinaParaFechamento;
	ProdutoService produtoService;
	private Cliente cliente;
	static TextView valorTotalProdutos;
	
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.abrir_maquina);
		TextView txtTitulo = (TextView) findViewById(R.id.txtMaquina);
		TextView txtCodigoMaquina = (TextView) findViewById(R.id.txtCodigoMaquina);
		TextView txtModeloMaquina = (TextView) findViewById(R.id.txtModeloMaquina);
		valorTotalProdutos = (TextView) findViewById(R.id.valorTotalProdutos);
		Button botaoEmitirReceita = (Button) findViewById(R.id.emitirDespesa);
		
		Intent intent = getIntent();
		maquinaParaFechamento = (Maquina) intent.getSerializableExtra("FechaMaquina");
		cliente = (Cliente) intent.getSerializableExtra("Cliente");
		
		txtTitulo.setText("Fechar Máquina");
		txtCodigoMaquina.setText(maquinaParaFechamento.getCodigo());
		txtModeloMaquina.setText(maquinaParaFechamento.getModelo());
		valorTotalProdutos.setTextColor(R.color.Red);
		
		ObtemTipoProdutoTask ObtemTipoProdutoTask = new ObtemTipoProdutoTask(this,this,maquinaParaFechamento,cliente);
		ObtemTipoProdutoTask.execute();
		
		listaProdutos = (ListView) findViewById(R.id.lista_produtos);
		
        registerForContextMenu(listaProdutos);

        listaProdutos.setOnItemLongClickListener(new OnItemLongClickListener(){
        	
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
				
				//Guardo o aluno selecionado
				produto = (Produto) adapter.getItemAtPosition(posicao);
				
				return false;
			}

		});
		
        botaoEmitirReceita.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final List<Produto> produtos = ProdutoService.listaProduto; 

				if(!produtos.isEmpty()){
					AlertDialog.Builder builder = new AlertDialog.Builder(FecharMaquinaActivity.this)
					.setTitle("Fechar máquina")
					.setMessage("Confirmar operação de fechamento?")
					.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							GeraFinanceiroTask enviaDespesaTask = new GeraFinanceiroTask(FecharMaquinaActivity.this,FecharMaquinaActivity.this,produtos,maquinaParaFechamento,"F");
							enviaDespesaTask.execute();
						}
					})
					.setNegativeButton("Não",null);
					builder.create().show();
				} else {
					Toast.makeText(FecharMaquinaActivity.this, "É necessário adicionar pelo menos um produto na máquina.", Toast.LENGTH_SHORT).show();;
				}
				
			}
		});
        
	}

	@Override
	public void carregaFormularioProduto(final ArrayList<Produto> produtos) {
		
		Button btnAdicionarProduto = (Button) findViewById(R.id.adicionarProduto);
		
		btnAdicionarProduto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Abre o formulário para adicionar produtos passando a lista de produtos que podem ser adicionados na máquina.
				Intent abreFormularioproduto = new Intent(FecharMaquinaActivity.this,AdicionaProdutoFechamentoActivity.class);
				Bundle args = new Bundle();
				args.putSerializable("Produtos",produtos);
				abreFormularioproduto.putExtra("BundleProdutos",args);
				startActivity(abreFormularioproduto);
			}
		});
		
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		
		// Implementa a função de deletar no submenu
		MenuItem deletar = menu.add("Deletar");
		deletar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
			
				ProdutoService.remover(produto);
				carregaLista();
				
				return false;
			}

		});
		
		super.onCreateContextMenu(menu, v, menuInfo);
	}
    
    @Override
    protected void onResume() {
    	super.onResume();
    	carregaLista();	
    }
	
	private void carregaLista() {
		List<Produto> produtos = ProdutoService.listaProduto; 
        
		Double calculaValorTotal = ProdutoService.calculaValorTotal(produtos);
	    valorTotalProdutos.setText(calculaValorTotal.toString());
	    
	    ProdutoAdapter adapterProduto = new ProdutoAdapter(produtos,this);
		listaProdutos.setAdapter(adapterProduto);
		
	}

	@Override
	public void carregaTelaOperacoes() {
		Intent irParaTelaOperacoes = new Intent(FecharMaquinaActivity.this,
				OperacoesActivity.class);
		startActivity(irParaTelaOperacoes);
		finish();
		
	}

}
