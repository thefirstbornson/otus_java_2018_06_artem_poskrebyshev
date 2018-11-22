package websocket;

import base.DBService;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import servlets.TemplateProcessor;

import javax.servlet.annotation.WebServlet;



//@WebServlet(name = "Number of user WebSocket Servlet", urlPatterns = { "/numusers" })
public class UsersCntWebSocketServlet extends WebSocketServlet {
    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(10000);
        factory.setCreator(new UserCntWebSocketCreator(
                                (TemplateProcessor) getServletContext().getAttribute("templateProcessor"),
                                (DBService) getServletContext().getAttribute("dbservice")));
        //factory.register(UsersCntWebSocket.class);
    }


}
