package controllers.likes;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Like;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class IndexLikesServlet
 */
@WebServlet("/likes/index")
public class IndexLikesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexLikesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        // 該当のID一件のみをデータベースから取得
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));


        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }

        // 最大件数と開始位置を指定してメッセージを取得
        List<Like> likes = em.createNamedQuery("getMyAllLikes", Like.class)
                                  .setParameter("report", r)
                                   .setFirstResult(15 * (page - 1))
                                  .setMaxResults(15)
                                  .getResultList();

        // いいねしている件数を取得
        long likes_count = (long)em.createNamedQuery("getLikesCount", Long.class)
                                     .setParameter("report", r)
                                     .getSingleResult();

        em.close();

        request.setAttribute("report", r);
        request.setAttribute("employee", login_employee);
        request.setAttribute("likes", likes);
        request.setAttribute("likes_count", likes_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/likes/index.jsp");
        rd.forward(request, response);

    }

}
