package br.edu.utfpr.dv.siacoes.dao;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public abstract class CampoUnificadorDAO {

    public Conexao (int id) throws SQLException{

        Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

        try{
			conn = ConnectionDAO.getInstance().getConnection();
			stmt = conn.prepareStatement("");
		
			stmt.setInt(1, id);
			
			rs = stmt.executeQuery();
			
			if(rs.next()){
				return(rs);
			}else{
				return 0;
			}
		}finally{
			if((rs != null) && !rs.isClosed())
				rs.close();
			if((stmt != null) && !stmt.isClosed())
				stmt.close();
			if((conn != null) && !conn.isClosed())
				conn.close();
		}
    }
    
    public List<Campo> listAll() throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			conn = ConnectionDAO.getInstance().getConnection();
			stmt = conn.createStatement();
		
			rs = stmt.executeQuery("") + " ORDER BY Campo.name");
			
			List<Campo> list = new ArrayList<Campo>();
			
			while(rs.next()){
				list.add(this.loadObject(rs));
			}
			
			return list;
		}finally{
			if((rs != null) && !rs.isClosed())
				rs.close();
			if((stmt != null) && !stmt.isClosed())
				stmt.close();
			if((conn != null) && !conn.isClosed())
				conn.close();
		}
    }
    public int save(CampoUnificado campo) throws SQLException{
		boolean insert = (campo.getIdCampo() == 0);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{
			conn = ConnectionDAO.getInstance().getConnection();
			
			if(insert){
				stmt = conn.prepareStatement();
			}else{
				stmt = conn.prepareStatement();
			}
			
			stmt.setInt(1,);
			stmt.setInt(2, );
			stmt.setInt(3, );
			stmt.setString(4, );
			stmt.setDouble(5, a);
			stmt.setDouble(6,);
			stmt.setInt(7, );
            stmt.setInt(8, );
            
			if(){
				stmt.setNull();
			}else{
				stmt.setDate();
			}
			stmt.setString();
			
			if(){
				stmt.setInt());
			}
			
			stmt.execute();
			
			if(insert){
				rs = stmt.getGeneratedKeys();
				
				if(rs.next()){
                    
                }
			}
			
			return ;
		}finally{
			if((rs != null) && !rs.isClosed())
				rs.close();
			if((stmt != null) && !stmt.isClosed())
				stmt.close();
			if((conn != null) && !conn.isClosed())
				conn.close();
		}
    }
    
    private Campo loadObject(ResultSet rs) throws SQLException{
        
		CampoUnificadorDAO campo = new CampoUnificadorDAO();
		
		campo.setIdCampoUnificadorDAO(rs.getInt();
		campo.setUser(new User());
		campo.getUser().setIdUser(rs.getInt();
		campo.getUser().setName(rs.getString();
		campo.setModule(Module.SystemModule.valueOf(rs.getInt());
		campo.setTitle(rs.getString());
		campo.setDescription(rs.getString());
		campo.setReportDate(rs.getDate());
		campo.setType(CampoUnificadorDAO.BugType.valueOf(rs.getInt()));
		campo.setStatus(CampoUnificadorDAO.BugStatus.valueOf(rs.getInt()));
		campo.setStatusDate(rs.getDate());
		campo.setStatusDescription(rs.getString());
		
		return campo;
	}
	
    }
