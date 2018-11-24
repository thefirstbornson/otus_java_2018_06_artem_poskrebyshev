package websocket;

import base.DBService;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import servlets.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//@WebServlet(name = "Number of user WebSocket Servlet", urlPatterns = { "/numusers" })
public class UsersCntWebSocketServlet extends WebSocketServlet {
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("HTTP GET method not implemented.");
    }
    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(10000);
//        factory.setCreator(new UserCntWebSocketCreator(
//                                (TemplateProcessor) getServletContext().getAttribute("templateProcessor"),
//                                (DBService) getServletContext().getAttribute("dbservice")));
        factory.setCreator(new UserCntWebSocketCreator());
        //factory.register(UsersCntWebSocket.class);
    }


}
