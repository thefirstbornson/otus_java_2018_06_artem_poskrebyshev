package websocket;

import fcontext.FrontendContext;
import messagesystem.Address;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class GetUserWebSocketServlet extends WebSocketServlet {
    private Address address;
    private FrontendContext context;

    public GetUserWebSocketServlet(FrontendContext context,Address address) {
        this.context = context;
        this.address = address;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(100_000_000);
        factory.setCreator((req, resp) -> new GetUserWebSocket( context, address));
    }
}