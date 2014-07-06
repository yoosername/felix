package keo.examples.felix;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("serial")
public class TestServlet
    extends HttpServlet
{
	private final String name;

    public TestServlet(String name)
    {
        this.name = name;
    }
    
    @Override
    public void init(ServletConfig config)
        throws ServletException
    {
        doLog("Init with config [" + config + "]");
        super.init(config);  
    }

    @Override
    public void destroy()
    {
        doLog("Destroyed servlet");
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();

        out.println("Request = " + req);
        out.println("PathInfo = " + req.getPathInfo());
    }

    private void doLog(String message)
    {
        System.out.println("## [" + this.name + "] " + message);
    }
}