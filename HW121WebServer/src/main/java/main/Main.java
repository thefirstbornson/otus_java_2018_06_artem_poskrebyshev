package main;

import base.DBService;
import datasets.UserDataSet;
import dbService.DBServiceHibernateImpl;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.AddUserServlet;
import servlets.GetUserServlet;
import servlets.NumberOfUsersServlet;
import servlets.TemplateProcessor;

public class Main {
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "HW121WebServer/public_html";

    public static void main(String[] args) throws Exception {
       DBService dbService = new DBServiceHibernateImpl();

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        TemplateProcessor templateProcessor = new TemplateProcessor();

        context.addServlet(new ServletHolder(new AddUserServlet(templateProcessor, dbService)), "/adduser");
        context.addServlet(new ServletHolder(new GetUserServlet(templateProcessor, dbService)), "/getuser");
        context.addServlet(new ServletHolder(new NumberOfUsersServlet(templateProcessor, dbService)), "/numusers");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }
}
