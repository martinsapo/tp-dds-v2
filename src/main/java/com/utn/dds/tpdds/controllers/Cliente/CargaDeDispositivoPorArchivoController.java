package com.utn.dds.tpdds.controllers.Cliente;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
@RequestMapping(value = "/cargaDeDispositivoDesdeArchivo")
public class CargaDeDispositivoPorArchivoController {

    private static String UPLOADED_FOLDER = "./src/test/json/";

    @RequestMapping(value = "")
    public String cargaDeDispositivoDesdeArchivo() {
        return "cargaDeDispositivoDesdeArchivo";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Seleccione un archivo a cargar");
            return "redirect:uploadStatus";
        }

        try {

            byte[] bytes = file.getBytes();
            String root = getClass().getResource(".").getPath();
            String path = root.substring(0,root.lastIndexOf("target"));

            File myFoo = new File(path +"archivos/dispositivos.json");
            FileOutputStream fooStream = new FileOutputStream(myFoo, false);
            fooStream.write(bytes);
            fooStream.close();
            redirectAttributes.addFlashAttribute("message", "Se cargo correctamente el archivo '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }

    @RequestMapping(value = "/uploadStatus" ,method = RequestMethod.GET)
    public String uploadStatus() {
        return "uploadStatus";
    }

}