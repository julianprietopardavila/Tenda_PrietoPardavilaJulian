package cursoandroid.cursoandroid.tenda_prietopardavilajulian.basesdedatos;

public class Usuarios {
    private long id;
    private String nome;
    private String primer_apelido;
    private String segundo_apelido;
    private String email;
    private String alcume;
    private String contrasinal;
    private int admin;

    public Usuarios(long id, String nome, String primer_apelido, String segundo_apelido, String email, String alcume, String contrasinal, int admin) {
        this.id = id; //id en BD
        this.nome = nome; //nome en BD
        this.primer_apelido = primer_apelido; ///primapelido en BD
        this.segundo_apelido = segundo_apelido; //secapelido en BD
        this.email = email; //email en BD
        this.alcume = alcume; //alcume en BD
        this.contrasinal = contrasinal; //contrasinal en BD
        this.admin = admin; //admin en BD
    }
    public Usuarios(String nome, String primer_apelido, String segundo_apelido, String email, String alcume, String contrasinal, int admin) {
        this.nome = nome; //nome en BD
        this.primer_apelido = primer_apelido; ///primapelido en BD
        this.segundo_apelido = segundo_apelido; //secapelido en BD
        this.email = email; //email en BD
        this.alcume = alcume; //alcume en BD
        this.contrasinal = contrasinal; //contrasinal en BD
        this.admin = admin; //admin en BD
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPrimer_apelido() {
        return primer_apelido;
    }

    public void setPrimer_apelido(String primer_apelido) {
        this.primer_apelido = primer_apelido;
    }

    public String getSegundo_apelido() {
        return segundo_apelido;
    }

    public void setSegundo_apelido(String segundo_apelido) {
        this.segundo_apelido = segundo_apelido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlcume() {
        return alcume;
    }

    public void setAlcume(String alcume) {
        this.alcume = alcume;
    }

    public String getContrasinal() {
        return contrasinal;
    }

    public void setContrasinal(String contrasinal) {
        this.contrasinal = contrasinal;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }
}
