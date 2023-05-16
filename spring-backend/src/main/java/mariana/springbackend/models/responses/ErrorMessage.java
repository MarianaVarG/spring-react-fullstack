package mariana.springbackend.models.responses;

import java.util.Date;

public class ErrorMessage {
    private Date timeStamp;

    private String massage;

    public ErrorMessage() {}

    public ErrorMessage(Date timeStamp, String massage) {
        this.timeStamp = timeStamp;
        this.massage = massage;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
