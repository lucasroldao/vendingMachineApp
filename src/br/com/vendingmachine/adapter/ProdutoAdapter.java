package br.com.vendingmachine.adapter;

import java.util.List;

import br.com.vendingmachine.activity.R;
import br.com.vendingmachine.domain.Produto;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProdutoAdapter extends BaseAdapter{
	
	private List<Produto> produtos;
	private Activity activity;
	
	public ProdutoAdapter(List<Produto> produtos, Activity activity) {
		this.produtos = produtos;
		this.activity = activity;
	}

	
	@Override
	public int getCount() {
		return produtos.size();
	}

	@Override
	public Object getItem(int position) {
		return produtos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return produtos.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        Produto produto = produtos.get(position);
        
		LayoutInflater inflater = activity.getLayoutInflater();
		View linha = inflater.inflate(R.layout.item_produto, null);
		
		TextView itemDescricao = (TextView) linha.findViewById(R.id.textItemDescricao);
		TextView itemValorUnitario = (TextView) linha.findViewById(R.id.textItemPreco);
		TextView itemValorVendido = (TextView) linha.findViewById(R.id.textItemPrecoVenda);
		TextView itemValoTotal = (TextView) linha.findViewById(R.id.textItemTotalProduto);
		TextView itemQuantidade = (TextView) linha.findViewById(R.id.textItemQuantidadeProduto);
		

		itemDescricao.setText(produto.getDescricao());
		itemValorUnitario.setText(produto.getValorUnitario().toString());
		itemValorVendido.setText(produto.getValorUnitario().toString());
		itemValoTotal.setText(produto.getValorTotal().toString());
		itemQuantidade.setText(produto.getQuantidade().toString());
		
		
		return linha;
	}

}
