package cursoandroid.cursoandroid.tenda_prietopardavilajulian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;


import java.util.ArrayList;

import cursoandroid.cursoandroid.tenda_prietopardavilajulian.basesdedatos.Pedidos;
import cursoandroid.cursoandroid.tenda_prietopardavilajulian.basesdedatos.Usuarios;
import cursoandroid.cursoandroid.tenda_prietopardavilajulian.basesdedatos.manexoBBDD;

public class Activity_VerPedidosEnTramite extends AppCompatActivity {

    ArrayList<Pedidos> listadoPersonas;
    RecyclerView recycler;
    private manexoBBDD sqlbd;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       try {
           super.onCreate(savedInstanceState);
           Usuarios user;
           setContentView(R.layout.activity__ver_pedidos);
           // RecyclerView rv=(RecyclerView) findViewById(R.id.recicladorpedidos);
           Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
           setSupportActionBar(toolbar);
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           recycler = (RecyclerView) findViewById(R.id.recicladorpedidos);
           recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
           //Cargar Array
           if (sqlbd == null) {
               sqlbd = manexoBBDD.getInstance(getApplicationContext());
               sqlbd.abrirBD();
           }
           Bundle bundle = getIntent().getExtras();

           if (bundle.getBoolean("admin")) {
               listadoPersonas = sqlbd.obtenerPedidos(bundle.getString("filtro"));

               if (sqlbd != null) {
                   sqlbd.pecharBD();
                   sqlbd = null;
               }
               PedidosAdapterAdmin adapter = new PedidosAdapterAdmin(listadoPersonas);

               recycler.setAdapter(adapter);
           } else {
               SharedPreferences pref = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
               listadoPersonas = sqlbd.obtenerPedidos(bundle.getString("filtro"), pref.getLong("id", 0));
               if (sqlbd != null) {
                   sqlbd.pecharBD();
                   sqlbd = null;
               }
               PedidosAdapter adapter = new PedidosAdapter(listadoPersonas);
               recycler.setAdapter(adapter);
           }


       } catch (Exception e) {
           if (sqlbd != null) {
               sqlbd.pecharBD();
               sqlbd = null;
           }
       }
    }
}
