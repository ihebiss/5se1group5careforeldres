package com.example.carecareforeldres.RestController;

import com.example.carecareforeldres.DTO.InfermierDto;
import com.example.carecareforeldres.Entity.Infermier;
import com.example.carecareforeldres.Entity.Patient;
import com.example.carecareforeldres.Service.IServiceInfermier;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/infermier")
@CrossOrigin("*")
public class InfermierController {
    IServiceInfermier serviceInfermier;
    @GetMapping("/retrieve-all-infermier")

    public List<InfermierDto> getInfermiers() {
        List<Infermier> infermiers = serviceInfermier.retrieveAllInfermiers();
        List<InfermierDto>list=infermiers.stream().map(infermier ->InfermierDto.toDto(infermier)).toList();
        return list ;

    }

    @GetMapping("/retrieve-infermier/{infermier-id}")

    public InfermierDto retrieveInfermier(@PathVariable("infermier-id") Integer idInfermier) {
        return InfermierDto.toDto(serviceInfermier.retrieveInfermier(idInfermier));
    }
    @PostMapping("/add-infermier")

    public InfermierDto addInfermier(@RequestBody InfermierDto infermier) {
        Infermier entity=InfermierDto.toEntity(infermier);
        return InfermierDto.toDto(serviceInfermier.addInfermier(entity));


    }

    @DeleteMapping("/remove-infermier/{infermier-id}")
    public void removeInfermier(@PathVariable("infermier-id") Integer idInfermier) {
        serviceInfermier.removeInfermier(idInfermier);

    }
    @PutMapping("/update-infermier")

    public InfermierDto updateInfermier(@RequestBody InfermierDto infermier) {
        Infermier entity=InfermierDto.toEntity(infermier);
        return InfermierDto.toDto(serviceInfermier.updateInfermier(entity));
    }
    @PutMapping("/infermierAff/{idinf}/{idetab}")
    @ResponseBody
    public Infermier affecterInfermierToEtab(@PathVariable("idinf") Integer idinfermier , @PathVariable("idetab")Long idEtab ) {
        return serviceInfermier.addInfermierAndAssignToEtabliss(idinfermier,idEtab);
    }
    @PutMapping("/infermierDesaff/{idinf}")
    @ResponseBody
    public Infermier InfermierUnAssignToEtabliss(@PathVariable("idinf") Integer idinfermier ) {
        return serviceInfermier.InfermierUnAssignToEtabliss(idinfermier);
    }
    @PutMapping("/addall-infermier/{idetab}")
    public List<Infermier>  affecterInfermierToEtab( @RequestBody List<Infermier> infermiers,@PathVariable("idetab") Long idEtab) {
        return serviceInfermier.ajouterInfermierToEtab(infermiers,idEtab);
    }
    @GetMapping("/retrieve-patientInfer/{infermier-id}")

    public List<Patient> findPatientEtabEqualInfEtab(@PathVariable("infermier-id") Integer idInfermier) {
        return serviceInfermier.findPatientEtabEqualInfEtab(idInfermier);
    }
}

