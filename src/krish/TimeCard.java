package krish;

public class TimeCard {
    private String PositionID;
    private String PositionStatus;
    private String Time;
    private String TimeOut;
    private String TimecardHours;
    private String PayCycleStartDate;
    private String PayCycleEndDate;
    private String EmployeeName;
    private String FileNumber;

    public String getPositionID() {
        return PositionID;
    }

    public void setPositionID(String positionID) {
        PositionID = positionID;
    }

    public String getPositionStatus() {
        return PositionStatus;
    }

    public void setPositionStatus(String positionStatus) {
        PositionStatus = positionStatus;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTimeOut() {
        return TimeOut;
    }

    public void setTimeOut(String timeOut) {
        TimeOut = timeOut;
    }

    public String getTimecardHours() {
        return TimecardHours;
    }

    public void setTimecardHours(String timecardHours) {
        TimecardHours = timecardHours;
    }

    public String getPayCycleStartDate() {
        return PayCycleStartDate;
    }

    public void setPayCycleStartDate(String payCycleStartDate) {
        PayCycleStartDate = payCycleStartDate;
    }

    public String getPayCycleEndDate() {
        return PayCycleEndDate;
    }

    public void setPayCycleEndDate(String payCycleEndDate) {
        PayCycleEndDate = payCycleEndDate;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getFileNumber() {
        return FileNumber;
    }

    public void setFileNumber(String fileNumber) {
        FileNumber = fileNumber;
    }
}
