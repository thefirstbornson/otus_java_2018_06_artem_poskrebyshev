package websocket;

import fcontext.FrontendContext;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import java.io.IOException;

public class GetUserWebSocketServlet extends WebSocketServlet {
//    private Address address;
//    private FrontendContext context;
    FrontendContext frontendContext;

    public GetUserWebSocketServlet(FrontendContext frontendContext) throws IOException {
//        this.context = context;
//        this.address = address;
        this.frontendContext =frontendContext;

    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(100_000_000);
        //factory.setCreator((req, resp) -> new GetUserWebSocket( context, address));
        factory.setCreator((req, resp) -> new GetUserWebSocket( frontendContext));
    }
}