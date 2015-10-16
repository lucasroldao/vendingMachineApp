package br.com.vendingmachine.service;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import br.com.vendingmachine.domain.Produto;

public class ProdutoService {
	public static List<Produto> listaProduto = retornarlistaProduto();

    public static Produto buscar(String codigo) {
        for (Produto c : listaProduto) {
            if (c.getCodigo().equals(codigo)) {
                Log.d("buscarproduto", "Produto: " + c);
                return c;
            }
        }
        return null;
    }

    public static void salvar(Produto Produto) {
    	listaProduto.add(Produto);
    }

    private static List<Produto> retornarlistaProduto() {
    	if(listaProduto == null) {
    		return new ArrayList<Produto>();
    	}
    	
    	return listaProduto;
    }

	public static void remover(Produto produto) {
		listaProduto.remove(produto);
	}

	public static Double calculaValorTotal(List<Produto> produtos) {
        Double total = 0.0;
		for (Produto c : produtos) {
			total = total + c.getValorTotal();
            }
		return total;
		}
}