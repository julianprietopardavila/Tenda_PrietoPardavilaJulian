package cursoandroid.cursoandroid.tenda_prietopardavilajulian;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import cursoandroid.cursoandroid.tenda_prietopardavilajulian.basesdedatos.Pedidos;



public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.ViewHolderDatos>{

    ArrayList<Pedidos> listaPedidos;

    public PedidosAdapter(ArrayList<Pedidos> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    @NonNull
    @Override
    public PedidosAdapter.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_pedido,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosAdapter.ViewHolderDatos holder, int position) {
        holder.asignarDatos(listaPedidos.get(position).getCantidade(),listaPedidos.get(position).getProduccion(),
                listaPedidos.get(position).getCategoria(), listaPedidos.get(position).getDireccion(), listaPedidos.get(position).getCp(),
                listaPedidos.get(position).getCidade(),listaPedidos.get(position).getId(),listaPedidos.get(position).getIdUsuario());
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }
    public class ViewHolderDatos extends RecyclerView.ViewHolder{
        String pedido_aux;
        TextView pedido;
        TextView envio;

        public ViewHolderDatos(@NonNull View itemView) {

            super(itemView);

            pedido=itemView.findViewById(R.id.txtListarPedido);
            envio=itemView.findViewById(R.id.txtListarEnvio);
        }
        public void asignarDatos(int cantidades,String productos, String categorias, String direcciones, int cps,String ciudades, long idPedido, long idUsuario){
            pedido_aux="PEDIDO Num: "+idPedido+",  "+ categorias+" - "+productos+", Cantidad: "+cantidades+",  Ordenante: "+ idUsuario;
            pedido.setText(pedido_aux.toString());
            envio.setText("ENVIAR A: Direccion "+ direcciones+" - "+ciudades+" - "+cps);
        }
    }
}
