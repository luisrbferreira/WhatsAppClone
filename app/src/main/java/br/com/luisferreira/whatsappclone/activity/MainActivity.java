package br.com.luisferreira.whatsappclone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.luisferreira.whatsappclone.R;

public class MainActivity extends AppCompatActivity {

    //Get instance para poder ter acesso ao banco de dados / Get reference para voltar a raiz
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //databaseReference.child("pontos").setValue(100);
    }
}