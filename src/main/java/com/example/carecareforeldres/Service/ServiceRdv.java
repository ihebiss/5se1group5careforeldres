package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.Configurations.PauseExep;
import com.example.carecareforeldres.Entity.Medecin;
import com.example.carecareforeldres.Entity.Patient;
import com.example.carecareforeldres.Entity.Rdv;
import com.example.carecareforeldres.Repository.MedecinRepository;
import com.example.carecareforeldres.Repository.PatientRepository;
import com.example.carecareforeldres.Repository.RdvRepository;
import com.example.carecareforeldres.demo.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ServiceRdv implements IServiceRdv {
    RdvRepository rdvRepository;
    MedecinRepository medecinRepository;
    PatientRepository patientReopository;
    private final EmailService emailService;

    @Override
    public Rdv addRdv(Rdv rdv) {
        return rdvRepository.save(rdv);
    }

    @Override
    public List<Rdv> retrieveAllRdvs() {
        return rdvRepository.findAll();
    }

    @Override
    public Rdv updateRdv(Rdv rdv) {
        return rdvRepository.save(rdv);
    }

    @Override
    public Rdv retrieveRdv(Long idRDV) {
        return rdvRepository.findById(idRDV).get();
    }

    @Override
    public void removeRdv(Long idRDV) {
        rdvRepository.deleteById(idRDV);

    }


    @Override
    public Rdv AddRdvAndAssign(Rdv rdv, Integer idMedecin, Integer idPatient, LocalDateTime dateRDV) {
        Medecin medecin = medecinRepository.findById(idMedecin).get();
        Patient patient = patientReopository.findById(idPatient).get();

        if (dateRDV.getHour() >= 12 && dateRDV.getHour() < 15) {
            throw new PauseExep("Nous sommes en Pause");
        }

        List<Rdv> listeRDV = rdvRepository.findLastByMedecin(idMedecin);
        if (!listeRDV.isEmpty()) {
            Rdv lastRdv = listeRDV.get(0);
            if (Duration.between(lastRdv.getDateRDV(), dateRDV).toHours() < 1) {
                dateRDV = lastRdv.getDateRDV().plusHours(1);

            }
        }

        if (medecin.getDisponible().equals(true)||medecin.getRdvs().isEmpty()){
            rdv.setPatient(patient);
            rdv.setMedecin(medecin);
            rdv.setDateRDV(dateRDV);
            rdv.setArchiver(false);
            rdvRepository.save(rdv);
        }
        String patientEmail = patient.getMail();
        String subject = "Confirmation de rendez-vous";
        String message = "Cher " + patient.getNom() + ",\n\nVotre rendez-vous avec le Dr " + medecin.getNom() +
                " a été confirmé pour le " + rdv.getDateRDV() + ".\n\nCordialement,\n" + medecin.getNom();

        try {
            emailService.sendEmail(patientEmail, subject, message);
            log.info("E-mail de confirmation envoyé avec succès à : " + patientEmail);
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi de l'e-mail de confirmation : " + e.getMessage());
        }


        return rdv;
    }








    @Override
    public void exportRdvToExcel(String fileName, Integer idMedecin) throws IOException {
        List<Rdv> rdvs = rdvRepository.findLastByMedecin(idMedecin);


        String desktopPath = System.getProperty("user.home") + "/Desktop/ExelPi/";
        String filePath = desktopPath + fileName;

        if (!fileName.endsWith(".xlsx")) {
            filePath += ".xlsx";
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Rendez-Vous");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("RDV ID");
            header.createCell(1).setCellValue("Médecin");
            header.createCell(2).setCellValue("Patient");
            header.createCell(3).setCellValue("Date Rdv");
            header.createCell(4).setCellValue("Archiver");
            sheet.setColumnWidth(1, 30 * 256);
            sheet.setColumnWidth(2, 30 * 256);
            sheet.setColumnWidth(3, 30 * 256);



            int rowNum = 1;
            for (Rdv rdv : rdvs) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rdv.getIdRDV());
                row.createCell(1).setCellValue(rdv.getMedecin().getNom()+" "+(rdv.getMedecin().getPrenom()));
                row.createCell(2).setCellValue(rdv.getPatient().getNom()+" "+(rdv.getPatient().getPrenom()));
                row.createCell(3).setCellValue(rdv.getDateRDV().toString());
                row.createCell(4).setCellValue(rdv.isArchiver());
            }

             FileOutputStream fileOut = new FileOutputStream(filePath);
                workbook.write(fileOut);
            }
    }
 // @Scheduled(cron = "* * * * * *")
    @Override
    public void verifierEtatMedecin() {
        LocalDateTime heure=LocalDateTime.now();
            for (Medecin m:medecinRepository.findAll()){
                for (Rdv r :m.getRdvs()){
                    LocalDateTime debut = r.getDateRDV();
                    LocalDateTime fin = debut.plusHours(1);
            if (heure.isBefore(fin) && heure.isAfter(debut)||(heure.getHour() >= 12 && heure.getHour() < 15)) {
                    m.setDisponible(false);
                medecinRepository.save(m);
            }
            else
            {
                m.setDisponible(true);
                 medecinRepository.save(m);
    }
        }
}
}

    @Override
    public List<Rdv> retrieveAllRdvsByMedecin(Integer idMedecin) {
        return rdvRepository.findLastByMedecin(idMedecin);
    }


  //  @Scheduled(cron = "* * * * * *")
    @Override
    public void ArchiverRdv() {
        LocalDateTime now = LocalDateTime.now();
        for (Rdv r : rdvRepository.findAll()) {
            if (now.isAfter(r.getDateRDV().plusHours(1))) {
                r.setArchiver(true);
                rdvRepository.save(r);
            }
        }

        }


    }


