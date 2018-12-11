package frontMain;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import websocket.AddUserWebSocketServlet;
import websocket.GetUserWebSocketServlet;
import websocket.UsersCntWebSocketServlet;

public class FrontMain {
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "/public_html";



    public static void main(String[] args) throws Exception {
       new FrontMain().start();
    }

    private void start() throws Exception {

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new AddUserWebSocketServlet()), "/adduser");
        servletContextHandler.addServlet(new ServletHolder(new GetUserWebSocketServlet()), "/getuser");
        servletContextHandler.addServlet(new ServletHolder(new UsersCntWebSocketServlet()), "/cntusrs");

        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(PUBLIC_HTML);
        resourceHandler.setBaseResource(resource);

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, servletContextHandler));

        server.start();
        server.join();
    }

}
