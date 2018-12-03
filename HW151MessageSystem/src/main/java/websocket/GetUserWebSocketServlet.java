package websocket;

import messagesystem.Address;
import messagesystem.MessageSystemContext;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class GetUserWebSocketServlet extends WebSocketServlet {
    private Address address;
    private MessageSystemContext mscontext;

    public GetUserWebSocketServlet(MessageSystemContext mscontext, Address address) {
        this.mscontext = mscontext;
        this.address = address;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(10000);
        factory.setCreator((req, resp) -> new GetUserWebSocket(mscontext, address));
    }
}