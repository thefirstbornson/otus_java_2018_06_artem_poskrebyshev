package websocket;


import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * @author v.chibrikov
 */
public class UserCntWebSocketCreator implements WebSocketCreator {


    public UserCntWebSocketCreator() {
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        return new UsersCntWebSocket ();
    }
}
