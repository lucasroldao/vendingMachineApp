package br.com.vendingmachine.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.vendingmachine.activity.R;
import br.com.vendingmachine.domain.Alocacao;
import br.com.vendingmachine.domain.Maquina;

public class MaquinaAdapter extends BaseAdapter {
	
	private List<Maquina> maquinas;
	private Activity activity;
	
	public MaquinaAdapter(List<Maquina> maquinas, Activity activity) {
		this.maquinas = maquinas;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return maquinas.size();
	}

	@Override
	public Maquina getItem(int position) {
		return maquinas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return maquinas.get(position).getId();
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        Maquina maquina = maquinas.get(position);
        
		LayoutInflater inflater = activity.getLayoutInflater();
		View linha = inflater.inflate(R.layout.layout_ls_maquina, null);
		
		TextView codMaquina = (TextView) linha.findViewById(R.id.textLsCodMaquina);
		TextView codModeloMaquina = (TextView) linha.findViewById(R.id.textLsModeloMaquina);
		
		codMaquina.setText(maquina.getCodigo());
		codModeloMaquina.setText(maquina.getModelo());
		
		return linha;
	}


}
