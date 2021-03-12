package client;

import com.beust.jcommander.Parameter;

public class Args {
    @Parameter(names = "-t", description = "Type of request")
    private String request;

    @Parameter(names = "-i", description = "The index of the cell")
    private Integer cell;

    @Parameter(names = "-m", description = "The value to save in the database")
    private String text;

    public String getRequest() {
        return request;
    }

    public Integer getCell() {
        return cell;
    }

    public String getText() {
        return text;
    }
}
