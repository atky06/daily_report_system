package controllers.reports;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
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
 * Servlet implementation class ReportsLikeServlet
 */
@WebServlet("/reports/like")
public class ReportsLikeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsLikeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // データベースに接続
        EntityManager em = DBUtil.createEntityManager();

        // 該当のID1件のみをデータベースから取得
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        Like l = new Like();

        if(r != null) {
        r.setLike_count(r.getLike_count() + 1);

        l.setEmployee((Employee)request.getSession().getAttribute("login_employee"));

        l.setReport((Report)r);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        l.setCreated_at(currentTime);
        l.setUpdated_at(currentTime);
        }


        // データベースに保存
        em.getTransaction().begin();
        em.persist(l);
        em.persist(r);
        em.getTransaction().commit();
        em.close();  // 接続の終了

        request.getSession().setAttribute("flush", "いいねをしました。");

        response.sendRedirect(request.getContextPath() + "/reports/index");


    }

}
