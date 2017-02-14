package br.com.luisferreira.whatsappclone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import br.com.luisferreira.whatsappclone.R;

public class LoginActivity extends AppCompatActivity {

    private EditText nome;
    private EditText telefone;
    private EditText codPais;
    private EditText codDDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nome = (EditText) findViewById(R.id.edit_nome);
        telefone = (EditText) findViewById(R.id.edit_telefone);
        codPais = (EditText) findViewById(R.id.edit_cod_pais);
        codDDD = (EditText) findViewById(R.id.edit_ddd);

        //Mascaras
        SimpleMaskFormatter simpleMaskCodPais = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter simpleMaskDDD = new SimpleMaskFormatter("NN");
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");

        MaskTextWatcher maskTextWatcherCodPais = new MaskTextWatcher(codPais, simpleMaskCodPais);
        MaskTextWatcher maskTextWatcherCodDDD = new MaskTextWatcher(codDDD, simpleMaskDDD);
        MaskTextWatcher maskTextWatcherTelefone = new MaskTextWatcher(telefone, simpleMaskTelefone);

        codPais.addTextChangedListener(maskTextWatcherCodPais);
        codDDD.addTextChangedListener(maskTextWatcherCodDDD);
        telefone.addTextChangedListener(maskTextWatcherTelefone);
    }
}