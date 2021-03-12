package server.commands;

import server.Dao;

public class GetCommand implements Command {

    private final int cell;

    public GetCommand(Integer cell) {
        this.cell = cell;
    }

    @Override
    public String execute() {
        return Dao.getInstance().get(cell);
    }
}
