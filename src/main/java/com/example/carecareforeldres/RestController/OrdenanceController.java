package com.example.carecareforeldres.RestController;

import com.example.carecareforeldres.DTO.OrdennanceDTO;
import com.example.carecareforeldres.Entity.Ordonnance;
import com.example.carecareforeldres.Entity.Patient;
import com.example.carecareforeldres.Entity.Rdv;
import com.example.carecareforeldres.Repository.OrdenanceRepository;
import com.example.carecareforeldres.Repository.PatientRepository;
import com.example.carecareforeldres.Repository.RdvRepository;
import com.example.carecareforeldres.Service.OrdenanceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/ordenance")
@CrossOrigin("*")
public class OrdenanceController {
OrdenanceService ordenanceService;
OrdenanceRepository ordenanceRepository;
RdvRepository rdvRepository;
    @PostMapping("/add/{MedId}")
    public Ordonnance ajouterOrd(@RequestBody Ordonnance res,@PathVariable("MedId") Integer MedId) {
        Ordonnance p1 = ordenanceService.addOrd(res,MedId);
        return p1;
    }

    @PostMapping("/addOrdDTO/{MedId}")
    public OrdennanceDTO addOrdDTO(@RequestBody OrdennanceDTO res, @PathVariable("MedId") Integer MedId) {
        OrdennanceDTO p1 = ordenanceService.addOrdDTO(res,MedId);
        return p1;
    }

    PatientRepository patientRepository;
    @GetMapping("/retrive_all_patient")
    public List<Patient> retrieveCoungeList() {

        return patientRepository.findAll();
    }
    @GetMapping("/retrive_all_Ord")
    public List<Ordonnance> retrieveOrdonnanceList() {
        return  ordenanceService.getAllOrd();

    }
    @GetMapping("/retrive_all_Ord/{idMed}")
    public List<Ordonnance> retrieveOrdonnanceListByMedecin(@PathVariable("idMed") Integer idMed) {
        return  ordenanceService.getAllOrdByMed(idMed);

    }
    @PutMapping("/update_ord")
    public Ordonnance updateMedicament(@RequestBody Ordonnance med){

        return ordenanceService.update(med);
    }

    @GetMapping("/retrive_ord/{resId}")
    public Ordonnance retrieveOrdonnance(@PathVariable("resId") Integer resId) {

        return ordenanceRepository.findById(resId).get();
    }



    @DeleteMapping("/delete_ord/{coungeId}")
    public void deleteOrdonnance(@PathVariable("coungeId") Integer coungeId) {
        ordenanceService.remove(coungeId);
    }


    @GetMapping("/getOrdonnancesByPatient/{idPatient}")
    public List<Ordonnance> getOrdonnancesByPatient(@PathVariable("idPatient") Integer idPatient) {
        return  ordenanceService.getOrdonnancesByPatient(idPatient);
    }

    @GetMapping("/getProchainRendezVousDuMedecin/{idMed}")
    public LocalDateTime getProchainRendezVousDuMedecin(@PathVariable("idMed") Integer idMed) {
        return  ordenanceService.getProchainRendezVousDuMedecin(idMed);
    }


    @GetMapping("/getRendezVousDuMedecin/{medecinId}")
    public List<Rdv> getRendezVousDuMedecin(@PathVariable Integer medecinId) {
        List<Rdv> rendezVous = ordenanceService.getRendezVousDuMedecin(medecinId);
        return rendezVous;
    }
}
