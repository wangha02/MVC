package rikkei.academy.dto.response;

public class ResponseMessenger {
    private String message;

    public ResponseMessenger() {
    }

    public ResponseMessenger(String messenger) {
        this.message = messenger;
    }

    public String getMessenger() {
        return message;
    }

    public void setMessenger(String messenger) {
        this.message = messenger;
    }
}
