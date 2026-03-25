package ru.yandex.practicum.gym;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TimetableTest {

    @Test
    public void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assert.assertEquals("За понедельник должно вернуться одно занятие",
                1, mondaySessions.size());
        Assert.assertEquals(singleTrainingSession, mondaySessions.get(0));

        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assert.assertEquals("За вторник не должно вернуться занятий",
                0, tuesdaySessions.size());
    }

    @Test
    public void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assert.assertEquals("За понедельник должно вернуться одно занятие",
                1, mondaySessions.size());

        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assert.assertEquals("За четверг должно вернуться два занятия",
                2, thursdaySessions.size());
        Assert.assertEquals("Первое занятие в четверг должно быть в 13:00",
                new TimeOfDay(13, 0), thursdaySessions.get(0).getTimeOfDay());
        Assert.assertEquals("Второе занятие в четверг должно быть в 20:00",
                new TimeOfDay(20, 0), thursdaySessions.get(1).getTimeOfDay());

        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assert.assertEquals("За вторник не должно вернуться занятий",
                0, tuesdaySessions.size());
    }

    @Test
    public void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> sessionsAt13 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        Assert.assertEquals("За понедельник в 13:00 должно вернуться одно занятие",
                1, sessionsAt13.size());

        List<TrainingSession> sessionsAt14 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Assert.assertEquals("За понедельник в 14:00 не должно вернуться занятий",
                0, sessionsAt14.size());
    }

    @Test
    public void testGetTrainingSessionsForDayAndTimeMultipleSessionsAtSameTime() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Измайлов", "Константин", "Викторович");

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Гимнастика для детей", Age.CHILD, 45);

        TrainingSession session1 = new TrainingSession(group1, coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));
        TrainingSession session2 = new TrainingSession(group2, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));
        Assert.assertEquals("В одно время может быть несколько занятий",
                2, sessions.size());
        Assert.assertTrue(sessions.contains(session1));
        Assert.assertTrue(sessions.contains(session2));
    }

    @Test
    public void testGetTrainingSessionsForDayEmptyTimetable() {
        Timetable timetable = new Timetable();

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY);
        Assert.assertEquals("Для пустого расписания должен вернуться пустой список",
                0, sessions.size());
    }

    @Test
    public void testGetTrainingSessionsForDayOrdering() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика", Age.ADULT, 60);

        TrainingSession session1 = new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(18, 0));
        TrainingSession session2 = new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 0));
        TrainingSession session3 = new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(14, 30));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY);
        Assert.assertEquals(3, sessions.size());
        Assert.assertEquals("Занятия должны быть отсортированы по времени",
                new TimeOfDay(10, 0), sessions.get(0).getTimeOfDay());
        Assert.assertEquals(new TimeOfDay(14, 30), sessions.get(1).getTimeOfDay());
        Assert.assertEquals(new TimeOfDay(18, 0), sessions.get(2).getTimeOfDay());
    }

    @Test
    public void testGetCountByCoachesSingleCoach() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика", Age.ADULT, 60);

        for (DayOfWeek day : DayOfWeek.values()) {
            TrainingSession session = new TrainingSession(group, coach, day, new TimeOfDay(10, 0));
            timetable.addNewTrainingSession(session);
        }

        List<CounterOfTrainings> counts = timetable.getCountByCoaches();
        Assert.assertEquals(1, counts.size());
        Assert.assertEquals(coach, counts.get(0).getCoach());
        Assert.assertEquals("Тренер проводит 7 занятий в неделю",
                7, counts.get(0).getCount());
    }

    @Test
    public void testGetCountByCoachesMultipleCoachesSorted() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Измайлов", "Константин", "Викторович");
        Coach coach3 = new Coach("Семёнов", "Владимир", "Кириллович");

        Group group = new Group("Акробатика", Age.ADULT, 60);

        for (int i = 0; i < 5; i++) {
            TrainingSession session = new TrainingSession(group, coach1,
                    DayOfWeek.values()[i], new TimeOfDay(10, 0));
            timetable.addNewTrainingSession(session);
        }

        for (int i = 0; i < 3; i++) {
            TrainingSession session = new TrainingSession(group, coach2,
                    DayOfWeek.values()[i], new TimeOfDay(12, 0));
            timetable.addNewTrainingSession(session);
        }

        for (int i = 0; i < 1; i++) {
            TrainingSession session = new TrainingSession(group, coach3,
                    DayOfWeek.values()[i], new TimeOfDay(14, 0));
            timetable.addNewTrainingSession(session);
        }

        List<CounterOfTrainings> counts = timetable.getCountByCoaches();
        Assert.assertEquals(3, counts.size());
        Assert.assertEquals(coach1, counts.get(0).getCoach());
        Assert.assertEquals(5, counts.get(0).getCount());
        Assert.assertEquals(coach2, counts.get(1).getCoach());
        Assert.assertEquals(3, counts.get(1).getCount());
        Assert.assertEquals(coach3, counts.get(2).getCoach());
        Assert.assertEquals(1, counts.get(2).getCount());
    }

    @Test
    public void testGetCountByCoachesEmptyTimetable() {
        Timetable timetable = new Timetable();

        List<CounterOfTrainings> counts = timetable.getCountByCoaches();
        Assert.assertEquals("Для пустого расписания должен вернуться пустой список",
                0, counts.size());
    }

    @Test
    public void testGetCountByCoachesSameCoachDifferentGroups() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 90);

        TrainingSession session1 = new TrainingSession(group1, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession session2 = new TrainingSession(group2, coach,
                DayOfWeek.MONDAY, new TimeOfDay(12, 0));
        TrainingSession session3 = new TrainingSession(group1, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);

        List<CounterOfTrainings> counts = timetable.getCountByCoaches();
        Assert.assertEquals(1, counts.size());
        Assert.assertEquals(coach, counts.get(0).getCoach());
        Assert.assertEquals("Все занятия тренера должны быть посчитаны",
                3, counts.get(0).getCount());
    }

    @Test
    public void testGetTrainingSessionsForDayAndTimeNonExistentDay() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика", Age.ADULT, 60);
        TrainingSession session = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        timetable.addNewTrainingSession(session);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.SUNDAY, new TimeOfDay(10, 0));
        Assert.assertEquals("Для дня без занятий должен вернуться пустой список",
                0, sessions.size());
    }

    @Test
    public void testAddMultipleSessionsSameTimeSameDay() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Измайлов", "Константин", "Викторович");
        Coach coach3 = new Coach("Семёнов", "Владимир", "Кириллович");

        Group group = new Group("Акробатика", Age.ADULT, 60);

        TrainingSession session1 = new TrainingSession(group, coach1,
                DayOfWeek.TUESDAY, new TimeOfDay(19, 0));
        TrainingSession session2 = new TrainingSession(group, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(19, 0));
        TrainingSession session3 = new TrainingSession(group, coach3,
                DayOfWeek.TUESDAY, new TimeOfDay(19, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.TUESDAY, new TimeOfDay(19, 0));
        Assert.assertEquals("В одно время может быть 3 занятия",
                3, sessions.size());
    }
}