package cursoandroid.cursoandroid.tenda_prietopardavilajulian.basesdedatos;

public class Pedidos {
    private long id;
    private String categoria;
    private String produccion;
    private int cantidade;
    private String direccion;
    private String cidade;
    private int cp;
    private String estado;
    private long idUsuario;


    public Pedidos(long id, String categoria, String produccion, int cantidade, String direccion, String cidade, int cp, String estado, long idUsuario) {
        this.id = id;
        this.categoria = categoria;
        this.produccion = produccion; //producto en BD
        this.cantidade = cantidade;
        this.direccion = direccion;
        this.cidade = cidade;
        this.cp = cp;
        this.estado = estado;
        this.idUsuario=idUsuario;
    }
    public Pedidos(String categoria, String produccion, int cantidade, String direccion, String cidade, int cp, String estado, long idUsuario) {

        this.categoria = categoria;
        this.produccion = produccion; //producto en BD
        this.cantidade = cantidade;
        this.direccion = direccion;
        this.cidade = cidade;
        this.cp = cp;
        this.estado = estado;
        this.idUsuario=idUsuario;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setProduccion(String produccion) {
        this.produccion = produccion;
    }

    public void setCantidade(int cantidade) {
        this.cantidade = cantidade;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getId() {
        return id;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getProduccion() {
        return produccion;
    }

    public int getCantidade() {
        return cantidade;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCidade() {
        return cidade;
    }

    public int getCp() {
        return cp;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "O pedido consta de "+ cantidade+" de "+ categoria + " - "+ produccion +"\n Enviarase a: "+ direccion +" - "+cidade +" - "+cp;
    }
}
