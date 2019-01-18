package com.utn.dds.tpdds.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class ReportesController {

     /*RequestMapping(value = "/submitReport", method = RequestMethod.POST)
     public String submit(@ModelAttribute("userFormData") ReportesDTO formData, @RequestParam("periodoDesde") @DateTimeFormat(pattern="yyyy-MM-dd") Date periodoD,
             @RequestParam("periodoHasta") @DateTimeFormat(pattern="yyyy-MM-dd") Date periodoH, @RequestParam("hogar") Integer hogarId, @RequestParam("transformador") String transformadorId,
             BindingResult result, Model model) {


       /*LocalDateTime periodoDesde = LocalDateTime.ofInstant(periodoD.toInstant(), ZoneId.systemDefault());
       LocalDateTime periodoHasta = LocalDateTime.ofInstant(periodoH.toInstant(), ZoneId.systemDefault());

         ArrayList<ConsumoHogar> listaConsumoHogar = new ArrayList<>();
         ConsumoHogar consumoHogar = new ConsumoHogar();
         ConsumoHogar consumoHogar2 = new ConsumoHogar();

         switch (formData.getReporte()){
             case "hogar":

                 Hogar hogar = RepositorioDeHogares.buscarPorId( hogarId );

                 ClienteResidencial cliente = hogar.getCliente();

                 List<Dispositivo> dispositivos = cliente.getDispositivos();

               for (Dispositivo dispositivo: dispositivos) {

                   Consumo consumo = dispositivo.obtenerConsumoDeUnPeriodo( periodoDesde, periodoHasta);

                       consumoHogar.setValor( consumo.getEnergiaConsumida());
                       consumoHogar.setPeriodoDesde( periodoDesde);
                       consumoHogar.setPeriodoHasta( periodoHasta );
                       consumoHogar.setObjeto( dispositivo.getNombreDelDispositivo());

                       listaConsumoHogar.add( consumoHogar );

               }

                 model.addAttribute("consumos", listaConsumoHogar );

             case "dispositivo":

                 BigDecimal intel = new BigDecimal(0);
                 BigDecimal estan = new BigDecimal(0);
                 BigDecimal consumI = new BigDecimal(0);
                 BigDecimal consumE = new BigDecimal(0);

                 List<DispositivoInteligente> lstInteligentes = RepositorioDeDispositivosInteligentes.getAll();

                 for (DispositivoInteligente dispo: lstInteligentes) {

                     Consumo consumo = dispo.obtenerConsumoDeUnPeriodo( periodoDesde, periodoHasta);

                     if (consumo != null){

                         intel = intel.add( new BigDecimal(1));


                         consumI = consumI.add(consumo.getEnergiaConsumida());
                     }

                 }

                 if (intel != null && intel.compareTo(BigDecimal.ZERO) > 0  && consumI != null && consumI.compareTo(BigDecimal.ZERO) > 0){

                     consumoHogar.setValor( consumI.divide(intel) );
                 }else{
                     consumoHogar.setValor(BigDecimal.ZERO);
                 }

                 consumoHogar.setPeriodoDesde( periodoDesde);
                 consumoHogar.setPeriodoHasta( periodoHasta );
                 consumoHogar.setObjeto("Dispositivos Inteligentes");

                 listaConsumoHogar.add(consumoHogar);


                 List<DispositivoEstandar> lstEstandar = RepositorioDeDispositivosEstandar.getAllDispositivosEstandar();

                 for (DispositivoEstandar dispo: lstEstandar) {

                     Consumo consumo = dispo.obtenerConsumoDeUnPeriodo( periodoDesde, periodoHasta);

                     if (consumo != null){

                     estan = estan.add( new BigDecimal(1));

                     consumE = consumE.add(consumo.getEnergiaConsumida());

                     }
                 }

                 if (estan != null && estan.compareTo(BigDecimal.ZERO) > 0 && consumE != null && consumE.compareTo(BigDecimal.ZERO) > 0){

                     consumoHogar2.setValor( consumE.divide(estan) );
                 }else{
                     consumoHogar2.setValor(BigDecimal.ZERO);
                 }


                 consumoHogar2.setPeriodoDesde( periodoDesde);
                 consumoHogar2.setPeriodoHasta( periodoHasta );
                 consumoHogar2.setObjeto("Dispositivos Estandar");

                 listaConsumoHogar.add(consumoHogar2);

                 model.addAttribute("consumos", listaConsumoHogar );

             case "transformador":

                 Transformador transformador = RepositorioDeTransformadores.buscarPorId( Integer.valueOf( transformadorId) );

                     consumoHogar.setValor( transformador.calcularConsumoEnUnPeriodo(periodoDesde, periodoHasta));
                     consumoHogar.setPeriodoDesde( periodoDesde);
                     consumoHogar.setPeriodoHasta( periodoHasta );
                     consumoHogar.setObjeto( "Transformador: " + String.valueOf( transformador.getId()));

                     listaConsumoHogar.add( consumoHogar );


                 model.addAttribute("consumos", listaConsumoHogar );

         }

     return "reportes";
    }*/

}
