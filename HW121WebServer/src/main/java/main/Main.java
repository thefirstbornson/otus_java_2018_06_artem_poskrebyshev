package main;

import base.DBService;
import datasets.AddressDataSet;
import datasets.PhoneDataSet;
import datasets.UserDataSet;
import dbService.DBServiceHibernateImpl;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.AdminServlet;
import servlets.TemplateProcessor;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private final static int PORT = 8080;
    private final static String PUBLIC_HTML = "HW121WebServer/public_html";

    public static void main(String[] args) throws Exception {
//        DBService dbService = new DBServiceHibernateImpl();
//
//        UserDataSet usr = new UserDataSet("Peter", 45);
//        List<PhoneDataSet> listPhone = new ArrayList<>();
//        listPhone.add(new PhoneDataSet("+79062137713", usr));
//        listPhone.add(new PhoneDataSet("+79062193063", usr));
//        listPhone.add(new PhoneDataSet("+79062198553", usr));
//        usr.setPhones(listPhone);
//        usr.setAddress(new AddressDataSet("Mayskiy per",usr));
//
//        dbService.save(usr);
//        System.out.println(dbService.load(1, UserDataSet.class));
//        dbService.close();

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        TemplateProcessor templateProcessor = new TemplateProcessor();

        context.addServlet(new ServletHolder(new AdminServlet(templateProcessor)), "/admin");
        //context.addServlet(AdminServlet.class, "/admin");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();

    }
}
