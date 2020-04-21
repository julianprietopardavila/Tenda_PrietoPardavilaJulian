package cursoandroid.cursoandroid.tenda_prietopardavilajulian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;

import cursoandroid.cursoandroid.tenda_prietopardavilajulian.basesdedatos.Usuarios;
import cursoandroid.cursoandroid.tenda_prietopardavilajulian.basesdedatos.manexoBBDD;

public class Activity_Cliente extends AppCompatActivity {
 /**   Button btnPedidos = (Button) findViewById(R.id.button_Pedidos);
    Button btverPedidosTramite= (Button) findViewById(R.id.button_VerTramite);
    Button btverCompras=(Button) findViewById(R.id.button_VerCompras);
    Button btSalir=(Button) findViewById(R.id.button_Sair);
    TextView txtNome=(TextView) findViewById(R.id.edittxtNome_Cliente);
    TextView txtApelido1=(TextView) findViewById(R.id.edittxtApelido1_Cliente);
    TextView txtApelido2=(TextView) findViewById(R.id.edittxtApelido2_Cliente);*/

    private manexoBBDD sqlbd;
    private long idUsuario;


private void facerPedido(){
    Intent intent = new Intent(getApplicationContext(), Activity_Pedido.class);
    startActivityForResult(intent, 0);
}

private boolean concerAdministrador(){
    boolean admin=false;
    SharedPreferences pref = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
    if(sqlbd==null) {
        sqlbd = manexoBBDD.getInstance(getApplicationContext());
        sqlbd.abrirBD();
    }

    idUsuario= pref.getLong("id",0);
    Usuarios usuario=sqlbd.obtenerUsuario(idUsuario);
    if (usuario.getAdmin()==0){
        admin=false;
    }else if(usuario.getAdmin()==1){
        admin=true;
    }
    if(sqlbd!=null){
        sqlbd.pecharBD();
        sqlbd=null;
    }
    return admin;
}

private void verPedidos(boolean administrador){

    Intent intent = new Intent(getApplicationContext(), Activity_VerPedidosEnTramite.class);
    intent.putExtra("filtro", "TRAMITE");
    intent.putExtra("admin", administrador);
    startActivity(intent);
}

private void verCompras(boolean admistrador){
    Intent intent = new Intent(getApplicationContext(), ActivityVerCompras.class);

    intent.putExtra("filtro", "ACEPTADO");
    intent.putExtra("admin", admistrador);
    startActivity(intent);
}

private void verRexeitadas(boolean admistrador){
    Intent intent=new Intent(getApplicationContext(),ActivityVerCompras.class);
    intent.putExtra("filtro","REXEITAR");
    intent.putExtra("admin",admistrador);
    startActivity(intent);
}

private void salirPantalla(){
    Intent intent = new Intent(getApplicationContext(), Login_Activity_Tenda.class);
    startActivity(intent);
}
    private void xestionEventos(){
        final Button btnPedidos = (Button) findViewById(R.id.button_Pedidos);
        Button btverPedidosTramite= (Button) findViewById(R.id.button_VerTramite);
        Button btverCompras=(Button) findViewById(R.id.button_VerCompras);
        Button btSalir=(Button) findViewById(R.id.button_Sair);
        Button btRexeitados=(Button) findViewById(R.id.button_Rexeitados);

        //Hacer Pedidos (solo se muestra a cliente)
        btnPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facerPedido();
            }
        });
        //Ver Pedidos Tramitados (Cliente solo debe ser lso suyos, admin todos)

            btverPedidosTramite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verPedidos(concerAdministrador());

                }
            });

        //Ver Compras realizadas admin todas, usuario solo las suyas

            btverCompras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verCompras(concerAdministrador());
                }
            });
        //Salir
        btSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salirPantalla();
            }
        });
        // Ver pedidos rexeitados solo lo ve admin
        btRexeitados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verRexeitadas(concerAdministrador());
            }
        });
    }
