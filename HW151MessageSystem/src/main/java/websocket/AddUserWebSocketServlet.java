package websocket;

import base.DBService;
import org.eclipse.jetty.websocket.servlet.*;

public class AddUserWebSocketServlet  extends WebSocketServlet {
    DBService dbService;

    public AddUserWebSocketServlet(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(60_000_000);
        factory.setCreator(new WebSocketCreator() {
            @Override
            public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
                System.out.println(req.getParameterMap().toString());
                return new AddUserWebSocket (dbService);
            }
        });
    }
}