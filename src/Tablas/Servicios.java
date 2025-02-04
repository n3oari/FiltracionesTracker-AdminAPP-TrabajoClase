
package Tablas;

//CLASE PADRE DE LA QUE HEREDARAN TODAS LAS TABLAS (IG,TW...)
public class Servicios {
    
    private String usuario;
    private String correo;
    private String contraseña;
  

    public Servicios(String usuario, String correo, String contraseña) {
        this.usuario = usuario;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    @Override
    public String toString() {
        return "Servicios{" + "usuario=" + usuario + ", correo=" + correo + ", contrase\u00f1a=" + contraseña + '}';
    }
    
    
    
    
    
}
