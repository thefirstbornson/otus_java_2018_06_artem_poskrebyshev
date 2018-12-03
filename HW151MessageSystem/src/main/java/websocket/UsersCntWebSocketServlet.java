package websocket;

import dbService.DBService;
import org.eclipse.jetty.websocket.servlet.*;

public class UsersCntWebSocketServlet extends WebSocketServlet {
    DBService dbService;

    public UsersCntWebSocketServlet(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(10000);
        factory.setCreator(new WebSocketCreator() {
            @Override
            public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
                return new UsersCntWebSocket (dbService);
            }
        });
    }
}