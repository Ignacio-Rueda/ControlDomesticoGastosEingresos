
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ignacio
 */
public class FacturasPorCategoriaBean {
   //Ruta para el fichero en el que quedan registrados todos los movimientos por fecha.
    private static final File rutaFicheroRegistroPorFecha = new File("C:" + File.separator + "Ficheros" + File.separator + "contabilidad" + File.separator + "registroFacturas.dat");
    private String nombre;
    private String mes;
    private String anyo;
    private List<FacturaBean> listaLecturaFichero;//Lista con los datos que actualmente tenemos registrados.
    private List<FacturaBean> listaDatosFiltrados;//Lista con los datos filtrados, según el criterio indicado.

    //----------------------------------------------------------------------------------------------
    //              GETTERS Y SETTERS
    //---------------------------------------------------------------------------------------------- 

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public List<FacturaBean> getListaLecturaFichero() {
                //Trato de deserializar el objeto
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(rutaFicheroRegistroPorFecha))) {
            setListaLecturaFichero((List<FacturaBean>) entrada.readObject());
        } catch (FileNotFoundException ex) {
            ex.getMessage();
        } catch (IOException ex) {
            ex.getMessage();
        } catch (Exception e) {
            e.getMessage();
        }
        return listaLecturaFichero;
    }

    public void setListaLecturaFichero(List<FacturaBean> listaLecturaFichero) {
        this.listaLecturaFichero = listaLecturaFichero;
    }

    public List<FacturaBean> getListaDatosFiltrados() {
        return listaDatosFiltrados;
    }

    public void setListaDatosFiltrados(List<FacturaBean> listaDatosFiltrados) {
        this.listaDatosFiltrados = listaDatosFiltrados;
    }
    
    
}
