package com.utn.dds.tpdds.model;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ListaDeZonasGeograficasYTransformadores {
    private ArrayList<ZonaGeografica> listaDeZonasGeograficas;
    private ArrayList<Transformador> listaDeTransformadores;
    private ArrayList<Hogar> listaDeHogares = new ArrayList<>();

    public void agregarTransformadores(String jsonTransformadores){
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Transformador.class);
        try{
            this.listaDeTransformadores = mapper.readValue(new File(jsonTransformadores),type);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void agregarZonasGeograficas(String jsonZonasGeograficas){
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, ZonaGeografica.class);
        try{
            this.listaDeZonasGeograficas = mapper.readValue(new File(jsonZonasGeograficas),type);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void agregarHogar(Hogar hogar) {
        Transformador transformador = buscarTransformadorMasCercano(hogar);
        hogar.setTransformador(transformador);
        transformador.agregarHogar(hogar);
        this.listaDeHogares.add(hogar);
    }

    public Transformador buscarTransformadorMasCercano(Hogar hogar) {
        Transformador transformadorMasCercano = this.listaDeTransformadores.get(0);
        double distanciaMasCercana = Helper.calcularDistancia(transformadorMasCercano.getLatitud(), transformadorMasCercano.getLongitud(), hogar.getLatitud(), hogar.getLongitud());
        for(Transformador transformador : this.listaDeTransformadores){
            double distancia = Helper.calcularDistancia(transformador.getLatitud(), transformador.getLongitud(), hogar.getLatitud(), hogar.getLongitud());
            if (distancia < distanciaMasCercana){
                distanciaMasCercana = distancia;
                transformadorMasCercano = transformador;
            }
        }
        return transformadorMasCercano;
    }

    /*public <T> void agregarALista(String json, Class<T> clase){
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clase.getClass());

        try{
            if (clase.getClass().equals(ZonaGeografica.class)) {
                this.listaDeZonasGeograficas = mapper.readValue(new File(json),type);
            } else if (clase.getClass().equals(Transformador.class)) {
                this.listaDeTransformadores = mapper.readValue(new File(json),type);
            } else {
                System.out.println("No es un transformador ni una zona geografica");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }*/

    public ArrayList<ZonaGeografica> getListaDeZonasGeograficas() {
        return listaDeZonasGeograficas;
    }

    public ArrayList<Transformador> getListaDeTransformadores() {
        return listaDeTransformadores;
    }
}
