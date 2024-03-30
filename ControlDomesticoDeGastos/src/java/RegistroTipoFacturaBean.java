
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Clase que representa un registro de los distintos tipos de facturas posibles.
 *
 * @author Ignacio
 */
public class RegistroTipoFacturaBean implements Serializable {

    private static final long serialVersionUID = 42L;
    //----------------------------------------------------------------------------------------------
    //              ATRIBUTOS OBJETO
    //---------------------------------------------------------------------------------------------- 
    //Ruta para el fichero en el que quedan registrados todos los movimientos por fecha.
    private static final File rutaFicheroRegistroTipoFactura = new File("C:" + File.separator + "Ficheros" + File.separator + "contabilidad" + File.separator + "registroTipoFacturas.dat");
    private String factura;
    private static Set<String> listadoRegistroTipoFacturas = new HashSet<>();

    //----------------------------------------------------------------------------------------------
    //              CONSTRUCTOR
    //---------------------------------------------------------------------------------------------- 
    /**
     * Leemos el listado de tipos de facturas.
     */
    public RegistroTipoFacturaBean() {
        RegistroTipoFacturaBean.listadoRegistroTipoFacturas = getListadoRegistroTipoFacturas();
    }

    //----------------------------------------------------------------------------------------------
    //              GETTERS Y SETTERS
    //---------------------------------------------------------------------------------------------- 
    /**
     * Este método deserializa el fichero para obtener un listado con todas los
     * posibles tipos de facturas.
     *
     * @return
     */
    public Set<String> getListadoRegistroTipoFacturas() {
        //Trato de deserializar el objeto
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(rutaFicheroRegistroTipoFactura))) {
            setListadoRegistroTipoFacturas((Set<String>) entrada.readObject());
        } catch (FileNotFoundException ex) {
            ex.getMessage();
        } catch (IOException ex) {
            ex.getMessage();
        } catch (Exception e) {
            e.getMessage();
        }
        return listadoRegistroTipoFacturas;
    }

    public void setListadoRegistroTipoFacturas(Set<String> listadoRegistroTipoFacturas) {
        RegistroTipoFacturaBean.listadoRegistroTipoFacturas = listadoRegistroTipoFacturas;
    }

    /**
     * Este método añade una factura a la lista y a continuación serializa.
     *
     * @throws NumberFormatException controlamos que sea un valor del tipo
     * numérico
     */
    public void addTipoFactura() throws IllegalArgumentException, IllegalStateException {
        try {
            if (factura.trim().isEmpty()) {//Eliminamos todos los espacios y si no está vacía la cadena, la guardamos
                throw new IllegalArgumentException();
            }
            if (listadoRegistroTipoFacturas.contains(factura)) {//Controlamos que no hay otro valor igual.
                throw new IllegalStateException();
            }
            //Si las verificaciones han sido exitosas, lo añadimos.
            RegistroTipoFacturaBean.listadoRegistroTipoFacturas.add(factura);
            //Trato de serializar el objeto
            try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(rutaFicheroRegistroTipoFactura))) {
                salida.writeObject(listadoRegistroTipoFacturas);
                salida.close();
            } catch (FileNotFoundException ex) {
                ex.getMessage();
            } catch (IOException ex) {
                ex.getMessage();
            } catch (Exception e) {
                e.getMessage();
            }

        } catch (IllegalArgumentException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El valor introducidos no es válido.", null + ex.getMessage()));
        } catch (IllegalStateException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El valor introducido ya existe.", null + ex.getMessage()));
        }

    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }
    /**
     * Método para limpiar la caja de texto.
     */
    public void clear() {
        this.factura = "";
    }

    /**
     * Permite eliminar un tipo de factura.
     *
     * @param factura
     */
    public void deleteFactura(String factura) {
        listadoRegistroTipoFacturas.remove(factura);
        //Trato de serializar el objeto
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(rutaFicheroRegistroTipoFactura))) {
            salida.writeObject(RegistroTipoFacturaBean.listadoRegistroTipoFacturas);
            salida.close();
        } catch (FileNotFoundException ex) {
            ex.getMessage();
        } catch (IOException ex) {
            ex.getMessage();
        } catch (Exception e) {
            e.getMessage();
        }

    }

}
