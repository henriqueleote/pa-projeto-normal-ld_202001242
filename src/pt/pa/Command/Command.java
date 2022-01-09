package pt.pa.Command;

public interface Command {
    void execute() throws Exception;
    void unExecute() throws Exception;
}