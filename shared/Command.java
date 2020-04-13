package shared;

public enum Command {
    DOWNLOAD_DOCUMENT("DOWNLOAD_DOCUMENT"), UPLOAD_DOCUMENT("UPLOAD_DOCUMENT"), END_OF_DOCUMENT("END_OF_DOCUMENT"),
    EOF("EOF"), LIST_DOCUMENT("LIST_DOCUMENT"), END_OF_LIST("END_OF_LIST"), NOT_FOUND("NOT_FOUND");

    private String cmd;

    Command(String command) {
        this.cmd = command;
    }

    @Override
    public String toString() {
        return this.cmd;
    }

}