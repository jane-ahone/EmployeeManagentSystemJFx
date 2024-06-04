package dashboard;

import java.util.Date;

public class employeeAttendanceData {
    private Date date;
    private Date time;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public employeeAttendanceData(Date date, Date time) {
        this.date = date;
        this.time = time;
    }
}
