
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    //----------------------------------------------------------------------------------------------
    //              ATRIBUTOS OBJETO
    //---------------------------------------------------------------------------------------------- 
    //Ruta para el fichero en el que quedan registrados todos los movimientos por fecha.
    private static final File rutaFicheroRegistroPorFecha = new File("C:" + File.separator + "Ficheros" + File.separator + "contabilidad" + File.separator + "registroFacturas.dat");
    private String nombre;
    private String anyo;
    private String mes;
    private static List<FacturaBean> listaLecturaFichero = new ArrayList<>();//Lista con los datos que actualmente tenemos registrados.
    private List<FacturaBean> listaDatosFiltrados = new ArrayList<>();//Lista con los datos filtrados, según el criterio indicado.
    private Set<String> listaAnios = new HashSet<>();//Lista con todos los años registrados hasta el momento.
    private Set<String> listaNombresFactura = new HashSet<>();//Lista con todos los tipos de factura + "todos" 
    //----------------------------------------------------------------------------------------------
    //              CONSTRUCTOR
    //---------------------------------------------------------------------------------------------- 

    /**
     * Leemos las lista de RegistroFacturas que tenemos registradas y las
     * guardamos en la lista, para a continuación poder ordenar según los
     * criterios indicados..
     */
    public FacturasPorCategoriaBean() {
        FacturasPorCategoriaBean.listaLecturaFichero = getListaLecturaFichero();
        listaAnios = getListaAnios();
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

    public String getAnyo() {
        return anyo;
    }

    public void setAnyo(String anyo) {
        this.anyo = anyo;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
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
        FacturasPorCategoriaBean.listaLecturaFichero = listaLecturaFichero;
    }

    /**
     * Con este método podemos localizar las factura/s con los criterios
     * introducidos.
     *
     * @return
     */
    public void localizarFactura() {
        switch (nombre) {
            case "TODAS":
                for (FacturaBean factura : FacturasPorCategoriaBean.listaLecturaFichero) {
                    //Si la factura coincide con el AÑO,MES y TODAS, la almacenamos en listaDatosFiltrados.
                    if (factura.getAnyo().equals(anyo) && factura.getMes().equals(mes)) {
                        listaDatosFiltrados.add(factura);
                    }
                }
                break;
            default:
                for (FacturaBean factura : FacturasPorCategoriaBean.listaLecturaFichero) {
                    //Si la factura coincide con el AÑO,MES y NOMBRE, la almacenamos en listaDatosFiltrados.
                    if (factura.getAnyo().equals(anyo) && factura.getMes().equals(mes) && factura.getNombre().equals(nombre)) {
                        listaDatosFiltrados.add(factura);
                    }
                }
        }
    }

    /**
     * Obtenemos la lista con los criterios introducidos.
     *
     * @return
     */
    public List<FacturaBean> getListaDatosFiltrados() {
        return listaDatosFiltrados;
    }

    public void setListaDatosFiltrados(List<FacturaBean> listaDatosFiltrados) {
        this.listaDatosFiltrados = listaDatosFiltrados;
    }

    /**
     * Obtenemos todos los años registrados hastta el momento.
     *
     * @return
     */
    public Set<String> getListaAnios() {
        for (FacturaBean factura : FacturasPorCategoriaBean.listaLecturaFichero) {
            listaAnios.add(factura.getAnyo());
        }
        return listaAnios;
    }

    public void setListaAnios(List<String> listaAnios) {
        listaAnios = listaAnios;
    }

    /**
     * Obtenemos todos los nombres de facturas registrados hasta el momento.
     * Añadimos "TODAS", para poder buscar por cualquier nombre.
     *
     * @return
     */
    public Set<String> getListaNombresFactura() {
        for (FacturaBean factura : FacturasPorCategoriaBean.listaLecturaFichero) {
            listaNombresFactura.add(factura.getNombre());
        }
        listaNombresFactura.add("TODAS");
        return listaNombresFactura;
    }

    public void setListaNombresFactura(Set<String> listaNombresFactura) {
        this.listaNombresFactura = listaNombresFactura;
    }

}
