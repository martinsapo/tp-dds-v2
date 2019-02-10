package com.utn.dds.tpdds.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Helper implements Serializable{
    public static Object mapJsonToObject(String json, Class aClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        try {
            return objectMapper.readValue(json, aClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Este es solo para cuando tenes un json que en su interior es una lista de JSON
    public static JSONArray mapJsonToArrayJSON(String pathToJSONFile) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(pathToJSONFile));
            JSONArray arrayJSON = (JSONArray) obj;
            return arrayJSON;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void objectToJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                .withSetterVisibility(JsonAutoDetect.Visibility.ANY)
                .withCreatorVisibility(JsonAutoDetect.Visibility.ANY));
        objectMapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        try {
            objectMapper.writeValue(new File("/Users/msaposnic/Documents/utn/tp-dds/src/test/json/reglas.json"), object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void registrarRegla(Regla regla, String nombreRegla) throws FileNotFoundException {
        serializeObject(regla, new FileOutputStream("/Users/msaposnic/Documents/utn/tp-dds/src/test/reglas/" + nombreRegla + ".json"));
    }

    public static void serializeObject(Object object, FileOutputStream file) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(object);
            out.close();
            file.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static Object deserializeObject(FileInputStream fileInputStream) {
        try {
            ObjectInputStream in = new ObjectInputStream(fileInputStream);
            Object e = in.readObject();
            in.close();
            fileInputStream.close();
            return e;
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return null;
        }
    }

    public static ArrayList<DispositivoInteligente> mapJsonToArray(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd,/MM/yyyy"));
        try {
            return  objectMapper.readValue(json, new TypeReference<List<DispositivoInteligente>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        return new String(data, "UTF-8");
    }

    public static String mapObjectToJson(Object object) throws
            JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    public static double calcularDistancia(double latitud1, double longitud1, double latitud2, double longitud2) {

        double r = 6371;

        double dLat  = Math.toRadians((latitud2 - latitud1));
        double dLong = Math.toRadians((longitud2 - longitud1));

        latitud1 = Math.toRadians(latitud1);
        latitud2   = Math.toRadians(latitud2);

        double a = haversin(dLat) + Math.cos(latitud1) * Math.cos(latitud2) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return r * c; // <-- d
    }

    public static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
