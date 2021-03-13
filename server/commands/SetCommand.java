package server.commands;

import server.Dao;

public class SetCommand implements Command {

    private final String cell;
    private final String text;

    public SetCommand(String cell, String text) {
        this.cell = cell;
        this.text = text;
    }

    @Override
    public String execute() {
        return Dao.getInstance().set(cell, text);
    }
}
