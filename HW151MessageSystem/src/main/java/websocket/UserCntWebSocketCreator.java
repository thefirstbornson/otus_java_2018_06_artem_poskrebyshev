package websocket;

import base.DBService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import servlets.TemplateProcessor;

/**
 * @author v.chibrikov
 */
public class UserCntWebSocketCreator implements WebSocketCreator {
    private TemplateProcessor templateProcessor;
    private DBService dbService;

    public UserCntWebSocketCreator() {
    }

    public UserCntWebSocketCreator(DBService dbService) {
        this.dbService = dbService;
    }

    public UserCntWebSocketCreator(TemplateProcessor templateProcessor, DBService dbService) {
        this.templateProcessor = templateProcessor;
        this.dbService = dbService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
//        return new UsersCntWebSocket (templateProcessor, dbService);
        return new UsersCntWebSocket();
    }
}
