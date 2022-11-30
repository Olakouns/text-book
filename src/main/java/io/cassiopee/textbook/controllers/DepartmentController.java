package io.cassiopee.textbook.controllers;

import io.cassiopee.textbook.entities.Permission;
import io.cassiopee.textbook.entities.ProCalendar;
import io.cassiopee.textbook.entities.ProgrammeCourse;
import io.cassiopee.textbook.entities.SubjectField;
import io.cassiopee.textbook.entities.enums.Semester;
import io.cassiopee.textbook.payload.ApiResponse;
import io.cassiopee.textbook.payload.StatClassRoom;
import io.cassiopee.textbook.payload.StatProgram;
import io.cassiopee.textbook.payload.TimesTables;
import io.cassiopee.textbook.services.DepartmentService;
import io.cassiopee.textbook.services.ResourceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/subjects")
    public SubjectField addSubjectField(@RequestParam  String subjectId, @RequestParam  String semester, @RequestBody SubjectField subjectField) {
        return departmentService.addSubjectField(UUID.fromString(subjectId), Semester.valueOf(semester), subjectField);
    }

    @GetMapping("/subjects")
    public List<SubjectField> getSubjectField() {
        return departmentService.getSubjectField();
    }

    @DeleteMapping("/subjects/{subjectFieldId}")
    public ApiResponse deleteSubjectField(@PathVariable String subjectFieldId) {
        return departmentService.deleteSubjectField(UUID.fromString(subjectFieldId));
    }

    @PostMapping("/programming")
    public ProgrammeCourse makeProgrammeCourse( @RequestParam String profId, @RequestParam String subjectFieldId,
                                            @RequestParam long classRoomId , @RequestBody ProgrammeCourse programmeCourse) {
        return departmentService.makeProgrammeCourse(UUID.fromString(profId), UUID.fromString(subjectFieldId), classRoomId, programmeCourse);
    }

    @GetMapping("/programming")
    public List<ProgrammeCourse> getProgrammeCourses(@RequestParam long classRoomId ) {
        return departmentService.getProgrammeCourses(classRoomId);
    }

    @PutMapping("/programming/{programmeCourseId}")
    public ProgrammeCourse updateProgrammeCourse( @PathVariable String programmeCourseId,  @RequestBody ProgrammeCourse programmeCourse) {
        return departmentService.updateProgrammeCourse(UUID.fromString(programmeCourseId), programmeCourse);
    }

    @PutMapping("/programming/{programmeCourseId}/make-started")
    public ApiResponse makeStarted(@PathVariable String programmeCourseId) {
        return departmentService.makeStarted(UUID.fromString(programmeCourseId));
    }

    @DeleteMapping("/programming/{programmeCourseId}")
    public ApiResponse deleteProgramming( @PathVariable String programmeCourseId) {
        return departmentService.deleteProgramming(UUID.fromString(programmeCourseId));
    }

    @PostMapping("/programming/{programmeCourseId}/pro-calendar")
    public ProCalendar makeProCalendar(@PathVariable String programmeCourseId, @RequestBody ProCalendar proCalendar) {
        return departmentService.makeProCalendar(UUID.fromString(programmeCourseId), proCalendar);
    }

    @GetMapping("/programming/pro-calendars")
    public List<ProCalendar> getAllProCalendarField(@RequestParam  long classRoomId) {
        return departmentService.getAllProCalendarField(classRoomId);
    }

    @GetMapping("/programming/{programmeCourseId}/pro-calendar/field")
    public List<ProCalendar> getProCalendarField(@PathVariable String programmeCourseId, @RequestParam  long classRoomId) {
        return departmentService.getProCalendarField(UUID.fromString(programmeCourseId), classRoomId);
    }

    @GetMapping("/programming/pro-calendar/all-class-room")
    public List<TimesTables> getAllTimesTables() {
        return departmentService.getAllTimesTables();
    }

    @GetMapping("/programming/{programmeCourseId}/stat-program")
    public StatProgram getTimesDone(@PathVariable String programmeCourseId) {
        return departmentService.getTimesDone(UUID.fromString(programmeCourseId));
    }

    @GetMapping("/programming/{classRoomId}/stat-class-room")
    public StatClassRoom getStatClassRoom(@PathVariable long classRoomId) {
        return departmentService.getStatClassRoom(classRoomId);
    }

    @GetMapping("/programming/stat-class-room/all")
    public StatClassRoom getStatClassRoomGeneral() {
        return departmentService.getStatClassRoomGeneral();
    }


}
