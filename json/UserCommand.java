package json;

public class UserCommand {
    private final String type;
    private final String key;
    private final String value;

    public UserCommand(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
