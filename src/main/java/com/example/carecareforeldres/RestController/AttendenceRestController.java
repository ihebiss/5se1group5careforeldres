package com.example.carecareforeldres.RestController;


import com.example.carecareforeldres.Entity.Attendence;
import com.example.carecareforeldres.Entity.Cuisinier;
import com.example.carecareforeldres.Repository.CuisinierRepository;
import com.example.carecareforeldres.Service.IAttendenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/attendance") // Change endpoint to singular form
public class AttendenceRestController {
    private final IAttendenceService attendenceService;
    private final CuisinierRepository cuisinierRepository;
    public AttendenceRestController(IAttendenceService attendenceService, CuisinierRepository cuisinierRepository) {
        this.attendenceService = attendenceService;
        this.cuisinierRepository = cuisinierRepository;
    }

    @PostMapping("/start")
    public ResponseEntity<Long> startAttendance(@RequestParam Integer userId) {
        Long attendanceId = attendenceService.startAttendance(userId);
        return ResponseEntity.ok(attendanceId);
    }

    @PostMapping("/end/{attendanceId}")
    public ResponseEntity<Void> endAttendance(@PathVariable Long attendanceId) {
        attendenceService.endAttendance(attendanceId);
        return ResponseEntity.ok().build(); // Indiquer le succès de la fin de l'assiduité
    }

    @GetMapping("/all") // Endpoint to retrieve all attendance records
    public List<Attendence> retrieveAllAttendence() {
        return attendenceService.retrieveAllAttendence();
    }

//        @PostMapping("/add") // Endpoint to add attendance
//        public Attendence addAttendence(@RequestBody Attendence attendance) {
//            attendance.setDate(LocalDateTime.now());
//            return attendenceService.addAttendence(attendance);
//        }
    //update pasing id user to add :
@PostMapping("/add/{userId}") // Endpoint to add attendance with user ID in the URL
public Attendence addAttendance(@PathVariable Integer userId, @RequestBody Attendence attendance) {
    attendance.setStart(LocalDateTime.now());
    attendance.setEnd(LocalDateTime.now());
    return attendenceService.addAttendence(userId, attendance);
}


    @PutMapping("/update/{id}") // Endpoint to update attendance (if needed)
    public void updateAttendence(@PathVariable("id") Long id, @RequestBody Attendence attendance) {
        // You can implement this method if you need to update attendance
        // Note: You may need to handle security and validation checks here
         attendenceService.endAttendance(id);
    }
    @GetMapping("/get/{id}") // Endpoint to retrieve a specific attendance record by ID (if needed)
    public Attendence retrieveAttendence(@PathVariable("id") Long id) {
        return attendenceService.retrieveAttendence(id);
    }


    @DeleteMapping("/remove/{id}") // Endpoint to remove attendance by ID (if needed)
    public void removeAttendence(@PathVariable("id") Long id) {
        attendenceService.removeAttendence(id);
    }


    @GetMapping("/getPresenceCuisinier/{id}")
    public List<Attendence> retrievePresence(@PathVariable("id") Integer id) {
        return attendenceService.retrievePresence(id);
    }
    @GetMapping("/getAllCuisinier")
    public List<Cuisinier> getAllCuisinier() {
        return cuisinierRepository.findAll();
    }
}
