package cursoandroid.cursoandroid.tenda_prietopardavilajulian.basesdedatos;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;

public class manexoBBDD extends SQLiteOpenHelper {
    public static final int BBDD_VERSION=1;
    public static final String BBDD_NOME="tendajulianprieto.db";
    private static manexoBBDD sIntsance;
    public SQLiteDatabase sqlBD;
    private final String TABOAS_PEDIDOS="PEDIDOS";
    private final String TABOAS_USUARIOS="USUARIOS";
    private final String CONSULTA_USUARIO="Select * from "+TABOAS_USUARIOS+" WHERE alcume=? AND contrasinal=?";
    private final String CONSULTA_USUARIO_ID="SELECT * FROM "+TABOAS_USUARIOS+" WHERE id=?";
    private final String CONSULTA_PEDIDOS="Select * FROM "+TABOAS_PEDIDOS;
    private final String CONSULTA_ALTA_USUARIO="Select id FROM "+TABOAS_USUARIOS+" WHERE alcume=?";
    private final String CONSULTA_PEDIDOS_ESTADO="SELECT * FROM "+TABOAS_PEDIDOS +" WHERE estado=?";
    private final String CONSULTA_PEDIDOS_ESTADO_USUARIO="SELECT * FROM "+TABOAS_PEDIDOS+ " WHERE estado=? AND idUsuario=?";

    public manexoBBDD(Context applicationContext) {
        super(applicationContext,BBDD_NOME,null,BBDD_VERSION);
    }

