package serverMain;

import server.EchoSocketMsgServer;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        new ServerMain().start();
    }

    private void start() throws Exception {
//        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//        startClient(executorService);

//        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
//        ObjectName name = new ObjectName("ru.otus:type=Server");
        EchoSocketMsgServer server = new EchoSocketMsgServer();
//        mbs.registerMBean(server, name);

        server.start();

//        executorService.shutdown();
    }
}