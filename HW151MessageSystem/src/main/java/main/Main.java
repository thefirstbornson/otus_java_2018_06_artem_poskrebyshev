package main;

import dbService.DBCache;
import dbService.DBCacheInMemory;
import dbService.DBService;
import dbService.DBServiceHibernateImpl;
import messagesystem.Address;
import messagesystem.MessageSystem;
import messagesystem.MessageSystemContext;
import messagesystem.adressees.DBServiceMS;
import messagesystem.adressees.FrontendService;
import messagesystem.adressees.dbimpl.DbServiceMSimpl;
import messagesystem.adressees.frontimpl.FrontendServiceGetUser;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import websocket.AddUserWebSocketServlet;
import websocket.GetUserWebSocketServlet;
import websocket.UsersCntWebSocketServlet;

public class Main {
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "/public_html";

    public static void main(String[] args) throws Exception {
        DBCache dbCache = new DBCacheInMemory(50, 500, 25);
        DBService dbService = new DBServiceHibernateImpl(dbCache);

        MessageSystem messageSystem = new MessageSystem();
        MessageSystemContext mscontext = new MessageSystemContext(messageSystem);

        Address addUserAddress = new Address("AddUserAddress");
        Address getUserAddress = new Address("GetUserAddress");
        Address usersCountUsersAddress  = new Address( "UsersCountAddress");
        Address dbAddress = new Address("DBAddress");

        mscontext.setFrontAddress(addUserAddress);
        mscontext.setFrontAddress(getUserAddress);
        mscontext.setFrontAddress(usersCountUsersAddress);
        mscontext.setDbAddress(dbAddress);

        FrontendService frontendServiceGetUser = new FrontendServiceGetUser(mscontext, getUserAddress );
        frontendServiceGetUser.init();
        DBServiceMS dbServiceMS = new DbServiceMSimpl(mscontext, dbAddress, dbService);
        dbServiceMS.init();

        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(PUBLIC_HTML);
        resourceHandler.setBaseResource(resource);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new AddUserWebSocketServlet(dbService)), "/adduser");
        servletContextHandler.addServlet(new ServletHolder(new GetUserWebSocketServlet(mscontext, getUserAddress)), "/getuser");
        servletContextHandler.addServlet(new ServletHolder(new UsersCntWebSocketServlet(dbService)), "/cntusrs");
        messageSystem.start();

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, servletContextHandler));

        server.start();
        server.join();
    }
}
