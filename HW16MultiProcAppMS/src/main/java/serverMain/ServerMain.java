package serverMain;

import server.EngineSocketMsgServer;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        new ServerMain().start();
    }

    private void start() throws Exception {
//        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//        startClient(executorService);

//        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
//        ObjectName name = new ObjectName("ru.otus:type=Server");
        EngineSocketMsgServer server = new EngineSocketMsgServer();
//        mbs.registerMBean(server, name);

        server.start();

//        executorService.shutdown();
    }
}