    public static synchronized manexoBBDD getInstance(Context context){
        if (sIntsance==null){
            sIntsance=new manexoBBDD(context.getApplicationContext());
        }
        return sIntsance;
    }
    public void abrirBD(){
        if(sqlBD==null || !sqlBD.isOpen()){
            sqlBD=sIntsance.getWritableDatabase();
        }
    }
    public void pecharBD(){
        if(sqlBD!=null || sqlBD.isOpen()){
            sqlBD.close();
        }
    }

public long cargarUsuario(Usuarios usuario_engadir){
    ContentValues valores=new ContentValues();
    valores.put("nome",usuario_engadir.getNome());
    valores.put("primapelido",usuario_engadir.getPrimer_apelido());
    valores.put("secapelido",usuario_engadir.getSegundo_apelido());
    valores.put("email",usuario_engadir.getEmail());
    valores.put("alcume",usuario_engadir.getAlcume());
    valores.put("contrasinal",usuario_engadir.getContrasinal());
    valores.put("admin",usuario_engadir.getAdmin());
    long id=sqlBD.insert("Usuarios",null,valores);
    return id;
}
public boolean comprobarNomeUsuario(Usuarios usuarioComprobar){
    boolean existe=false;
    Usuarios us_comp = usuarioComprobar;
    String[] params= new String[]{String.valueOf(us_comp.getAlcume().toString())};
    Cursor datosConsulta=sqlBD.rawQuery(CONSULTA_ALTA_USUARIO,params);
    if(datosConsulta.moveToFirst()==false){
        existe=false;
    }else{existe=true; }
    datosConsulta.close();
    return existe;
}
public long cargarPedido(Pedidos pedido_engador){
        ContentValues valores=new ContentValues();
        valores.put("categoria",pedido_engador.getCategoria());
        valores.put("producto",pedido_engador.getProduccion());
        valores.put("cantidade",pedido_engador.getCantidade());
        valores.put("direccion",pedido_engador.getDireccion());
        valores.put("cidade",pedido_engador.getCidade());
        valores.put("cp",pedido_engador.getCp());
        valores.put("estado",pedido_engador.getEstado());
        valores.put("idUsuario",pedido_engador.getIdUsuario());
        long id=sqlBD.insert("Pedidos",null,valores);
        return id;
}
public long cambiarEstadoPedido(Pedidos pedido_cambiar){
        ContentValues valor=new ContentValues();
        valor.put("estado",pedido_cambiar.getEstado());
        String where="id=?";
        String[] params=new String[]{String.valueOf(pedido_cambiar.getId())};
        long rexistrosModificados=sqlBD.update(TABOAS_PEDIDOS,valor,where,params);
        return rexistrosModificados;
    }
    public long cambiarEstadoPedido(long id,String estado){
        ContentValues valor=new ContentValues();
        valor.put("estado",estado);
        String where="id=?";
        String[] params=new String[]{String.valueOf(id)};
        long rexistrosModificados=sqlBD.update(TABOAS_PEDIDOS,valor,where,params);
        return rexistrosModificados;
    }
public Usuarios obtenerUsuario(String nome_usuario, String pwd){
        Usuarios devolver_usuario=null;
        String[] params= new String[]{String.valueOf(nome_usuario),String.valueOf(pwd)};
        Cursor datosConsulta=sqlBD.rawQuery(CONSULTA_USUARIO,params);
        if(datosConsulta.moveToFirst()){
            devolver_usuario=new Usuarios(datosConsulta.getLong(0),
                    datosConsulta.getString(1),datosConsulta.getString(2),datosConsulta.getString(3),
                    datosConsulta.getString(4),datosConsulta.getString(5),datosConsulta.getString(6),
                    datosConsulta.getInt(7));
        }
        datosConsulta.close();
        return devolver_usuario;
}
    public Usuarios obtenerUsuario(long idusuario){
        Usuarios devolver_usuario=null;
        String[] params= new String[]{String.valueOf(idusuario)};
        Cursor datosConsulta=sqlBD.rawQuery(CONSULTA_USUARIO_ID,params);
        if(datosConsulta.moveToFirst()){
            devolver_usuario=new Usuarios(datosConsulta.getLong(0),
                    datosConsulta.getString(1),datosConsulta.getString(2),datosConsulta.getString(3),
                    datosConsulta.getString(4),datosConsulta.getString(5),datosConsulta.getString(6),
                    datosConsulta.getInt(7));
        }
        datosConsulta.close();
        return devolver_usuario;
    }
public ArrayList<Pedidos> obtenerPedidos(){
        ArrayList<Pedidos> devolver_Pedidos=new ArrayList<Pedidos>();

        Cursor datosColsulta=sqlBD.rawQuery(CONSULTA_PEDIDOS,null);
        if (datosColsulta.moveToFirst()){
            Pedidos pedido;
            while (!datosColsulta.isAfterLast()){
                pedido=new Pedidos(datosColsulta.getLong(0),datosColsulta.getString(1),datosColsulta.getString(2),
                        datosColsulta.getShort(3),datosColsulta.getString(4),
                        datosColsulta.getString(5),datosColsulta.getInt(6),datosColsulta.getString(7),datosColsulta.getLong(8));
                        devolver_Pedidos.add(pedido);
                        datosColsulta.moveToNext();
            }
        }
        datosColsulta.close();
        return devolver_Pedidos;
}
    public ArrayList<Pedidos> obtenerPedidos(String estado){
        ArrayList<Pedidos> devolver_Pedidos=new ArrayList<Pedidos>();
        String[] params= new String[]{String.valueOf(estado)};
        Cursor datosColsulta=sqlBD.rawQuery(CONSULTA_PEDIDOS_ESTADO,params);
        if (datosColsulta.moveToFirst()){
            Pedidos pedido;

            while (!datosColsulta.isAfterLast()){

                pedido=new Pedidos(datosColsulta.getLong(0),datosColsulta.getString(1),datosColsulta.getString(2),
                        datosColsulta.getShort(3),datosColsulta.getString(4),
                        datosColsulta.getString(5),datosColsulta.getInt(6),datosColsulta.getString(7),datosColsulta.getLong(8));
                devolver_Pedidos.add(pedido);
                datosColsulta.moveToNext();
            }
        }
        datosColsulta.close();
        return devolver_Pedidos;
    }
    public ArrayList<Pedidos> obtenerPedidos(String estado, long idUsuario){
        ArrayList<Pedidos> devolver_Pedidos=new ArrayList<Pedidos>();
        String[] params= new String[]{String.valueOf(estado), String.valueOf(idUsuario)};
        Cursor datosColsulta=sqlBD.rawQuery(CONSULTA_PEDIDOS_ESTADO_USUARIO,params);
        if (datosColsulta.moveToFirst()){
            Pedidos pedido;
            while (!datosColsulta.isAfterLast()){
                pedido=new Pedidos(datosColsulta.getLong(0),datosColsulta.getString(1),datosColsulta.getString(2),
                        datosColsulta.getShort(3),datosColsulta.getString(4),
                        datosColsulta.getString(5),datosColsulta.getInt(6),datosColsulta.getString(7),datosColsulta.getLong(8));
                devolver_Pedidos.add(pedido);
                datosColsulta.moveToNext();
            }
        }
        datosColsulta.close();
        return devolver_Pedidos;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
