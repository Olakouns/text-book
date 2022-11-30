package io.cassiopee.textbook.controllers;

import io.cassiopee.textbook.entities.ProCalendar;
import io.cassiopee.textbook.entities.ProgrammeCourse;
import io.cassiopee.textbook.entities.Seance;
import io.cassiopee.textbook.entities.SubjectField;
import io.cassiopee.textbook.payload.ApiResponse;
import io.cassiopee.textbook.payload.TeacherOperator;
import io.cassiopee.textbook.services.HeadClassService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/head-class")
public class HeadClassController {

    private final HeadClassService headClassService;

    public HeadClassController(HeadClassService headClassService) {
        this.headClassService = headClassService;
    }

    @PostMapping("/seances")
    public Seance addSeance(@RequestParam String subjectFieldId, @RequestBody Seance seance){
        return  headClassService.addSeance(UUID.fromString(subjectFieldId), seance);
    }

    @PutMapping("/seances/{seanceId}")
    public Seance updateSeance(@PathVariable String seanceId, @RequestBody Seance seance){
        return  headClassService.updateSeance(UUID.fromString(seanceId), seance);
    }

    @PutMapping("/seances/{seanceId}/validate")
    public ApiResponse validateSeance(@PathVariable String seanceId){
        return  headClassService.validateSeance(UUID.fromString(seanceId));
    }

    @DeleteMapping("/seances/{seanceId}")
    public ApiResponse deleteResponse(@PathVariable String seanceId){
        return  headClassService.deleteResponse(UUID.fromString(seanceId));
    }

    @GetMapping("/seances/by-subject-field")
    public Page<Seance> getSeance(@RequestParam String subjectFieldId,
                                  @RequestParam(defaultValue = "") String search,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "0") long startedDate,
                                  @RequestParam(defaultValue = "0") long endedDate){

        return  headClassService.getSeance(UUID.fromString(subjectFieldId), search, page, size, startedDate, endedDate);
    }

    @GetMapping("/subject-field")
    public List<ProgrammeCourse> getSubjectFieldProgram(){
        return  headClassService.getSubjectFieldProgram();
    }

    @GetMapping("/pro-calendars")
    public List<ProCalendar> getMyCalendar(){
        return  headClassService.getMyCalendar();
    }

    @GetMapping("/teacher-operators")
    public List<TeacherOperator> getTeacherOperator(){
        return  headClassService.getTeacherOperator();
    }

}
