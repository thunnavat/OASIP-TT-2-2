package sit.int221.oasipservice.services;

import io.jsonwebtoken.impl.DefaultClaims;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.oasipservice.config.JwtTokenUtil;
import sit.int221.oasipservice.dtos.CreateEventDTO;
import sit.int221.oasipservice.dtos.UpdateEventDTO;
import sit.int221.oasipservice.entities.Event;
import sit.int221.oasipservice.entities.EventCategory;
import sit.int221.oasipservice.entities.EventCategoryOwner;
import sit.int221.oasipservice.entities.User;
import sit.int221.oasipservice.repositories.EventCategoryOwnerRepository;
import sit.int221.oasipservice.repositories.EventRepository;
import sit.int221.oasipservice.repositories.EventCategoryRepository;
import sit.int221.oasipservice.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventCategoryRepository eventCategoryRepository;
    private final UserRepository userRepository;
    private final EventCategoryOwnerRepository eventCategoryOwnerRepository;
    private final ModelMapper modelMapper;
    private final JwtTokenUtil jwtTokenUtil;

    public List<Event> getEvents(HttpServletRequest request) {
        String jwtToken = getTokenFromHeader(request);
        String role = jwtTokenUtil.getRoleFromToken(jwtToken).get(0).toString();
        String email = jwtTokenUtil.getEmailFromToken(jwtToken);

        if (role.equals("STUDENT")) {
            return eventRepository.findAllByBookingEmailOrderByEventStartTimeDesc(email);
        } else if (role.equals("LECTURER")) {
            User user = userRepository.findByEmailIgnoreCase(email);
            List<EventCategoryOwner> eventCategoryOwner = eventCategoryOwnerRepository.findAllByUser(user);
            List<Event> events = new ArrayList<>();
            if (eventCategoryOwner != null) {
                eventCategoryOwner.forEach(e -> events.addAll(e.getEventCategory().getEvents()));
            }
            return events;
        }

        return eventRepository.findAll(Sort.by(Sort.Direction.DESC, "eventStartTime"));
    }

    public Event getEventById(HttpServletRequest request, Integer eventId) {
        String jwtToken = getTokenFromHeader(request);
        String role = jwtTokenUtil.getRoleFromToken(jwtToken).get(0).toString();
        String email = jwtTokenUtil.getEmailFromToken(jwtToken);

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event id " + eventId + "Does not exist"));

        if (role.equals("STUDENT")) {
            if (!email.equals(event.getBookingEmail())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }

        return  event;
    }

    public List<Event> getEventsByDay(HttpServletRequest request, Instant dateTimeMidnight) {
        String jwtToken = getTokenFromHeader(request);
        String role = jwtTokenUtil.getRoleFromToken(jwtToken).get(0).toString();
        String email = jwtTokenUtil.getEmailFromToken(jwtToken);

        if (role.equals("STUDENT")) {
            return eventRepository.findAllByEmailAndDay(email, dateTimeMidnight);
        }

        List<Event> events = eventRepository.findAllByDay(dateTimeMidnight);

        return events;
    }

    public List<Event> getEventsByCategory(HttpServletRequest request, Integer category) {
        String jwtToken = getTokenFromHeader(request);
        String role = jwtTokenUtil.getRoleFromToken(jwtToken).get(0).toString();
        String email = jwtTokenUtil.getEmailFromToken(jwtToken);

        if (role.equals("STUDENT")) {
            return eventRepository.findAllByEmailAndEventCategory(email, category);
        }

        List<Event> events = eventRepository.findAllByEventCategory(category);
        return events;
    }

    public List<Event> getEventsByUpcomingTime(HttpServletRequest request, Instant eventStartTime) {
        String jwtToken = getTokenFromHeader(request);
        String role = jwtTokenUtil.getRoleFromToken(jwtToken).get(0).toString();
        String email = jwtTokenUtil.getEmailFromToken(jwtToken);

        if (role.equals("STUDENT")) {
            return eventRepository.findAllByBookingEmailAndEventStartTimeAfter(email, eventStartTime);
        }

        List<Event> events = eventRepository.findAllByEventStartTimeAfter(eventStartTime);
        return events;
    }

    public List<Event> getEventsByPastTime(HttpServletRequest request, Instant eventStartTime) {
        String jwtToken = getTokenFromHeader(request);
        String role = jwtTokenUtil.getRoleFromToken(jwtToken).get(0).toString();
        String email = jwtTokenUtil.getEmailFromToken(jwtToken);

        if (role.equals("STUDENT")) {
            return eventRepository.findAllByBookingEmailAndEventStartTimeBefore(email, eventStartTime);
        }

        List<Event> events = eventRepository.findAllByEventStartTimeBefore(eventStartTime);
        return events;
    }

    public List<Event> getEventsByCategoryAndDate(Integer eventCategoryId, Instant startDateMidNightTime) {
        List<Event> event = eventRepository.findByEventCategoryIdAndDate(eventCategoryId, startDateMidNightTime);
        return event;
    }

    public Event create(HttpServletRequest request, CreateEventDTO newEvent) {
        String jwtToken = getTokenFromHeader(request);
        String role = jwtTokenUtil.getRoleFromToken(jwtToken).get(0).toString();
        String email = jwtTokenUtil.getEmailFromToken(jwtToken);

        if (role.equals("STUDENT")) {
            if (!email.equals(newEvent.getBookingEmail())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking email must be the same as the student's email");
            }
        }

        EventCategory eventCategory = eventCategoryRepository.findById(newEvent.getEventCategoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Eventcategory" + newEvent.getEventCategoryId() + "id Does not exist"));

        Instant endTime = newEvent.getEventStartTime().plusSeconds(eventCategory.getEventDuration() * 60);
        List<Event> eventsOverlap = eventRepository.findOverlapTimeByEventCategoryId(newEvent.getEventStartTime(), endTime, eventCategory.getId());
        if (eventsOverlap.size() != 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time is overlap");

        Event event = modelMapper.map(newEvent, Event.class);
        event.setId(null);
        event.setEventCategory(eventCategory);
        event.setEventDuration(eventCategory.getEventDuration());

        return eventRepository.saveAndFlush(event);
    }

    public void delete(HttpServletRequest request, Integer eventId) {
        String jwtToken = getTokenFromHeader(request);
        String role = jwtTokenUtil.getRoleFromToken(jwtToken).get(0).toString();
        String email = jwtTokenUtil.getEmailFromToken(jwtToken);

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event id " + eventId + "Does not exist"));

        if (role.equals("STUDENT")) {
            if (!email.equals(event.getBookingEmail())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }

        eventRepository.deleteById(eventId);
    }

    public Event update(HttpServletRequest request, UpdateEventDTO updateEvent, Integer eventId) {
        String jwtToken = getTokenFromHeader(request);
        String role = jwtTokenUtil.getRoleFromToken(jwtToken).get(0).toString();
        String email = jwtTokenUtil.getEmailFromToken(jwtToken);

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event id " + eventId + "Does not exist"));

        if (role.equals("STUDENT")) {
            if (!email.equals(event.getBookingEmail())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }

        EventCategory eventCategory = eventCategoryRepository.findById(event.getEventCategory().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Eventcategory" + event.getEventCategory().getId() + "id Does not exist"));

        Instant endTime = updateEvent.getEventStartTime().plusSeconds(eventCategory.getEventDuration() * 60);
        List<Event> eventsOverlap = eventRepository.findOverlapTimeByEventCategoryId(updateEvent.getEventStartTime(), endTime, eventCategory.getId());
        if (eventsOverlap.size() != 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "overlap");
        
        event.setEventStartTime(updateEvent.getEventStartTime());
        event.setEventNotes(updateEvent.getEventNotes());
        return eventRepository.saveAndFlush(event);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        String jwtToken = null;
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            jwtToken = bearerToken.substring(7);
        }
        return jwtToken;
    }
}
