
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Clase que representa un registro para la gestión de facturas. Los objetos de
 * esta clase permiten almacenar y gestionar un lista de objetos del tipo
 * FacturaBean.
 *
 * @author Ignacio
 */
public class RegistroFacturasBean implements Serializable {

    private static final long serialVersionUID = 42L;
    //----------------------------------------------------------------------------------------------
    //              ATRIBUTOS OBJETO
    //---------------------------------------------------------------------------------------------- 
    //Ruta para el fichero en el que quedan registrados todos los movimientos por fecha.
    private static final File rutaFicheroRegistroPorFecha = new File("C:" + File.separator + "Ficheros" + File.separator + "contabilidad" + File.separator + "registroFacturas.dat");
    private FacturaBean factura = new FacturaBean();
    private static List<FacturaBean> listadoRegistroFacturas = new ArrayList<>();


    //----------------------------------------------------------------------------------------------
    //              CONSTRUCTOR
    //---------------------------------------------------------------------------------------------- 
    /**
     * Leemos las lista de RegistroFacturas que tenemos registradas y las guardamos en la lista,
 para a continuación poder escribir y no perder los cambios.
     */
    public RegistroFacturasBean() {
        RegistroFacturasBean.listadoRegistroFacturas = getListadoRegistroFacturas();
    }

    //----------------------------------------------------------------------------------------------
    //              GETTERS Y SETTERS
    //---------------------------------------------------------------------------------------------- 
    /**
     * Este método deserializa el fichero para obtener un listado con todas las
     * factuas.
     *
     * @return
     */
    public List<FacturaBean> getListadoRegistroFacturas() {
        //Trato de deserializar el objeto
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(rutaFicheroRegistroPorFecha))) {
            setListadoRegistroFacturas((List<FacturaBean>) entrada.readObject());
        } catch (FileNotFoundException ex) {
            ex.getMessage();
        } catch (IOException ex) {
            ex.getMessage();
        } catch (Exception e) {
            e.getMessage();
        }
        return listadoRegistroFacturas;
    }

    public void setListadoRegistroFacturas(List<FacturaBean> listadoRegistroFacturas) {
        RegistroFacturasBean.listadoRegistroFacturas = listadoRegistroFacturas;
    }

    /**
     * Este método añade una factura a la lista y a continuación serializa.
     *
     * @throws NumberFormatException controlamos que sea un valor del tipo
     * numérico
     */
    public void addFactura() throws NumberFormatException,NullPointerException {
            
        try {
            if(this.factura.getNombre()== null){
                throw new NullPointerException();
            }
            //Si la cifra contiene decimales con comas, reemplazamos por punto.
            String importeSignoPuntuacion = this.factura.getImporte();
            importeSignoPuntuacion = importeSignoPuntuacion.replace(",", ".");
            this.factura.setImporte(importeSignoPuntuacion);
            //Comprobamos que el importe es de tipo numérico.
            Double importeDouble;
            importeDouble = Double.parseDouble(this.factura.getImporte());
            // Conservar solo las últimas dos cifras decimales y lo seteamos.
            double numeroRedondeado = Math.round(importeDouble * 100) / 100.0;
            this.factura.setImporte(String.valueOf(numeroRedondeado));
            //Si las verificaciones han sido correctas,añadimos la factura.
            RegistroFacturasBean.listadoRegistroFacturas.add(factura);
            //Trato de serializar el objeto
            try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(rutaFicheroRegistroPorFecha))) {
                salida.writeObject(listadoRegistroFacturas);
                salida.close();
            } catch (FileNotFoundException ex) {
                ex.getMessage();
            } catch (IOException ex) {
                ex.getMessage();
            } catch (Exception e) {
                e.getMessage();
            }
        } catch (NumberFormatException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El número introducido no es válido", null + ex.getMessage()));
        } catch (NullPointerException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar un tipo de factura", null + ex.getMessage()));
        }

    }

    public FacturaBean getFactura() {
        return factura;
    }

    public void setFactura(FacturaBean factura) {
        this.factura = factura;
    }

    /**
     * Permite eliminar una factura.
     *
     * @param factura
     */
    public void deleteFactura(FacturaBean factura) {
        RegistroFacturasBean.listadoRegistroFacturas.remove(factura);
        //Trato de serializar el objeto
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(rutaFicheroRegistroPorFecha))) {
            salida.writeObject(RegistroFacturasBean.listadoRegistroFacturas);
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
