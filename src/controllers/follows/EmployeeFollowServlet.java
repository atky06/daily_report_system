package controllers.follows;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeeFollowServlet
 */
@WebServlet("/employees/follow")
public class EmployeeFollowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeFollowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));

        Follow f = new Follow();


        if(e != null) {

            f.setEmployee((Employee)request.getSession().getAttribute("login_employee"));

            f.setFollow((Employee)e);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            f.setCreated_at(currentTime);
            f.setUpdated_at(currentTime);
        }

        em.getTransaction().begin();
        em.persist(f);
        em.persist(e);
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", "フォローしました");

        response.sendRedirect(request.getContextPath() + "/reports/index");



    }

}
