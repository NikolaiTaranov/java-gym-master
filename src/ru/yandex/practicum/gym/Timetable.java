package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> getTimetable() {
        return timetable;
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay hours = trainingSession.getTimeOfDay();
        if (!timetable.containsKey(day)) {
            timetable.put(day, new TreeMap<>());
        }

        TreeMap<TimeOfDay, List<TrainingSession>> houtTable = timetable.get(day);
        if (!houtTable.containsKey(hours)) {
            houtTable.put(hours, new ArrayList<>());
        }

        List<TrainingSession> trainings = houtTable.get(hours);
        trainings.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);

        if (daySchedule == null) {
            return new ArrayList<>();
        }

        List<TrainingSession> resultList = daySchedule.get(timeOfDay);
        return resultList;
    }

    public Map<Coach, Integer> getCountByCoaches() {
        Map<Coach, Integer> countForCoach;
        CounterOfTrainings counterOfTrainings = new CounterOfTrainings();
        countForCoach = counterOfTrainings.counter(timetable);

        List<Map.Entry<Coach, Integer>> entries = new ArrayList<>(countForCoach.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<Coach, Integer>>() {
            @Override
            public int compare(Map.Entry<Coach, Integer> o1, Map.Entry<Coach, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        Map<Coach, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Coach, Integer> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}

