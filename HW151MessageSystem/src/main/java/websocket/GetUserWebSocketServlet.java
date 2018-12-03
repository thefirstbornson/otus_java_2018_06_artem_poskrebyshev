package websocket;

import messagesystem.Address;
import messagesystem.message.MessageSystemContext;
import org.eclipse.jetty.websocket.servlet.*;

public class GetUserWebSocketServlet extends WebSocketServlet {
    private Address address;
    private MessageSystemContext mscontext;

    public GetUserWebSocketServlet(MessageSystemContext mscontext, Address address) {
        this.mscontext = mscontext;
        this.address = address;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(100_000_000);
        factory.setCreator((req, resp) -> new GetUserWebSocket( mscontext, address));
    }
}