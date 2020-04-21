package cursoandroid.cursoandroid.tenda_prietopardavilajulian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.Toast;

import cursoandroid.cursoandroid.tenda_prietopardavilajulian.basesdedatos.Usuarios;
import cursoandroid.cursoandroid.tenda_prietopardavilajulian.basesdedatos.manexoBBDD;

import static java.util.Objects.isNull;

public class Activity_Rexistro extends AppCompatActivity {
private manexoBBDD sqlbd;
private long idUsuario;


    public void xestionEventos(){

        Button btnGardar = (Button) findViewById(R.id.btnGardar_Rexistro);
        final EditText nome_Rexitro=(EditText) findViewById(R.id.editTxtNome_Rexistro);
        final EditText primApelido=(EditText) findViewById(R.id.editTxtApelido1_Rexistro);
        final EditText secApelido=(EditText) findViewById(R.id.editTxtApelido2_Rexistro);
        final EditText email=(EditText) findViewById(R.id.editTxtEmail_Rexistro);
        final EditText alcume=(EditText) findViewById(R.id.editTxtAlcume_Rexistro);
        final EditText contrasinal=(EditText) findViewById(R.id.edittxtContrasinal_Rexistro);
        final CheckBox administrado=(CheckBox) findViewById(R.id.chkAdmin_Rexistro);

        btnGardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
                    int admin;
                    if (administrado.isChecked()) {
                        admin = 1;
                    } else {
                        admin = 0;
                    }
                    if (isNull(alcume.getText().toString()) || alcume.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Introduce un alcume!!!", Toast.LENGTH_LONG).show();
                    } else {
                        Usuarios novoUsuario = new Usuarios(nome_Rexitro.getText().toString(), primApelido.getText().toString(), secApelido.getText().toString(),
                                email.getText().toString(), alcume.getText().toString().trim(), contrasinal.getText().toString(), admin);
                        if (sqlbd == null) {
                            sqlbd = manexoBBDD.getInstance(getApplicationContext());
                            sqlbd.abrirBD();
                        }
                        if (sqlbd.comprobarNomeUsuario(novoUsuario)) {
                            Toast.makeText(getApplicationContext(), "O Nome de Usuario xa exite", Toast.LENGTH_LONG).show();
                        } else {

                            idUsuario = sqlbd.cargarUsuario(novoUsuario);
                            if (sqlbd != null) {
                                sqlbd.pecharBD();
                                sqlbd = null;
                            }

                            SharedPreferences.Editor editor = pref.edit();
                            editor.putLong("id", idUsuario);
                            editor.commit();
                            Intent intent = new Intent(v.getContext(), Activity_Cliente.class);
                            startActivity(intent);
                        }
                    }
                } catch (Exception e) {
                    if (sqlbd != null) {
                        sqlbd.pecharBD();
                        sqlbd = null;
                    }
                }
            }
        });
    }

 /*   @Override
    public void onStop(){
        super.onStop();
        if(sqlbd!=null){
            sqlbd.pecharBD();
            sqlbd=null;
        }
    }*/
 @Override
 public boolean onSupportNavigateUp() {
     onBackPressed();
     return true;
 }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__rexistro);
        xestionEventos();
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
