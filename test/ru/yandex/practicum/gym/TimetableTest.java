package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        //Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());

    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
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

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        Assertions.assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());
        Set<TimeOfDay> expectedKeys = Set.of(
                new TimeOfDay(13, 0),
                new TimeOfDay(20, 0)
        );
        Assertions.assertEquals(expectedKeys, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).keySet());
        // Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Group group2 = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach2 = new Coach("Васильев2", "Николай2", "Сергеевич2");
        TrainingSession singleTrainingSession1 = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(singleTrainingSession1);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        List<TrainingSession> testList = new ArrayList<>();
        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        if (sessions != null) {
            testList.addAll(sessions);
        }
        Assertions.assertEquals(2, testList.size());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        List<TrainingSession> testList1 = new ArrayList<>();
        List<TrainingSession> sessions1 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        if (sessions1 != null) {
            testList1.addAll(sessions1);
        }

        Assertions.assertEquals(0, testList1.size());
    }

    @Test
    void testCountOfTrainings() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession1 = new TrainingSession(group1, coach1,
                DayOfWeek.TUESDAY, new TimeOfDay(14, 0));

        Group group3 = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Coach coach3 = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession3 = new TrainingSession(group3, coach3,
                DayOfWeek.FRIDAY, new TimeOfDay(20, 0));

        Group group2 = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach2 = new Coach("Васильев2", "Николай2", "Сергеевич2");
        TrainingSession singleTrainingSession2 = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(singleTrainingSession1);
        timetable.addNewTrainingSession(singleTrainingSession2);
        timetable.addNewTrainingSession(singleTrainingSession3);

        CounterOfTrainings countOf = new CounterOfTrainings();
        HashMap<Coach, Integer> test = countOf.counter(timetable);

        Assertions.assertEquals(3, test.get(new Coach("Васильев", "Николай", "Сергеевич")));
        Assertions.assertEquals(1, test.get(new Coach("Васильев2", "Николай2", "Сергеевич2")));
    }

    @Test
    void testGetCountOfTrainings() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession1 = new TrainingSession(group1, coach1,
                DayOfWeek.TUESDAY, new TimeOfDay(14, 0));

        Group group3 = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Coach coach3 = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession3 = new TrainingSession(group3, coach3,
                DayOfWeek.FRIDAY, new TimeOfDay(20, 0));

        Group group2 = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach2 = new Coach("Васильев2", "Николай2", "Сергеевич2");
        TrainingSession singleTrainingSession2 = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(singleTrainingSession1);
        timetable.addNewTrainingSession(singleTrainingSession2);
        timetable.addNewTrainingSession(singleTrainingSession3);

        CounterOfTrainings countOf = new CounterOfTrainings();
        HashMap<Coach, Integer> test = countOf.counter(timetable);
        timetable.getCountByCoaches(test);

        ArrayList<Integer> expectedResult = new ArrayList();
        expectedResult.add(0,3);
        expectedResult.add(1,1);

        boolean isDescending = true;

        ArrayList<Integer> values = new ArrayList<>(test.values());
        for (int i = 0; i < values.size() - 1; i++) {
            if (values.get(i) < values.get(i + 1)) {
                isDescending = false;
                break;
            }
        }

        Assertions.assertTrue(isDescending, "Порядок значений не является убывающим: " + values);
        Assertions.assertEquals(3, test.get(coach));
        Assertions.assertEquals(1, test.get(coach2));
    }

}

