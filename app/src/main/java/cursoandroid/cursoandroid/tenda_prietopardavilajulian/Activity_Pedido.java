package cursoandroid.cursoandroid.tenda_prietopardavilajulian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Activity_Pedido extends AppCompatActivity {

    public void xestionEventos(){
        final Spinner spCategorias=(Spinner) findViewById(R.id.spinnerCategorias);
        final Spinner spProducto=(Spinner) findViewById(R.id.spinnerProductos);
        final EditText txtCantidade=(EditText) findViewById(R.id.editTextCantidade);

        spCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ArrayAdapter adapter;
                switch (spCategorias.getItemAtPosition(position).toString()){
                    case "Informatica":

                          adapter = ArrayAdapter.createFromResource(Activity_Pedido.this,R.array.producto_informatica,R.layout.support_simple_spinner_dropdown_item);
                          adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                          spProducto.setAdapter(adapter);

                        break;
                    case "Electronica":
                        adapter = ArrayAdapter.createFromResource(Activity_Pedido.this,R.array.producto_electronica,R.layout.support_simple_spinner_dropdown_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spProducto.setAdapter(adapter);
                        break;
                    case "Móviles":
                        adapter = ArrayAdapter.createFromResource(Activity_Pedido.this,R.array.producto_Mobiles,R.layout.support_simple_spinner_dropdown_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spProducto.setAdapter(adapter);
                        break;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button btnHacerPedido = (Button) findViewById(R.id.btSeguinte_Pedidos);
        btnHacerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtCantidade.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Non pode deixar o campo cantidade valeiro",Toast.LENGTH_LONG).show();

                }else {
                    Intent intent = new Intent(v.getContext(), Activity_Envio.class);
                    intent.putExtra("categoria", spCategorias.getSelectedItem().toString());
                    intent.putExtra("producto", spProducto.getSelectedItem().toString());
                    intent.putExtra("cantidad", txtCantidade.getText().toString());
                    startActivity(intent);
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
        setContentView(R.layout.activity__pedido);
        xestionEventos();
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
