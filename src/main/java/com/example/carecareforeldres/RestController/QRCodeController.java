package com.example.carecareforeldres.RestController;
import com.example.carecareforeldres.Entity.Patient;
import com.example.carecareforeldres.Repository.PatientRepository;
import com.example.carecareforeldres.Service.QRCodeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@AllArgsConstructor
@RestController
@RequestMapping("/qr")
@CrossOrigin("*")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private PatientRepository patientService;

    @GetMapping("/patient/{id}/qrcode")
    public ResponseEntity<byte[]> generateQRCodeForPatient(@PathVariable("id") Integer id) {
        try {

            // Récupérez le patient par son ID
            Patient patient = patientService.findById(id).get();
            if (patient == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            LocalDate dateTime=LocalDate.now();
            String patientInfo = "Nom: " + patient.getNom() + ", Prénom: " + patient.getPrenom() + ", Date de naissance: " + patient.getDatedeNais() +
            ",Date de decede: " +(dateTime);

            byte[] qrCodeImage = qrCodeService.generateQRCode(patientInfo);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrCodeImage);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
