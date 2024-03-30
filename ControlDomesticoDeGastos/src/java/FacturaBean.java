
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Ignacio
 */
/**
 * *
 * Clase que representa una factura para un registro de facturas.
 *
 * @author Ignacio
 */
public class FacturaBean implements Serializable {

    private static final long serialVersionUID = 42L;
//----------------------------------------------------------------------------------------------
//              ATRIBUTOS OBJETO
//---------------------------------------------------------------------------------------------- 
    private String nombre;
    private String importe;
    private String mes;
    private String anyo;
    private List<String> listaMeses = new ArrayList<>();//Meses disponibles.
    private LocalDate fechaActual;

//----------------------------------------------------------------------------------------------
//              CONSTRUCTOR
//---------------------------------------------------------------------------------------------- 
    public FacturaBean() {
        //Conocemos la fecha actual y obtenemos el año.
        fechaActual = LocalDate.now();
        anyo = String.valueOf(fechaActual.getYear());
        //Almacenamos todos los meses disponibles a mostrar.
        listaMeses.add("ENERO");
        listaMeses.add("FEBRERO");
        listaMeses.add("MARZO");
        listaMeses.add("ABRIL");
        listaMeses.add("MAYO");
        listaMeses.add("JUNIO");
        listaMeses.add("JULIO");
        listaMeses.add("AGOSTO");
        listaMeses.add("SEPTIEMBRE");
        listaMeses.add("OCTUBRE");
        listaMeses.add("NOVIEMBRE");
        listaMeses.add("DICIEMBRE");
    }
//----------------------------------------------------------------------------------------------
//              GETTERS Y SETTERS
//----------------------------------------------------------------------------------------------  

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnyo() {
        return anyo;
    }

    public void setAnyo(String anyo) {
        this.anyo = anyo;
    }

    public List<String> getListaMeses() {
        return listaMeses;
    }

    public void setListaMeses(List<String> listaMeses) {
        this.listaMeses = listaMeses;
    }

    /**
     * Método para limpiar el inputText.
     */
    public void clearImporte() {
        this.importe = "";
    }
 //----------------------------------------------------------------------------------------------
//              EQUALS & HASHCODE
//----------------------------------------------------------------------------------------------  

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FacturaBean other = (FacturaBean) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

    
    @Override
    public String toString() {
        return String.format("NOMBRE FACTURA: %s MES: %s AÑO %s IMPORTE %s",
                this.nombre,
                this.mes,
                this.anyo,
                this.importe
        );
    }
}
