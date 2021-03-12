package server.commands;

import server.Dao;

public class DeleteCommand implements Command {

    private final int cell;

    public DeleteCommand(Integer cell) {
        this.cell = cell;
    }

    @Override
    public String execute() {
        return Dao.getInstance().delete(cell);
    }
}
