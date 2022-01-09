package pt.pa.Command;

import java.util.Stack;

public class CommandManager {
    private Stack<Command> commands;

    public CommandManager() {
        commands = new Stack();
    }

    public void executeCommand(Command command) throws Exception {
        command.execute();
        commands.push(command);
    }

    public void undo() throws Exception {
        if (commands.empty())
            throw new Exception("dsadsa");
        Command cmd = commands.pop();
        cmd.unExecute();
    }
}