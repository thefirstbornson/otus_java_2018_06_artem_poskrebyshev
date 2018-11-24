package main;

import base.DBService;
import datasets.UserDataSet;
import dbCache.DBCache;
import dbCache.DBCacheInMemory;
import dbService.DBServiceHibernateImpl;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import servlets.AddUserServlet;
import servlets.GetUserServlet;
import servlets.TemplateProcessor;
import websocket.UsersCntWebSocketServlet;

public class Main {
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "/public_html";

    public static void main(String[] args) throws Exception {
        DBService dbService = new DBServiceHibernateImpl();
        DBCache dbCache = new DBCacheInMemory(50, 500, 25);

        ResourceHandler resourceHandler = new ResourceHandler();
//        resourceHandler.setResourceBase(PUBLIC_HTML);
        Resource resource = Resource.newClassPathResource(PUBLIC_HTML);
        resourceHandler.setBaseResource(resource);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        TemplateProcessor templateProcessor = new TemplateProcessor();
        context.addServlet(new ServletHolder(new AddUserServlet(templateProcessor, dbService)), "/adduser");
        context.addServlet(new ServletHolder(new GetUserServlet(templateProcessor, dbService, dbCache)), "/getuser");
//        context.addServlet(new ServletHolder(new NumberOfUsersServlet(templateProcessor, dbService)), "/numusers");
//        context.addServlet(new ServletHolder(new UsersCntWebSocketServlet(templateProcessor, dbService)), "/numusers");
        context.setAttribute("dbservice",dbService);
        context.setAttribute("templateProcessor",templateProcessor);
        context.addServlet( UsersCntWebSocketServlet.class, "/numusers");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }
}
