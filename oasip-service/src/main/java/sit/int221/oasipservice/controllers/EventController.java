package sit.int221.oasipservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sit.int221.oasipservice.dtos.CreateEventDTO;
import sit.int221.oasipservice.dtos.UpdateEventDTO;
import sit.int221.oasipservice.entities.Event;
import sit.int221.oasipservice.services.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<Event> getEvents(HttpServletRequest request) { return service.getEvents(request); }

    @GetMapping("/{id}")
    public Event getEventById(HttpServletRequest request, @PathVariable Integer id) {
        return service.getEventById(request, id);
    }

    @GetMapping("/day")
    public List<Event> getEventsByDay (HttpServletRequest request, @RequestParam Instant dateTimeMidnight) {
        return service.getEventsByDay(request, dateTimeMidnight);
    }

    @GetMapping("category/{categoryId}")
    public List<Event> getEventsByCategory (HttpServletRequest request, @PathVariable Integer categoryId) {
        return service.getEventsByCategory(request, categoryId);
    }

    @GetMapping("/upcoming")
    public List<Event> getEventsByUpcomingTime (HttpServletRequest request, @RequestParam Instant eventStartTime) {
        return service.getEventsByUpcomingTime(request, eventStartTime);
    }

    @GetMapping("/past")
    public List<Event> getEventsByPastTime (HttpServletRequest request, @RequestParam Instant eventStartTime) {
        return service.getEventsByPastTime(request, eventStartTime);
    }

    @GetMapping("/categoryDate")
    public List<Event> getEventsByCategoryAndDate(@RequestParam Integer eventCategoryId, @RequestParam Instant startDateMidNightTime) {
        return service.getEventsByCategoryAndDate(eventCategoryId, startDateMidNightTime);
    }

    @PostMapping(value = "")
    @PreAuthorize("!hasAuthority('LECTURER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Event create(HttpServletRequest request, @Valid CreateEventDTO newEvent) throws IOException {
        return service.create(request, newEvent);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("!hasAuthority('LECTURER')")
    public void delete(HttpServletRequest request, @PathVariable Integer id) {
        service.delete(request, id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("!hasAuthority('LECTURER')")
    public Event update(HttpServletRequest request, @Valid @RequestBody UpdateEventDTO updateEvent, @PathVariable Integer id) {
        return service.update(request, updateEvent, id);
    }

}

