package websocket;

import dbService.DBService;
import dbCache.DBCache;
import org.eclipse.jetty.websocket.servlet.*;

public class GetUserWebSocketServlet extends WebSocketServlet {
    private final DBService dbService;
    private final DBCache dbCache;

    public GetUserWebSocketServlet(DBService dbService, DBCache dbCache) {
        this.dbService = dbService;
        this.dbCache = dbCache;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(10000);
        factory.setCreator(new WebSocketCreator() {
            @Override
            public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
                return new GetUserWebSocket(dbService, dbCache);
            }
        });
    }
}