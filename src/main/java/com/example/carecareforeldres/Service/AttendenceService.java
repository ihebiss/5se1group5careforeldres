package com.example.carecareforeldres.Service;


import com.example.carecareforeldres.Entity.Attendence;
import com.example.carecareforeldres.Entity.Cuisinier;
import com.example.carecareforeldres.Repository.AttendenceRepository;
import com.example.carecareforeldres.Repository.CuisinierRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AttendenceService implements IAttendenceService {

    private final AttendenceRepository attendenceRepository;
    private final CuisinierRepository userRepository;

    public AttendenceService(AttendenceRepository attendenceRepository,CuisinierRepository userRepository) {
        this.attendenceRepository = attendenceRepository;
        this.userRepository=userRepository;
    }

    @Override
    public List<Attendence> retrieveAllAttendence() {
        return attendenceRepository.findAll();
    }

    @Override
    public Attendence retrieveAttendence(Long attendenceId) {
        return attendenceRepository.findById(attendenceId).orElse(null);
    }

//    @Override
//    public Attendence addAttendence(Attendence attendence) {
//        return attendenceRepository.save(attendence);
//    }
    //update add user id to patth add
@Override
public Attendence addAttendence(Integer userId,Attendence attendence) {
    Cuisinier user = new Cuisinier();
    user= userRepository.findById(userId).get();
        attendence.setCuisinier(user);
    return attendenceRepository.save(attendence);
}


    @Override
    public void removeAttendence(Long attendenceId) {
        attendenceRepository.deleteById(attendenceId);
    }

    @Override
    public Attendence modifyAttendence(Attendence attendence) {
        return null;
    }
    @Override
    public Long startAttendance(Integer userId) {
        boolean az=true;
        Attendence attendence = new Attendence();
        Optional<Cuisinier> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
                Cuisinier user = userOptional.get();
                attendence.setCuisinier(user);
                attendence.setPresence(false);
                attendence.setAttendance(0);
                attendence.setStart(LocalDateTime.now());
                attendence.setEnd(null);
                attendence.setWorkedHours(0.0); // Utiliser 0.0 pour un double
                attendence = attendenceRepository.save(attendence);
                return attendence.getAttendenceId();

        }
        throw new RuntimeException("Utilisateur avec l'ID " + userId + " non trouvé");

    }
 @Override
    public void endAttendance(Long attendenceId) {
        Optional<Attendence> attendanceOptional = attendenceRepository.findById(attendenceId);
        if (attendanceOptional.isPresent()) {
            Attendence attendance = attendanceOptional.get();
            attendance.setEnd(LocalDateTime.now());
            Duration duration = Duration.between(attendance.getStart(), attendance.getEnd());
            double workedHours = duration.toMinutes();
            attendance.setWorkedHours(workedHours);
            attendenceRepository.save(attendance);
        } else {
            throw new RuntimeException("Entrée d'assistance avec l'ID " + attendenceId + " non trouvée");
        }
    }
    @Transactional
    @Scheduled(cron = "* * * 30 * ?")
    public void setAttendence() {
        Double S = 0.0;
        Integer R = 0;
        LocalDate today = LocalDate.now();

        for (Cuisinier c : userRepository.findAll()) {
            boolean bool = false;

            for (Attendence a : c.getAttendences()) {
                LocalDate startDate = a.getStart().toLocalDate();
                if (startDate.isEqual(today)) {
                    S = S + a.getWorkedHours();
                    if (S > 1080) {
                        a.setPresence(true);
                        attendenceRepository.save(a);
                    } else {
                        bool = true;
                    }
                }
            }

            if (bool) {
                boolean hasCoungeToday = c.getCounges().stream()
                        .anyMatch(counge -> today.isAfter(counge.getDateDebut()) && today.isBefore(counge.getDateFin()));
                if (!hasCoungeToday) {
                    if (c.getLastAttendanceUpdate() == null || !c.getLastAttendanceUpdate().isEqual(today)) {
                        R = (c.getAttendance() != null ? c.getAttendance() : 0) + 1;
                        c.setAttendance(R);
                        c.setLastAttendanceUpdate(today);
                        userRepository.save(c);
                    }
                }
            }
        }



    }

    @Override

    public List<Attendence> retrievePresence(Integer cuisinierId) {

        List<Attendence> retrievePresence= attendenceRepository.findAll();
        List<Attendence> newList=new ArrayList<>();
        for (Attendence a:retrievePresence){
            if (a.isPresence()==true){
                newList.add(a);
            }
        }
        return newList;
    }


}
