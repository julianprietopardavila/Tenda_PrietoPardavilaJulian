package cursoandroid.cursoandroid.tenda_prietopardavilajulian;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import cursoandroid.cursoandroid.tenda_prietopardavilajulian.basesdedatos.Pedidos;
import cursoandroid.cursoandroid.tenda_prietopardavilajulian.basesdedatos.manexoBBDD;

public class Activity_Envio extends AppCompatActivity {
private manexoBBDD sqlbd;
private Pedidos pedidocliente=null;
    private void xestionEventos(){
        Button btnFinalizar = (Button) findViewById(R.id.btFinalizar_Direccion);
        final EditText txtDireccion=(EditText) findViewById(R.id.editTextDireccion);
        final EditText txtCidade=(EditText) findViewById(R.id.editTextCidade);
        final EditText txtCP=(EditText) findViewById(R.id.editTextCP);

        btnFinalizar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (txtCidade.getText().toString().trim().equals("") || txtDireccion.getText().toString().trim().equals("") || txtCP.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(),"Non pode deixar nin a Direccion nin ha cidade nin o CP valeiros",Toast.LENGTH_LONG).show();
                } else {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
                    Bundle bundle = getIntent().getExtras();
                    pedidocliente = new Pedidos(bundle.getString("categoria"), bundle.getString("producto"),
                            Integer.parseInt(bundle.getString("cantidad")), txtDireccion.getText().toString(), txtCidade.getText().toString(),
                            Integer.parseInt(txtCP.getText().toString()), "TRAMITE", pref.getLong("id", 0));
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Realizanr Pedido").setIcon(R.drawable.ic_launcher_foreground);
                    builder.setMessage(pedidocliente.toString());
                    builder.setPositiveButton("Seguir co pedido", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                if (sqlbd == null) {
                                    sqlbd = manexoBBDD.getInstance(getApplicationContext());
                                    sqlbd.abrirBD();
                                }
                                sqlbd.cargarPedido(pedidocliente);
                                if (sqlbd != null) {
                                    sqlbd.pecharBD();
                                    sqlbd = null;
                                }
                                Intent intent = new Intent(getApplicationContext(), Activity_Cliente.class);
                                startActivity(intent);
                            } catch (Exception e) {
                                if (sqlbd != null) {
                                    sqlbd.pecharBD();
                                    sqlbd = null;
                                }
                            }
                        }
                    });

                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), Activity_Cliente.class);
                            startActivity(intent);
                        }
                    });
                    builder.create();
                    builder.show();

                }
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__envio);
        xestionEventos();
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
