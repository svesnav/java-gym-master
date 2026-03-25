package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        this.timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        // Получаем или создаём TreeMap для дня недели
        TreeMap<TimeOfDay, List<TrainingSession>> daySessions = timetable.computeIfAbsent(day, k -> new TreeMap<>());

        // Получаем или создаём список для времени
        List<TrainingSession> sessions = daySessions.computeIfAbsent(time, k -> new ArrayList<>());

        sessions.add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> daySessions = timetable.get(dayOfWeek);
        if (daySessions == null) {
            return new ArrayList<>();
        }

        List<TrainingSession> result = new ArrayList<>();
        for (List<TrainingSession> sessions : daySessions.values()) {
            result.addAll(sessions);
        }
        return result;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> daySessions = timetable.get(dayOfWeek);
        if (daySessions == null) {
            return new ArrayList<>();
        }

        List<TrainingSession> sessions = daySessions.get(timeOfDay);
        if (sessions == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(sessions);
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> coachCounts = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> daySessions : timetable.values()) {
            for (List<TrainingSession> sessions : daySessions.values()) {
                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();
                    Integer count = coachCounts.get(coach);
                    if (count == null) {
                        count = 0;
                    }
                    coachCounts.put(coach, count + 1);
                }
            }
        }

        List<CounterOfTrainings> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachCounts.entrySet()) {
            result.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        // Сортировка по убыванию количества тренировок
        result.sort((a, b) -> Integer.compare(b.getCount(), a.getCount()));

        return result;
    }


}