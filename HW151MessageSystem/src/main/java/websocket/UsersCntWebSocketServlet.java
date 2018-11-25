package websocket;

import base.DBService;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;


//@WebServlet(name = "Number of user WebSocket Servlet", urlPatterns = { "/numusers" })
public class UsersCntWebSocketServlet extends WebSocketServlet {
    DBService dbService;

    public UsersCntWebSocketServlet(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(10000);
        factory.setCreator(new UserCntWebSocketCreator(dbService));
//        factory.setCreator(new UserCntWebSocketCreator(
//                                (TemplateProcessor) getServletContext().getAttribute("templateProcessor"),
//                                (DBService) getServletContext().getAttribute("dbservice")));
//        factory.setCreator(new UserCntWebSocketCreator());
        //factory.register(UsersCntWebSocket.class);
    }


}
