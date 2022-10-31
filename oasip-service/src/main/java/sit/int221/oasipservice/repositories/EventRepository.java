package sit.int221.oasipservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int221.oasipservice.entities.Event;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findAllByBookingEmailOrderByEventStartTimeDesc(String email);

    @Query("SELECT e FROM Event e WHERE e.eventCategory.id = ?1")
    List<Event> findAllByEventCategory(Integer eventCategory);

    @Query("SELECT e FROM Event e WHERE e.eventCategory.id = ?2 AND e.bookingEmail = ?1")
    List<Event> findAllByEmailAndEventCategory(String email, Integer eventCategory);

    List<Event> findAllByEventStartTimeAfter(Instant eventStartTime);

    List<Event> findAllByBookingEmailAndEventStartTimeAfter(String email, Instant eventStartTime);

    List<Event> findAllByEventStartTimeBefore(Instant eventStartTime);

    List<Event> findAllByBookingEmailAndEventStartTimeBefore(String email, Instant eventStartTime);

    @Query("SELECT e FROM Event e WHERE e.eventStartTime >= ?1 AND e.eventStartTime < ?2")
    List<Event> findAllByDay(Instant dateTimeMidnight, Instant dateTimeMidnightNextDay);

    default List<Event> findAllByDay(Instant dateTimeMidnight) {
        Instant dateTimeMidnightNextDay = dateTimeMidnight.plus(1, ChronoUnit.DAYS);
        return findAllByDay(dateTimeMidnight, dateTimeMidnightNextDay);
    }

    @Query("SELECT e FROM Event e WHERE e.eventStartTime >= ?2 AND e.eventStartTime < ?3 AND e.bookingEmail = ?1")
    List<Event> findAllByEmailAndDay(String email, Instant dateTimeMidnight, Instant dateTimeMidnightNextDay);

    default List<Event> findAllByEmailAndDay(String email, Instant dateTimeMidnight) {
        Instant dateTimeMidnightNextDay = dateTimeMidnight.plus(1, ChronoUnit.DAYS);
        return findAllByEmailAndDay(email, dateTimeMidnight, dateTimeMidnightNextDay);
    }

    @Query(nativeQuery = true,
            value = "SELECT * FROM events e WHERE " +
            "e.eventCategoryId = ?3 AND " +
            "((e.eventStartTime < ?1 AND (e.eventStartTime + INTERVAL e.eventDuration MINUTE) > ?1) OR " +
            "(e.eventStartTime >= ?1 AND e.eventStartTime < ?2))")
    List<Event> findOverlapTimeByEventCategoryId(Instant startTime, Instant endTime, Integer eventCategoryId);

    @Query("SELECT e FROM Event e WHERE e.eventCategory.id = ?1 AND e.eventStartTime >= ?2 AND e.eventStartTime < ?3")
    List<Event> findByEventCategoryIdAndDate(Integer eventCategoryId, Instant dateTimeMidnight, Instant dateTimeMidnightNextDay);


    default List<Event> findByEventCategoryIdAndDate(Integer eventCategoryId, Instant startDateMidNightTime) {
        Instant dateTimeMidnightNextDay = startDateMidNightTime.plus(1, ChronoUnit.DAYS);
        return findByEventCategoryIdAndDate(eventCategoryId, startDateMidNightTime, dateTimeMidnightNextDay);
    }

}