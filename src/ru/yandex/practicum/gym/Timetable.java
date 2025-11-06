package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> getTimetable() {
        return timetable;
    }
    ///* как это хранить??? */ timetable

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

    public TreeMap<TimeOfDay, List<TrainingSession> >/* непонятно, что возвращать */ getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);

        if (daySchedule == null) {
            return new TreeMap<>();
        }

        TreeMap<TimeOfDay, List<TrainingSession>> sortedMap = new TreeMap<>();

        for (TimeOfDay time : daySchedule.navigableKeySet().tailSet(timeOfDay)) {
            sortedMap.put(time, daySchedule.get(time));
        }
        return sortedMap;
    }

    public HashMap<Coach, Integer> sortCountByCoaches(HashMap<Coach, Integer> unsorted) {
        List<Map.Entry<Coach, Integer>> sortedList = new ArrayList<>(unsorted.entrySet());

        Collections.sort(sortedList, new Comparator<Map.Entry<Coach, Integer>>() {
            @Override
            public int compare(Map.Entry<Coach, Integer> o1, Map.Entry<Coach, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });


        HashMap<Coach, Integer> sorted = new HashMap<>();
        for (Map.Entry<Coach, Integer> entry : sortedList) {
            sorted.put(entry.getKey(), entry.getValue());
        }
        return sorted;
    }

}

