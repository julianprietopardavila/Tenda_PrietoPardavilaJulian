package cursoandroid.cursoandroid.tenda_prietopardavilajulian;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cursoandroid.cursoandroid.tenda_prietopardavilajulian.basesdedatos.Pedidos;
import cursoandroid.cursoandroid.tenda_prietopardavilajulian.basesdedatos.manexoBBDD;


public class PedidosAdapterAdmin extends RecyclerView.Adapter<PedidosAdapterAdmin.ViewHolderDatos>  {


    ArrayList<Pedidos> listaPedidos;


    public PedidosAdapterAdmin(ArrayList<Pedidos> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    @NonNull
    @Override
    public PedidosAdapterAdmin.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_pedido_admin,null,false);

        return new ViewHolderDatos(view);
    }

    public void removelistaPedidos(int position) {
        listaPedidos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,listaPedidos.size());
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull PedidosAdapterAdmin.ViewHolderDatos holder, final int position) {
        holder.asignarDatos(listaPedidos.get(position).getCantidade(),listaPedidos.get(position).getProduccion(),
                listaPedidos.get(position).getCategoria(), listaPedidos.get(position).getDireccion(), listaPedidos.get(position).getCp(),
                listaPedidos.get(position).getCidade(),listaPedidos.get(position).getId(),listaPedidos.get(position).getIdUsuario());
        holder.btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manexoBBDD sqllite = null;
                try {
                    if (sqllite == null) {
                        sqllite = manexoBBDD.getInstance(v.getContext());
                        sqllite.abrirBD();
                    }
                    sqllite.cambiarEstadoPedido(listaPedidos.get(position).getId(), "ACEPTADO");

                    if (sqllite != null) {
                        sqllite.pecharBD();
                        sqllite = null;
                    }
                    removelistaPedidos(position);
                } catch (Exception e) {
                    if (sqllite != null) {
                        sqllite.pecharBD();
                        sqllite = null;
                    }
                }
            }
        });
        holder.btnRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manexoBBDD sqllite = null;
                try {
                    if (sqllite == null) {
                        sqllite = manexoBBDD.getInstance(v.getContext());
                        sqllite.abrirBD();
                    }
                    sqllite.cambiarEstadoPedido(listaPedidos.get(position).getId(), "REXEITAR");
                    removelistaPedidos(position);
                } catch (Exception e) {
                    if (sqllite != null) {
                        sqllite.pecharBD();
                        sqllite = null;
                    }
                }


            }

        });

    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }


    public class ViewHolderDatos extends RecyclerView.ViewHolder{
        String pedido_aux;
        TextView pedido;
        TextView envio;
        Button btnAceptar;
        Button btnRechazar;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            pedido=itemView.findViewById(R.id.txtListarPedido);
            envio=itemView.findViewById(R.id.txtListarEnvio);

            btnAceptar=itemView.findViewById(R.id.btnAceptarPedidoAdmin);
            btnRechazar=itemView.findViewById(R.id.btnRexeitarPedidoAdmin);
        }
        public void asignarDatos(int cantidades,String productos, String categorias, String direcciones, int cps,String ciudades, long idPedido, long idUsuario){
            pedido_aux="PEDIDO Num: "+idPedido+",  "+ categorias+" - "+productos+", Cantidad: "+cantidades+",  Ordenante: "+ idUsuario;
            pedido.setText(pedido_aux.toString());
            envio.setText("ENVIAR A: Direccion "+ direcciones+" - "+ciudades+" - "+cps);
        }
    }
}
