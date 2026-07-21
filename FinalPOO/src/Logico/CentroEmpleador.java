package Logico;
import java.io.Serializable;

/*Entidad que representa un centro empleador o empresa*/
public class CentroEmpleador implements Serializable 
{
	    /*RNC que identifica al centro empleador*/
	    private String rnc;

	    /* Nombre del centro empleador*/
	    private String nombre;

	    /*Reputacion del centro empleador*/
	    private float reputacion;

	    /*Sector al que pertenece la empresa*/
	    private String sector;

	    /*Direccion del centro empleador*/
	    private String direccion;
	    
	    private static final long serialVersionUID = 1L;

	    public CentroEmpleador() {
	        // Inicializas la reputación en cero, como tenías en tu código original
	        this.reputacion = 0.0f; 
	        
	        // Si tienes tu clase de ConstantesGlobales puedes usarla así:
	        // this.reputacion = ConstantesGlobales.VALOR_FLOTANTE_CERO;
	    }

	    
	    public CentroEmpleador(String rnc, String nombre, String sector, String direccion) {
	        this.rnc = rnc;
	        this.nombre = nombre;
	        this.sector = sector;
	        this.direccion = direccion;
	        this.reputacion = 0.0f;
	    }
	    
	    
	    /*
	     *Obtiene el RNC del centro empleador
	      @return Cadena con el RNC
	     */
	    public String getRnc() 
	    {
	        return rnc;
	    }

	    /*
	     Obtiene el nombre del centro empleado
	     @return Cadena con el nombre
	    */
	    public String getNombre() 
	    {
	        return nombre;
	    }

	    /*
	     Obtiene la reputacion del centro empleador
	     @return Valor flotante con la reputacion
	    */
	    public float getReputacion() 
	    {
	        return reputacion;
	    }
	    
	    /*
	     Establece el RNC del centro empleador
	     @param rnc Cadena de texto con el RNC
	    */
	    public void setRnc(String rnc) 
	    {
	        this.rnc = rnc;
	    }

	    /*
	     Establece el nombre del centro empleador
	     @param nombre Cadena de texto con el nombre
	    */
	    public void setNombre(String nombre) 
	    {
	        this.nombre = nombre;
	    }
	    
	    /*
	     Establece la reputacion del centro empleador.
	     @param reputacion Valor flotante a asignar.
	    */
	    public void setReputacion(float reputacion) 
	    {
	        this.reputacion = reputacion;
	    }

	    /*
	     Establece el sector del centro empleador.
	     @param sector Cadena de texto con el sector.
	    */
	    public void setSector(String sector) 
	    {
	        this.sector = sector;
	    }

	    /*
	      Establece la direccion del centro empleador.
	      @param direccion Cadena de texto con la direccion.
	    */
	    public void setDireccion(String direccion) 
	    {
	        this.direccion = direccion;
	    }
}
