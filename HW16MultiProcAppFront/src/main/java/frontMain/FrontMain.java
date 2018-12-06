package frontMain;

import fcontext.FrontendContext;
import frontsocket.ClientSocketMsgWorker;
import messagesystem.Address;
import serversocket.SocketMsgWorker;

public class FrontMain {
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "/public_html";
    private static final String HOST = "localhost";
    private static final int SOCKET_PORT = 5050;


    public static void main(String[] args) throws Exception {
        new FrontMain().start();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void start() throws Exception {
        SocketMsgWorker client = new ClientSocketMsgWorker(HOST, SOCKET_PORT);
        client.init();
//
//        Socket addUserFrontend     = new Socket("AddUserFrontend");
//        Socket getUserFrontend     = new Socket("GetUserFrontend");
//        Socket usersCountFrontend  = new Socket("UsersCountFrontend");
        Address clientSocket = new Address("ClientSocket");

        FrontendContext frontendContext = new FrontendContext(clientSocket);
        while (true) {
            if (frontendContext.getDbAdress() != null) {
                System.out.println(frontendContext.getDbAdress());
                break;
            }
        }
        //client.send(new MsgGetDB(frontendContext.getAddress(),frontendContext.getMSAddress()));

//        frontendContext.setFrontAddress(getUserFrontend);
//        frontendContext.setFrontAddress(addUserFrontend);
//        frontendContext.setFrontAddress(usersCountFrontend);
//        frontendContext.setFrontAddress(clientSocket);
//
//        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        //servletContextHandler.addServlet(new ServletHolder(new AddUserWebSocketServlet(mscontext, addUserFrontend)), "/adduser");
//        servletContextHandler.addServlet(new ServletHolder(new GetUserWebSocketServlet(frontendContext, getUserFrontend)), "/getuser");
//       // servletContextHandler.addServlet(new ServletHolder(new UsersCntWebSocketServlet(mscontext, usersCountFrontend)), "/cntusrs");
//
//        ResourceHandler resourceHandler = new ResourceHandler();
//        Resource resource = Resource.newClassPathResource(PUBLIC_HTML);
//        resourceHandler.setBaseResource(resource);
//
//        Server server = new Server(PORT);
//        server.setHandler(new HandlerList(resourceHandler, servletContextHandler));

//        server.start();
//        server.join();
    }

}
