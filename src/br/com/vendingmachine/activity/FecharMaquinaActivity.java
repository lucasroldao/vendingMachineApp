package br.com.vendingmachine.activity;

import java.util.ArrayList;
import java.util.List;

import br.com.vendingmachine.adapter.ProdutoAdapter;
import br.com.vendingmachine.domain.Produto;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

public class FecharMaquinaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fechar_maquina);
		
		List<Produto> produtos = new ArrayList<Produto>();
		/*
		produtos.add(new Produto("Chiclete", 1.00, 2.00));
		produtos.add(new Produto("Bola 45mm", 1.90, 4.00));
		produtos.add(new Produto("Cereal", 2.60, 2.00));
		produtos.add(new Produto("Salgadinho", 3.00, 2.00));
		produtos.add(new Produto("Chiclete", 3.00, 5.00));
		produtos.add(new Produto("Chiclete", 3.00, 5.00));
		produtos.add(new Produto("Chiclete", 3.00, 9.00));
		*/
		ListView listaProdutos = (ListView) findViewById(R.id.listaProdutos);
		
		ProdutoAdapter adapter = new ProdutoAdapter(produtos, this);
		
		listaProdutos.setAdapter(adapter);
	}

}
