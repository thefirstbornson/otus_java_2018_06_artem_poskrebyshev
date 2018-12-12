package serverMain;

import server.EngineSocketMsgServer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain {
    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());
    private static final String[] CLIENT_START_COMMANDS = new String[]{
            "\\HW16MultiProcAppFront\\target\\HW16.1-MultiProcAppFront-1.0.jar"
           ,"\\HW16MulitProcAppDB\\target\\HW16MulitProcAppDB-1.0.jar"};

    public static void main(String[] args) throws Exception {
     new ServerMain().start();
    }

    private void start() throws Exception {
        Path currentRelativePath = Paths.get("");
        String curDir = currentRelativePath.toAbsolutePath().toString();
        ExecutorService executor = Executors.newFixedThreadPool(CLIENT_START_COMMANDS.length);

        for (String command : CLIENT_START_COMMANDS) {
            executor.execute(() -> {
                try {
                    ProcessBuilder pbDB = new ProcessBuilder("java", "-jar", curDir + command);
                    pbDB.redirectErrorStream(true); // redirect error stream to output stream
                    pbDB.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                    Process proc = pbDB.start();
                    logger.log(Level.SEVERE, "Job running");
                    proc.waitFor();
                    logger.log(Level.SEVERE, "Job finished");
                } catch (IOException | InterruptedException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                }
            });
        }

        EngineSocketMsgServer server = new EngineSocketMsgServer();
        server.start();

    }
}