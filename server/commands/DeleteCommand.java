package server.commands;

import server.Dao;

public class DeleteCommand implements Command {

    private final String cell;

    public DeleteCommand(String cell) {
        this.cell = cell;
    }

    @Override
    public String execute() {
        return Dao.getInstance().delete(cell);
    }
}
