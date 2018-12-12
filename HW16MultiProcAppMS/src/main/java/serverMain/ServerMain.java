package serverMain;

import runner.ProcessRunnerImpl;
import server.EngineSocketMsgServer;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain {
    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());
    private static final String CLIENT_START_COMMAND =
            "java -jar ../HW16MultiProcAppFront/target/HW16.1-MultiProcAppFront-1.0.jar";
    private static final int CLIENT_START_DELAY_SEC = 5;

    public static void main(String[] args) throws Exception {
        new ServerMain().start();
    }

    private void start() throws Exception {
//        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//        startClient(executorService);
//
        ProcessBuilder pbDB = new ProcessBuilder("java", "-jar"
                , "c:\\Users\\User\\IdeaProjects\\otus_java_2018_06_artem_poskrebyshev\\HW16MulitProcAppDB\\target\\HW16MulitProcAppDB-1.0.jar");
//        pbDB.redirectErrorStream(true); // redirect error stream to output stream
//        pbDB.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        Process proc = pbDB.start();
//        System.out.println( "Job running" );
//        proc.waitFor();
//        System.out.println( "Job finished" );


        ProcessBuilder pbf = new ProcessBuilder("java", "-jar"
                , "c:\\Users\\User\\IdeaProjects\\otus_java_2018_06_artem_poskrebyshev\\HW16MultiProcAppFront\\target\\HW16.1-MultiProcAppFront-1.0.jar");
//        pbf.redirectErrorStream(true); // redirect error stream to output stream
//        pbf.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        Process proc2 = pbf.start();
//        System.out.println( "Job running" );
//        proc2.waitFor();
//        System.out.println( "Job finished" );



//        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
//        ObjectName name = new ObjectName("ru.otus:type=Server");
        EngineSocketMsgServer server = new EngineSocketMsgServer();
//        mbs.registerMBean(server, name);

        server.start();

//        executorService.shutdown();
    }

    private void startClient(ScheduledExecutorService executorService) {
        executorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(CLIENT_START_COMMAND);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
    }
}