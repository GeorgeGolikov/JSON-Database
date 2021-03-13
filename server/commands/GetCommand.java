package server.commands;

import server.Dao;

public class GetCommand implements Command {

    private final String cell;

    public GetCommand(String cell) {
        this.cell = cell;
    }

    @Override
    public String execute() {
        return Dao.getInstance().get(cell);
    }
}
