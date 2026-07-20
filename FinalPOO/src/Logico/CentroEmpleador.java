package Logico;

/*Entidad que representa un centro empleador o empresa*/
public class CentroEmpleador 
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

	    public class CentroEmpleador {


	        /*Constructor por defecto para inicializar el centro empleador*/
	         
	        public CentroEmpleador() {
	            this.reputacion = ConstantesGlobales.VALOR_FLOTANTE_CERO;
	        }

	    }
}
