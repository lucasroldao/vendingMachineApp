package br.com.vendingmachine.validator;

import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class ValidaCampo {
	/**
	 * Método responsávek por validar se os campos estão preenchidos
	 * */
	public static boolean validateNotNull(View pView, String pMessage) {
		if (pView instanceof EditText) {
			EditText edText = (EditText) pView;
			Editable text = edText.getText();
			if (text != null) {
				String strText = text.toString().trim();
				if (!TextUtils.isEmpty(strText)) {
					return true;
				}
			}
			// em qualquer outra condição é gerado um erro
			edText.setError(pMessage);
			edText.setFocusable(true);
			edText.requestFocus();
			edText.setText(null);
			return false;
		}
		return false;
	}
	
	public static boolean validateNotZero(View pView, String pMessage) {
		if (pView instanceof EditText) {
			EditText edText = (EditText) pView;
			Integer valor = Integer.valueOf(edText.getText().toString());
			
			if(valor != 0){
				return true;
			}
			// em qualquer outra condição é gerado um erro
			edText.setError(pMessage);
			edText.setFocusable(true);
			edText.requestFocus();
			edText.setText(null);
			return false;

			}

		return false;
	}
	
}