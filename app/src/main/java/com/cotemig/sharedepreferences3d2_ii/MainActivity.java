package com.cotemig.sharedepreferences3d2_ii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cotemig.sharedepreferences3d2_ii.Utils.AppConstants;
import com.cotemig.sharedepreferences3d2_ii.Utils.SecurityPreferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Invocar a classe SharedPreferences da Activity
        this.mSecurityPreferences = new SecurityPreferences(this);

        // mapeando os campos da tela para tratamento
        this.mViewHolder.edtTextCampo = findViewById(R.id.edtTextCampo);
        this.mViewHolder.txtSharedPref = findViewById(R.id.txtSharedPref);
        this.mViewHolder.btnGravar = findViewById(R.id.btnGravar);
        this.mViewHolder.btnMostrar = findViewById(R.id.btnMostrar);
        this.mViewHolder.btnMostrar = findViewById(R.id.btnMostrar);
        this.mViewHolder.edtTextNome = findViewById(R.id.edtTextNome);

        // Definir os Objetos com clique
        this.mViewHolder.btnMostrar.setOnClickListener(this);
        this.mViewHolder.btnGravar.setOnClickListener(this);

        this.mViewHolder.edtTextCampo.setText(this.mSecurityPreferences.getStoreString(AppConstants.EmailUsuario));

        // criando ou abrindo um banco de dados
         SQLiteDatabase myDB = openOrCreateDatabase("3D2.db", MODE_PRIVATE, null);
        // Criando a tabela do banco
        myDB.execSQL("CREATE TABLE IF NOT EXISTS usuarios (email varchar(100), nome varchar(100))");

    }

    @Override
    public void onClick(View tela) {
        int id = tela.getId();
        if (id == R.id.btnMostrar){
           this.mViewHolder.txtSharedPref.setText(this.mSecurityPreferences.getStoreString(AppConstants.EmailUsuario));
           // Abrir o banco de dados
           SQLiteDatabase myDB = openOrCreateDatabase("3D2.db", MODE_PRIVATE, null);
           Cursor myUsuarios = myDB.rawQuery("Select nome, email from usuarios order by nome",null);
           String dados = "";
           while (myUsuarios.moveToNext()){
                String nome = myUsuarios.getString(0);
                String email = myUsuarios.getString(1);
                dados = dados + " "+ nome + " "+ email;
           }
           this.mViewHolder.txtSharedPref.setText(dados);
           myUsuarios.close();

        }else if (id == R.id.btnGravar){
            // Gravando no sharedPreferences
            this.mSecurityPreferences.storeString(AppConstants.EmailUsuario,this.mViewHolder.edtTextCampo.getText().toString());
            // Gravar no Banco de dados
            ContentValues registro = new ContentValues();
            registro.put("email",this.mViewHolder.edtTextCampo.getText().toString());
            registro.put("nome",this.mViewHolder.edtTextNome.getText().toString());
            SQLiteDatabase myDB = openOrCreateDatabase("3D2.db", MODE_PRIVATE, null);
            myDB.insert("usuarios",null,registro);

        }else if (id == R.id.btnRemover){
            this.mSecurityPreferences.RemoveString(AppConstants.EmailUsuario);
            SQLiteDatabase myDB = openOrCreateDatabase("3D2.db", MODE_PRIVATE, null);
            myDB.delete("usuarios","email = " +this.mViewHolder.edtTextCampo.getText().toString(),null);
        }
    }


    public static class ViewHolder{
        EditText edtTextCampo;
        EditText edtTextNome;
        Button btnGravar;
        Button btnMostrar;
        TextView txtSharedPref;
        Button btnRemover;
    }
}
