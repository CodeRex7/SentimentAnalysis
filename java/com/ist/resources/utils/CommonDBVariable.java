
package com.ist.resources.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SOUMYAGOURAB.S
 */
public class CommonDbVariable {
    
    
    public Connection con = null;
    public Statement st = null;
    public PreparedStatement pt = null;
    public CallableStatement ct = null;
    public ResultSet rs = null;
    public ResultSetMetaData rsmd = null;
    
    public String ltxtQuery = null;
    
    public CommonDbVariable(){
        con = CommonUtils.database_connect();
        ltxtQuery = "";
    }
    
    public int update(String txtTableName,Map<String,String> updateColumns,Map<String,String> whereClause)
    {
        int i = -1;
        try {
            ltxtQuery = "UPDATE "+txtTableName + " SET " ;
            for(Map.Entry<String,String> entry : updateColumns.entrySet())
            {
                ltxtQuery += entry.getKey() +" = "+entry.getValue();
            } 
            if(whereClause.size()>0)
            {
                ltxtQuery += " WHERE ";
                for(Map.Entry<String,String> entry : whereClause.entrySet())
                {
                    ltxtQuery += entry.getKey() +" = "+entry.getValue();
                }
            }
            
            st = con.createStatement();
            i = st.executeUpdate(ltxtQuery);
        } catch (Exception ex) {
            Logger.getLogger(CommonDbVariable.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return i;
    }
}
