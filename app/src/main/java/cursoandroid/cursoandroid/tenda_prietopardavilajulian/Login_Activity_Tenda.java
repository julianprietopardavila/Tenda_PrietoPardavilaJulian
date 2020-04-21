package cursoandroid.cursoandroid.tenda_prietopardavilajulian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cursoandroid.cursoandroid.tenda_prietopardavilajulian.basesdedatos.Usuarios;
import cursoandroid.cursoandroid.tenda_prietopardavilajulian.basesdedatos.manexoBBDD;

public class Login_Activity_Tenda extends AppCompatActivity {


    private manexoBBDD bd;


    private void copiarBBDD(){
        String bddestino="/data/data/"+ getPackageName()+"/databases/"+ bd.BBDD_NOME;
        File file= new File(bddestino);
        //Comprobamos que no exista el fichero
        if (!file.exists()){
            String rutabd="/data/data/"+getPackageName()+"/databases";
            File filerutadb=new File(rutabd);
            //Comprobamos que no exista la ruta y la creamos
            if(!filerutadb.exists()){
                filerutadb.mkdir();
            }
            InputStream inputstream;
            try{
                inputstream=getAssets().open(bd.BBDD_NOME);
                OutputStream outputstream=new FileOutputStream(bddestino);
                int tamread;
                byte[] buffer=new byte[2048];

                while ((tamread=inputstream.read(buffer))>0){
                    outputstream.write(buffer,0,tamread);
                }
                inputstream.close();
                outputstream.flush();
                outputstream.close();

            }catch (IOException e){
                e.printStackTrace();
            }
        }else{return;}
    }


    public void  xestionEventos(){
        final EditText txtUsuario=(EditText) findViewById(R.id.editTextUsuario);
        final EditText txtPwd=(EditText) findViewById(R.id.editText_Contrasinal);

        Button btnAdmin=(Button) findViewById(R.id.button_Admin);

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
                    if (bd == null) {
                        bd = manexoBBDD.getInstance(getApplicationContext());
                        bd.abrirBD();
                    }
                    Usuarios usuarioacceso = bd.obtenerUsuario(txtUsuario.getText().toString(), txtPwd.getText().toString());
                    if (bd != null) {
                        bd.pecharBD();
                        bd = null;
                    }
                    if (usuarioacceso != null) {
                        Intent intent = new Intent(v.getContext(), Activity_Cliente.class);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putLong("id", usuarioacceso.getId());
                        editor.commit();
                        //intent.putExtra("id",usuarioacceso.getId());
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error al introducir usuarioo contraseña", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    if (bd != null) {
                        bd.pecharBD();
                        bd = null;
                    }
                }
            }
        });

        Button btnRexistro=(Button) findViewById(R.id.button_Rexistro_Login);
        btnRexistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Activity_Rexistro.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login___tenda);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.clear();
        editor.commit();
        copiarBBDD();
        xestionEventos();
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
    }

}
