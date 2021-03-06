/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Operations;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Asir
 */
public class ViewEventLog extends Operations {
    
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        
        ArrayList resultados = new ArrayList();
        
        try
        {
            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://127.0.0.1/sambaLOG";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "admin", "departamento");
            
            String query = "SELECT * FROM LOG LIMIT 5";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while (rs.next())
            {
                resultados.add(rs.getString("mensaje"));
            }
            st.close();
            
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        
        request.setAttribute("resultados", resultados);
        RequestDispatcher dispatcher = request.getRequestDispatcher("eventLog.jsp");
        if (dispatcher != null){
            try {
                dispatcher.forward(request, response);
            } catch (ServletException ex) {
                Logger.getLogger(ViewEventLog.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ViewEventLog.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                response.sendRedirect("/SambaQuarantine");
            } catch (IOException ex) {
                Logger.getLogger(ViewEventLog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
