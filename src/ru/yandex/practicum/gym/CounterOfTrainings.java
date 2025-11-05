package ru.yandex.practicum.gym;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class CounterOfTrainings {
    public int i;
    HashMap<Coach, Integer> countOfTrainings = new HashMap<>();

    public HashMap<Coach, Integer> counter(Timetable timetable) {
        i = 1;
        for (TreeMap<TimeOfDay, List<TrainingSession>> day: timetable.getTimetable().values()) {
            for (List<TrainingSession> time: day.values()) {
                for (TrainingSession trainig: time) {
                    if (!countOfTrainings.containsKey(trainig.getCoach())) {
                        countOfTrainings.put(trainig.getCoach(), i);
                    } else {
                        countOfTrainings.put(trainig.getCoach(), (countOfTrainings.get(trainig.getCoach())) + 1);
                    }
                }
            }
        }
        return countOfTrainings;
    }
}