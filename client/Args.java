package client;

import com.beust.jcommander.Parameter;

public class Args {
    @Parameter(names = "-t", description = "Type of request")
    private String request;

    @Parameter(names = "-k", description = "The index of the cell")
    private String cell;

    @Parameter(names = "-v", description = "The value to save in the database")
    private String text = "";

    public String getRequest() {
        return request;
    }

    public String getCell() {
        return cell;
    }

    public String getText() {
        return text;
    }
}
