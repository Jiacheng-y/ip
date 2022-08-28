package duke;

import duke.exception.ContentNotFoundException;
import duke.exception.DateNotFoundException;
import duke.exception.TaskNotFoundException;
import duke.task.TaskList;
import duke.task.Task;
import duke.tools.CommandParser;
import duke.tools.Storage;
import duke.tools.Ui;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Task Master called Duke to manage those pesky tasks.
 */
public class Duke {
    private TaskList tasks;
    private Storage storage;

    private Duke(String filePath) {
        this.storage = new Storage(filePath);
        this.tasks = storage.load();
    }

    private void run() {
        Ui.greet();

        Scanner userScan = new Scanner(System.in);
        while (userScan.hasNext()) {
            String input = userScan.nextLine();
            CommandParser keywordParser = new CommandParser(input);
            if (input.contentEquals("bye")) {
                Ui.goodbye();
                break;
            } else {
                CommandParser.COMMANDS enumCommand;
                try {
                    enumCommand = keywordParser.getCommand();
                } catch (TaskNotFoundException e) {
                    Ui.noTaskExceptionToast(e);
                    continue;
                }

                switch (enumCommand) {
                    case BYE:
                        Ui.goodbye();
                        break;
                    case LIST:
                        tasks.list();
                        break;
                    case MARK:
                        Task doneTask = tasks.markTask(keywordParser.getTaskNo());
                        storage.saveTasks(tasks);
                        Ui.congrats(doneTask);
                        break;
                    case UNMARK:
                        Task undoneTask = tasks.unmarkTask(keywordParser.getTaskNo());
                        storage.saveTasks(tasks);
                        Ui.undoneToast(undoneTask);
                        break;
                    case DELETE:
                        Task deleted = tasks.deleteTask(keywordParser.getTaskNo());
                        storage.saveTasks(tasks);
                        break;
                    default:
                        try {
                            tasks.addTask(input);
                            storage.saveTasks(tasks);

                        } catch (TaskNotFoundException e) {
                            Ui.noTaskExceptionToast(e);
                        } catch (ContentNotFoundException e) {
                            Ui.noContentExceptionToast(e);
                        } catch (DateNotFoundException e) {
                            Ui.noDateExceptionToast(e);
                        } catch (DateTimeParseException e) {
                            Ui.wrongDateFormatToast(e);
                        } finally {
                            continue;
                        }
                }
            }
        }

    }

    /**
     * Drives Duke class.
     * @param args array of arguments.
     */
    public static void main(String[] args) {
        new Duke("./data/tasks.txt").run();

    }
}
