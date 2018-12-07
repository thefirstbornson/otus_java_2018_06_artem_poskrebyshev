package frontMain;

import org.eclipse.jetty.server.Server;

public class FrontMain {
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "/public_html";



    public static void main(String[] args) throws Exception {
        new FrontMain().start();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void start() throws Exception {

//
//        Socket addUserFrontend     = new Socket("AddUserFrontend");
//        Socket getUserFrontend     = new Socket("GetUserFrontend");
//        Socket usersCountFrontend  = new Socket("UsersCountFrontend");
//        Address clientSocket = new Address("ClientSocket");

//        FrontendContext frontendContext = new FrontendContext(clientSocket);

        //client.send();

//        frontendContext.setFrontAddress(getUserFrontend);
//        frontendContext.setFrontAddress(addUserFrontend);
//        frontendContext.setFrontAddress(usersCountFrontend);
//        frontendContext.setFrontAddress(clientSocket);
//
//        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        //servletContextHandler.addServlet(new ServletHolder(new AddUserWebSocketServlet(mscontext, addUserFrontend)), "/adduser");
//        servletContextHandler.addServlet(new ServletHolder(new GetUserWebSocketServlet(frontendContext, getUserFrontend)), "/getuser");
       // servletContextHandler.addServlet(new ServletHolder(new GetUserWebSocketServlet(frontendContext)), "/getuser");
       // servletContextHandler.addServlet(new ServletHolder(new UsersCntWebSocketServlet(mscontext, usersCountFrontend)), "/cntusrs");

//        ResourceHandler resourceHandler = new ResourceHandler();
//        Resource resource = Resource.newClassPathResource(PUBLIC_HTML);
//        resourceHandler.setBaseResource(resource);

        Server server = new Server(PORT);
//        server.setHandler(new HandlerList(resourceHandler, servletContextHandler));

        server.start();

        server.join();
    }

}
