package ge.biterium.chat;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Created by Zura Tikaradze
 */
@WebServlet("")
public class UserNameServlet extends HttpServlet {
    public static final String VIEW = "/chatroompage.jsp";
    public static final String VIEW1 = "/usernamepage.jsp";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username=req.getParameter("username");
        PrintWriter printWriter = resp.getWriter();
        if(username!=null) {
            HttpSession session = req.getSession(true);
            String roomName = req.getParameter("roomSelect");
            if (((roomName != null)) && (roomName.equals("newRoomOption"))) roomName = req.getParameter("newRoomName");
            session.setAttribute("username", username);
            session.setAttribute("roomname", roomName);
            RequestDispatcher view = req.getRequestDispatcher(VIEW);
            view.forward(req, resp);
        }
        else {
            RequestDispatcher view = req.getRequestDispatcher(VIEW1);
            view.forward(req, resp);
        }
    }
}
