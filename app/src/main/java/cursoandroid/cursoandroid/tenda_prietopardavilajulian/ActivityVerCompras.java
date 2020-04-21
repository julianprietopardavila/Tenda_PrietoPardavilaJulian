package cursoandroid.cursoandroid.tenda_prietopardavilajulian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;

import cursoandroid.cursoandroid.tenda_prietopardavilajulian.basesdedatos.Pedidos;
import cursoandroid.cursoandroid.tenda_prietopardavilajulian.basesdedatos.manexoBBDD;

public class ActivityVerCompras extends AppCompatActivity {

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
            String filtro;
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity__ver_pedidos);

            Bundle bundle = getIntent().getExtras();
            filtro = bundle.getString("filtro");
            if (filtro.equals("ACEPTADO")) {
                if (!bundle.getBoolean("admin")) {
                    this.setTitle(R.string.verCompras_titulo_Cliente);
                } else {
                    this.setTitle(R.string.verCompras_titulo_Admin);
                }

            } else if (filtro.equals("REXEITAR")) {
                this.setTitle(R.string.verCompras_titulo_Rechazadas);
            }

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
            if (bundle.getBoolean("admin")) {
                listadoPersonas = sqlbd.obtenerPedidos(filtro);
            } else {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
                listadoPersonas = sqlbd.obtenerPedidos(filtro, pref.getLong("id", 0));
            }
            if (sqlbd != null) {
                sqlbd.pecharBD();
                sqlbd = null;
            }
            PedidosAdapter adapter = new PedidosAdapter(listadoPersonas);
            recycler.setAdapter(adapter);
        } catch (Exception e) {
            if (sqlbd != null) {
                sqlbd.pecharBD();
                sqlbd = null;
            }
        }
    }
}
