package main;

//import base.DBService;
//import dbService.dbCache.DBCache;
//import dbService.dbCache.DBCacheInMemory;
//import dbService.DBServiceHibernateImpl;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import servlets.TemplateProcessor;
import websocket.UsersCntWebSocketServlet;
import websocket.WebSocketChatServlet;

public class Main {
    private final static int PORT = 8099;
    private final static String PUBLIC_HTML = "StudySpring/public_html";

    public static void main(String[] args) throws Exception {
//        DBService dbService = new DBServiceHibernateImpl();
//        DBCache dbService.dbCache = new DBCacheInMemory(50, 500, 25);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);
//        Resource resource = Resource.newClassPathResource(PUBLIC_HTML);
//        resourceHandler.setBaseResource(resource);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        TemplateProcessor templateProcessor = new TemplateProcessor();
//        context.addServlet(new ServletHolder(new AddUserServlet(templateProcessor, dbService)), "/adduser");
//        context.addServlet(new ServletHolder(new GetUserServlet(templateProcessor, dbService, dbService.dbCache)), "/getuser");
////        context.addServlet(new ServletHolder(new NumberOfUsersServlet(templateProcessor, dbService)), "/numusers");
////        context.addServlet(new ServletHolder(new UsersCntWebSocketServlet(templateProcessor, dbService)), "/numusers");
//        context.setAttribute("dbservice",dbService);
//        context.setAttribute("templateProcessor",templateProcessor);
        context.addServlet( UsersCntWebSocketServlet.class, "/numusrs");
        context.addServlet(WebSocketChatServlet.class, "/chat");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }
}
