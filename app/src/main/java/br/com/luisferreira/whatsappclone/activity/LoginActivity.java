package br.com.luisferreira.whatsappclone.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.Random;

import br.com.luisferreira.whatsappclone.R;
import br.com.luisferreira.whatsappclone.helper.Permissao;
import br.com.luisferreira.whatsappclone.helper.Preferencias;

public class LoginActivity extends AppCompatActivity {

    private EditText nome;
    private EditText telefone;
    private EditText codPais;
    private EditText codDDD;
    private Button cadastrar;
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permissao.validaPermissoes(1, this, permissoesNecessarias);

        nome = (EditText) findViewById(R.id.edit_nome);
        telefone = (EditText) findViewById(R.id.edit_telefone);
        codPais = (EditText) findViewById(R.id.edit_cod_pais);
        codDDD = (EditText) findViewById(R.id.edit_ddd);
        cadastrar = (Button) findViewById(R.id.btn_cadastrar);

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

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeUsuario = nome.getText().toString();
                String telefoneCompleto =
                        codPais.getText().toString() +
                                codDDD.getText().toString() +
                                telefone.getText().toString();

                String telefoneSemFormatacao = telefoneCompleto.replace("+", "");
                telefoneSemFormatacao = telefoneSemFormatacao.replace("-", "");

                //Gerar Token
                Random random = new Random();
                int numeroRandomico = random.nextInt(8999) + 1000;

                String token = String.valueOf(numeroRandomico);
                String mensagemEnvio = "WhatsApp Código de Confirmação: " + token;

                //Salvar os dados para validação
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatacao, token);

                //Envio do SMS
                telefoneSemFormatacao = "5554";
                boolean smsEnviado = enviaSMS("+" + telefoneSemFormatacao, mensagemEnvio);

                /*HashMap<String, String> usuario = preferencias.getDadosUsuario();
                Log.i("T", "Token: " + usuario.get("token") + " | Nome: " + usuario.get("nome") + " | Telefone: " + usuario.get("telefone"));*/
            }
        });
    }

    //Envio do SMS
    private boolean enviaSMS(String telefone, String mensagem) {

        try {
            //Recupera a instacia da classe SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);

            return true;
        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}