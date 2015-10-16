package br.com.vendingmachine.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.com.vendingmachine.task.LoginTask;
import br.com.vendingmachine.validator.ValidaCampo;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);


		Button botaoLogin = (Button) findViewById(R.id.botaologin);

		botaoLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				final EditText login = (EditText) findViewById(R.id.login);
				final EditText senha = (EditText) findViewById(R.id.senha);
				
				boolean loginValido = ValidaCampo.validateNotNull(login, "Preencha o campo login");
				boolean senhaValida = ValidaCampo.validateNotNull(senha, "Preencha o campo senha");
			    
				if (loginValido == true && senhaValida == true){
					LoginTask loginTask = new LoginTask(LoginActivity.this,login,senha);
					loginTask.execute();
				}

			}
		});
	}
}
