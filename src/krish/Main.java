package krish;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

class Main {
    static List<TimeCard> timeCards = new ArrayList<>();
    static final int POSITIONID = 0, POSITIONSTATUS = 1, POSITIONTIME = 2, TIMEOUT = 3, TIMECARDHOURS = 4, PAYCYCLESTARTDATE = 5, PAYCYCLEENDDATE = 6, EMPLOYEENAMEFIRSTPART = 7, EMPLOYEENAMESECONDPART = 8, FILENUMBER = 9;

    public static void main(String[] args) {
        findAllEmployeeWhoHaveLessThan10HoursOfTimeBetweenShiftsButGreaterThan1Hour(timeCards());
        findAllEmployeesWhoWorkedForSevenConsecutiveDays(timeCards());
        findAllEmployeesWhoWorkedMoreThan14HrsInASingleShift(timeCards());
    }

    private static List<TimeCard> timeCards() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("res/Assignment_Timecard.xlsx - Sheet1.csv"));
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);
                TimeCard timecard = new TimeCard();
                timecard.setPositionID(data[POSITIONID]);
                timecard.setPositionStatus(data[POSITIONSTATUS]);
                timecard.setTime(data[POSITIONTIME]);
                timecard.setTimeOut(data[TIMEOUT]);
                timecard.setTimecardHours(data[TIMECARDHOURS]);
                timecard.setPayCycleStartDate(data[PAYCYCLESTARTDATE]);
                timecard.setPayCycleEndDate(data[PAYCYCLEENDDATE]);
                timecard.setEmployeeName(data[EMPLOYEENAMEFIRSTPART] + "," + data[EMPLOYEENAMESECONDPART]);           //  since the indexes 7 and 8 both are in emploeeName column
                timecard.setFileNumber(data[FILENUMBER]);
                timeCards.add(timecard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeCards;
    }

    private static void findAllEmployeesWhoWorkedForSevenConsecutiveDays(List<TimeCard> timeCards) {

         /*  <---------        assuming
                            1)two shifts per day and
                            2)shift having same date and
                            3)position is positionId                      ---------------->     */

        Map<String, String> employeeNameWithPositionId = new TreeMap<>();
        Map<String, Set<Integer>> employeeNameWithDateList = new TreeMap<>();
        Set<String> employeesWithGivenCondition = new TreeSet<>();
        for (TimeCard timeCard : timeCards) {
            String employeeName = timeCard.getEmployeeName();
            String currentDate = timeCard.getTime();
            String positionId = timeCard.getPositionID();
            if (!currentDate.isEmpty()) {                                                  //   elimination rows with currentDate=""
                int presentDate = Integer.parseInt(currentDate.split("/")[1]);
                Set<Integer> dateList = employeeNameWithDateList.getOrDefault(employeeName, new TreeSet<>());
                dateList.add(presentDate);
                employeeNameWithDateList.put(employeeName, dateList);
                employeeNameWithPositionId.put(positionId, employeeName);
            }
        }
        for (Map.Entry<String, Set<Integer>> entry : employeeNameWithDateList.entrySet()) {
            for (int i : entry.getValue()) {
                if (entry.getValue().contains(i + 1) && entry.getValue().contains(i + 2) && entry.getValue().contains(i + 3) && entry.getValue().contains(i + 4) && entry.getValue().contains(i + 5) && entry.getValue().contains(i + 6) && entry.getValue().contains(i + 7)) {
                    employeesWithGivenCondition.add(entry.getKey());
                }
            }
        }
        System.out.println("Employees Who Worked For Seven Consecutive Days Are :");
        System.out.println();
        for (String employee : employeesWithGivenCondition) {
            for (Map.Entry<String, String> entry : employeeNameWithPositionId.entrySet()) {
                if (entry.getValue().contains(employee)) {
                    System.out.println(entry.getKey() + " --------> " + entry.getValue());
                }
            }
        }
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println();
    }

    private static void findAllEmployeeWhoHaveLessThan10HoursOfTimeBetweenShiftsButGreaterThan1Hour(List<TimeCard> timeCards) {

        /*  <---------        assuming
                            1)two shifts per day and
                            2)shift having same date and
                            3)position is positionId                      ---------------->     */

        Map<String, Map<String, List<Integer>>> employeeWithTimeAndHoursMap = new TreeMap<>();
        Map<String, List<Integer>> timeWithHoursMap = new TreeMap<>();
        Map<String, String> employeeNameWithPositionId = new TreeMap<>();
        Set<String> employeesWithGivenCondition = new TreeSet<>();
        for (TimeCard timeCard : timeCards) {
            String employeeName = timeCard.getEmployeeName();
            String hours = timeCard.getTimecardHours();
            String time = timeCard.getTime();
            String positionId = timeCard.getPositionID();
            if (!time.isEmpty()) {
                employeeNameWithPositionId.put(positionId, employeeName);
                timeWithHoursMap = employeeWithTimeAndHoursMap.getOrDefault(employeeName, new TreeMap<>());
                List<Integer> hoursList = timeWithHoursMap.getOrDefault(time.substring(0, 10), new ArrayList<>());
                String[] parts = hours.split(":");
                int hour = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);
                int totalMinutes = (hour * 60) + minutes;
                hoursList.add(totalMinutes);
                timeWithHoursMap.put(time.substring(0, 10), hoursList);
                employeeWithTimeAndHoursMap.put(employeeName, timeWithHoursMap);
            }
        }
        for (Map.Entry<String, Map<String, List<Integer>>> entry : employeeWithTimeAndHoursMap.entrySet()) {
            for (Map.Entry<String, List<Integer>> e : timeWithHoursMap.entrySet()) {
                if (e.getValue().size() == 2) {
                    if (Math.abs(e.getValue().get(0) - e.getValue().get(1)) >= 60 && Math.abs(e.getValue().get(0) - e.getValue().get(1)) < 600) {
                        employeesWithGivenCondition.add(entry.getKey());
                    }
                }
            }
        }
        System.out.println();
        System.out.println("Employees Who Have Less Than 10 Hours Of Time Between Shifts But Greater Than 1 Hour:");
        System.out.println();
        for (String employee : employeesWithGivenCondition) {
            for (Map.Entry<String, String> entry : employeeNameWithPositionId.entrySet()) {
                if (entry.getValue().contains(employee)) {
                    System.out.println(entry.getKey() + " --------> " + entry.getValue());
                }
            }
        }
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println();
    }


    private static void findAllEmployeesWhoWorkedMoreThan14HrsInASingleShift(List<TimeCard> timeCards) {

         /*  <---------        assuming
                            1)two shifts per day and
                            2)shift having same date and
                            3)position is positionId                      ---------------->     */

        Map<String, String> employeeWithPositionId = new TreeMap<>();
        for (TimeCard timeCard : timeCards) {
            String positionId = timeCard.getPositionID();
            String employeeName = timeCard.getEmployeeName();
            String timecardHours = timeCard.getTimecardHours();
            String[] parts = timecardHours.split(":");
            if (parts.length == 2) {
                try {
                    int hours = Integer.parseInt(parts[0]);
                    int minutes = Integer.parseInt(parts[1]);
                    int totalMinutes = (hours * 60) + minutes;
                    if (totalMinutes > 840) {
                        employeeWithPositionId.put(positionId, employeeName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("employees who worked ore than 14 hours in a single shift are : ");
        System.out.println();
        for (Map.Entry<String, String> entry : employeeWithPositionId.entrySet()) {
            System.out.println(entry.getKey() + " -------------> " + entry.getValue());
        }
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println();
    }
}