private void cargarOpciones(){
    Button btnPedidos = (Button) findViewById(R.id.button_Pedidos);
    Button btverPedidosTramite= (Button) findViewById(R.id.button_VerTramite);
    Button btverCompras=(Button) findViewById(R.id.button_VerCompras);
    Button btSalir=(Button) findViewById(R.id.button_Sair);
    Button btRexeitados=(Button) findViewById(R.id.button_Rexeitados);
    TextView txtNome=(TextView) findViewById(R.id.edittxtNome_Cliente);
    TextView txtApelido1=(TextView) findViewById(R.id.edittxtApelido1_Cliente);
    TextView txtApelido2=(TextView) findViewById(R.id.edittxtApelido2_Cliente);
    ImageView imgUser=(ImageView) findViewById(R.id.imgUsuarii_Cliente);
    SharedPreferences pref = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
  try {
      if (sqlbd == null) {
          sqlbd = manexoBBDD.getInstance(getApplicationContext());
          sqlbd.abrirBD();
      }


      idUsuario = pref.getLong("id", 0);
      Usuarios usuario = sqlbd.obtenerUsuario(idUsuario);
      txtNome.setText(usuario.getNome());
      txtApelido1.setText(usuario.getPrimer_apelido());
      txtApelido2.setText(usuario.getSegundo_apelido());
      if (usuario.getAdmin() == 0) {
          btnPedidos.setVisibility(View.VISIBLE);
          btverCompras.setVisibility(View.VISIBLE);
          btverPedidosTramite.setVisibility(View.VISIBLE);
          btSalir.setVisibility(View.VISIBLE);
          btRexeitados.setVisibility(View.INVISIBLE);
          imgUser.setImageResource(R.drawable.cliente);
      } else {
          btnPedidos.setVisibility(View.INVISIBLE);
          btRexeitados.setVisibility(View.VISIBLE);
          btverCompras.setVisibility(View.VISIBLE);
          btverCompras.setText(R.string.cliente_btnVerComprasAdmin);
          btverPedidosTramite.setVisibility(View.VISIBLE);
          btSalir.setVisibility(View.INVISIBLE);
          imgUser.setImageResource(R.drawable.admin);
      }
      if (sqlbd != null) {
          sqlbd.pecharBD();
          sqlbd = null;
      }
  } catch (Exception e) {
      if (sqlbd != null) {
          sqlbd.pecharBD();
          sqlbd = null;
      }
  }
}
/**@Override
    public void onStart(){
        super.onStart();
        if(sqlbd==null){
            sqlbd= manexoBBDD.getInstance(getApplicationContext());
            sqlbd.abrirBD();
        }
    }*/
@Override
public boolean onCreateOptionsMenu(Menu menu){
    getMenuInflater().inflate(R.menu.menu,menu);
    if(!concerAdministrador()){
        MenuItem it =menu.findItem(R.id.menu_barracliente_verrexeitados);
        it.setVisible(false);
    }else{
        MenuItem it =menu.findItem(R.id.menu_barracliente_hacerpedido);
        it.setVisible(false);
    }
    return true;
}
@Override
public boolean onOptionsItemSelected(MenuItem item){
    switch (item.getItemId()){
        case R.id.menu_barracliente_hacerpedido:
            facerPedido();
            return true;
        case R.id.menu_barracliente_vertramite:
            verPedidos(concerAdministrador());
            return true;
        case R.id.menu_barracliente_verrealizadas:
            verCompras(concerAdministrador());
            return true;
        case R.id.menu_barracliente_verrexeitados:
            verRexeitadas(concerAdministrador());
            return true;
        case R.id.menu_barracliente_salir:
            salirPantalla();
            return true;
         default:
             return super.onOptionsItemSelected(item);
    }
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__cliente);
        cargarOpciones();
        xestionEventos();
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbarCliente);
        setSupportActionBar(toolbar);

    }
}
