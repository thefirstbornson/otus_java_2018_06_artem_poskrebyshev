package websocket;

import messagesystem.Address;
import messagesystem.MessageSystemContext;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class AddUserWebSocketServlet  extends WebSocketServlet {
    private Address address;
    private MessageSystemContext mscontext;

    public AddUserWebSocketServlet(MessageSystemContext mscontext, Address address) {
        this.mscontext = mscontext;
        this.address = address;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(100_000_000);
        factory.setCreator((req, resp) -> new AddUserWebSocket( mscontext, address));
    }
}