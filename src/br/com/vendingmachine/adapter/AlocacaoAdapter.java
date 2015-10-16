package br.com.vendingmachine.adapter;

import java.util.List;
import br.com.vendingmachine.activity.R;
import br.com.vendingmachine.domain.Alocacao;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class AlocacaoAdapter extends BaseAdapter{
	
	private List<Alocacao> alocacoes;
	private Activity activity;
	
	public AlocacaoAdapter(List<Alocacao> alocacoes, Activity activity) {
		this.alocacoes = alocacoes;
		this.activity = activity;
	}

	
	@Override
	public int getCount() {
		return alocacoes.size();
	}

	@Override
	public Alocacao getItem(int position) {
		return alocacoes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return alocacoes.get(position).getid();
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        Alocacao Alocacao = alocacoes.get(position);
        
		LayoutInflater inflater = activity.getLayoutInflater();
		View linha = inflater.inflate(R.layout.layout_ls_alocacao, null);
		
		TextView dataSolicitacao = (TextView) linha.findViewById(R.id.txtDataSolicitacao);
		TextView nomeCliente = (TextView) linha.findViewById(R.id.txtNomeCliente);
		TextView endereco = (TextView) linha.findViewById(R.id.txtEndereco);
		TextView numero = (TextView) linha.findViewById(R.id.txtNumero);
		TextView telefone = (TextView) linha.findViewById(R.id.txtTelefone);
		TextView codMaquina = (TextView) linha.findViewById(R.id.txtCodMaquina);
		TextView codModeloMaquina = (TextView) linha.findViewById(R.id.txtModeloMaquina);
		
		dataSolicitacao.setText(Alocacao.getDataSolicitacao());
		nomeCliente.setText(Alocacao.getCliente().getNomeFantasia());
		endereco.setText(Alocacao.getCliente().getLogradouro());
		telefone.setText(Alocacao.getCliente().getTelefone().toString());
		numero.setText(Alocacao.getCliente().getNumero().toString());
		codMaquina.setText(Alocacao.getMaquina().getCodigo());
		codModeloMaquina.setText(Alocacao.getMaquina().getModelo());
		
		return linha;
	}

}
