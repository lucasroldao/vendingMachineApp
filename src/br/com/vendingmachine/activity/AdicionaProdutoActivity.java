package br.com.vendingmachine.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import br.com.vendingmachine.domain.Produto;
import br.com.vendingmachine.service.ProdutoService;
import br.com.vendingmachine.validator.ValidaCampo;

public class AdicionaProdutoActivity extends Activity {
	
	private ArrayList<Produto> listaTipoProdutos;
	private EditText quantidade;
	private EditText valorTotal;
	private EditText valorUnitario;
	private EditText valorVenda;
	private static Produto produto;
	private boolean quantidadeValida;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.incluir_produto);
		
		// Busca campos
		valorTotal = (EditText) findViewById(R.id.total);
		valorUnitario = (EditText) findViewById(R.id.valorUnitario);
		valorVenda = (EditText) findViewById(R.id.valorVenda);
		
		Intent intent = getIntent();
		Bundle args = intent.getBundleExtra("BundleProdutos");
		listaTipoProdutos = (ArrayList<Produto>) args.getSerializable("Produtos");
		
		
		List<String> produtosSpinner = new ArrayList<String>();
		
		for (Produto p : listaTipoProdutos){
			produtosSpinner.add(p.getDescricao());
		}
		
		Spinner spinerProduto = (Spinner) findViewById(R.id.spinnerProdutos);
		
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line,produtosSpinner);
		
		spinerProduto.setAdapter(adapter);
		
		spinerProduto.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				produto = listaTipoProdutos.get(position);
				
				valorUnitario.setText(produto.getValorUnitario().toString());
				valorVenda.setText(produto.getPrecoVenda().toString());
				
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Toast.makeText(AdicionaProdutoActivity.this, "É necessário selecionar ao menos um produto", Toast.LENGTH_SHORT).show();
			}

		});
		Button botaoAdicionaProduto = (Button) findViewById(R.id.botaoAdicionarProduto);
		
		botaoAdicionaProduto.setOnClickListener(new OnClickListener() {
			Double resultado;
			
			@Override
			public void onClick(View v) {
				quantidade = (EditText) findViewById(R.id.quantidade);
				
				
				// Valida se é diferente de nulo e diferente de 0
				quantidadeValida = ValidaCampo.validateNotNull(quantidade, "Preencha o campo quantidade");
				
				if(quantidadeValida == true){
					
					boolean quantidadeDiferenteZero = false; 
					
					quantidadeDiferenteZero = ValidaCampo.validateNotZero(quantidade, "O campo quantidade deve ser diferente de 0.");
					
					if (quantidadeDiferenteZero == true) {
						produto.setQuantidade(Integer.parseInt(quantidade.getText().toString()));
						resultado = produto.getPrecoVenda() * produto.getQuantidade();
						valorTotal.setText(resultado.toString());
						produto.setValorTotal(resultado);
						
						Produto buscarProduto = ProdutoService.buscar(produto.getCodigo());
						
						if(buscarProduto == null){
							ProdutoService.salvar(produto);
							finish();
						} else {
							AlertDialog.Builder builder = new AlertDialog.Builder(AdicionaProdutoActivity.this)
							.setTitle("Erro")
							.setMessage("Produto já existente na lista.")
							.setPositiveButton("OK", null);
							builder.create().show();
						}
					}
				}
			}
		});
	}
}