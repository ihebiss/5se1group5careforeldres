package com.example.carecareforeldres.RestController;

import com.example.carecareforeldres.DTO.RdvDto;
import com.example.carecareforeldres.Entity.Rdv;
import com.example.carecareforeldres.Service.IServiceRdv;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/rdv")
@CrossOrigin("*")
public class RdvController {
    IServiceRdv serviceRdv;

    @GetMapping("/retrieve-all-rdv")
    /*public List<Rdv> getRdvs() {
        List<Rdv> rdvs = serviceRdv.retrieveAllRdvs();
        return rdvs;*/
    public List<RdvDto> getRdvs() {
        List<Rdv> rdvs = serviceRdv.retrieveAllRdvs();
        List<RdvDto> list = rdvs.stream().map(rdv -> RdvDto.toDto(rdv)).toList();
        return list;

    }

    @GetMapping("/retrieve-rdv/{rdv-id}")
    /*public Rdv retrieveRdv(@PathVariable("rdv-id") Long idRDV) {
        return serviceRdv.retrieveRdv(idRDV);*/
    public RdvDto retrieveRdv(@PathVariable("rdv-id") Long idRDV) {
        return RdvDto.toDto(serviceRdv.retrieveRdv(idRDV));
    }

    @PostMapping("/add-rdv")
   /*public Rdv addRdv(@RequestBody Rdv rdv) {
        return serviceRdv.addRdv(rdv);*/
    public RdvDto addRdv(@RequestBody RdvDto rdv) {
        Rdv entity = RdvDto.toEntity(rdv);
        return RdvDto.toDto(serviceRdv.addRdv(entity));


    }

    @DeleteMapping("/remove-rdv/{rdv-id}")
    public void removeRdv(@PathVariable("rdv-id") Long idRDV) {
        serviceRdv.removeRdv(idRDV);
    }

    @PutMapping("/update-rdv")
    /*public Rdv updateRdv(@RequestBody Rdv rdv) {
        return serviceRdv.updateRdv(rdv);*/
    public RdvDto updateRdv(@RequestBody RdvDto rdv) {
        Rdv entity = RdvDto.toEntity(rdv);
        return RdvDto.toDto(serviceRdv.updateRdv(entity));
    }

    @PutMapping("/affect-rdv/{idMedecin}/{idPatient}/{dateRDV}")
    @ResponseBody
    public Rdv AffecterUnRdv(@RequestBody Rdv rdv, @PathVariable("idMedecin") Integer idMedecin,
                             @PathVariable("idPatient") Integer idPatient,
                             @PathVariable("dateRDV") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateRDV) {

        return serviceRdv.AddRdvAndAssign(rdv, idMedecin, idPatient, dateRDV);
    }

    @GetMapping("/rdv/export/{idMedecin}")
    public ResponseEntity<String> exportRDVsToExcel(@RequestParam String fileName, @PathVariable("idMedecin") Integer idMedecin) throws IOException {
        String excelFileName = fileName.endsWith(".xlsx") ? fileName : fileName + ".xlsx";
        serviceRdv.exportRdvToExcel(excelFileName, idMedecin);
        return ResponseEntity.ok("RDVs vers le fichier Excel: " + excelFileName);
    }
    @GetMapping("/retrieveMed-rdv/{idMedecin}")
    /*public Rdv retrieveRdv(@PathVariable("rdv-id") Long idRDV) {
        return serviceRdv.retrieveRdv(idRDV);*/
    public List<Rdv> retrieveAllRdvsByMedecin(@PathVariable("idMedecin")  Integer idMedecin) {
        return serviceRdv.retrieveAllRdvsByMedecin(idMedecin);
    }
}

