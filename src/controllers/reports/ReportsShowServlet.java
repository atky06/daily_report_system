package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");




        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        long likes_count = (long)em.createNamedQuery("getMyLikesCount", Long.class)
                .setParameter("employee", login_employee)
                .setParameter("report", r)
                .getSingleResult();

        long follows_count = (long)em.createNamedQuery("getMyFollowsCount", Long.class)
                .setParameter("employee", login_employee)
                .setParameter("follow", r.getEmployee())
                .getSingleResult();

        em.close();



        request.setAttribute("follows_count", follows_count);
        request.setAttribute("likes_count", likes_count);
        request.setAttribute("report", r);
        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}
