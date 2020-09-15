package br.edu.utfpr.dv.siacoes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.dv.siacoes.log.UpdateEvent;
import br.edu.utfpr.dv.siacoes.model.Activity;

public class ActivityDAO extends CampoUnificadorDAO{
	
    CampoUnificadorDAO campo = new CampoUificadorDAO();
    

	super.Conexao ();
	super.List<Campo> listAll();
	
	public List<Activity> listByDepartment(int idDepartment) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{
			conn = ConnectionDAO.getInstance().getConnection();
			stmt = conn.prepareStatement(
					"SELECT activity.*, activityunit.description AS unit, activitygroup.description AS groupDescription, activitygroup.sequence AS groupSequence " +
					"FROM activity INNER JOIN activityunit ON activityunit.idActivityUnit=activity.idActivityUnit " +
					"INNER JOIN activitygroup ON activitygroup.idActivityGroup=activity.idActivityGroup " +
					"WHERE activity.idDepartment=? ORDER BY activitygroup.sequence, activity.sequence");
			
			stmt.setInt(1, idDepartment);
					
			rs = stmt.executeQuery();
			
			List<Activity> list = new ArrayList<Activity>();
			
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
	
	public List<Activity> listByGroup(int idDepartment, int idGroup) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{
			conn = ConnectionDAO.getInstance().getConnection();
			stmt = conn.prepareStatement(
				"SELECT activity.*, activityunit.description AS unit, activitygroup.description AS groupDescription, activitygroup.sequence AS groupSequence " +
				"FROM activity INNER JOIN activityunit ON activityunit.idActivityUnit=activity.idActivityUnit " +
				"INNER JOIN activitygroup ON activitygroup.idActivityGroup=activity.idActivityGroup " +
				"WHERE activity.idDepartment=? AND activity.idActivityGroup=? ORDER BY activity.sequence");
		
			stmt.setInt(1, idDepartment);
			stmt.setInt(2, idGroup);
					
			rs = stmt.executeQuery();
			
			List<Activity> list = new ArrayList<Activity>();
			
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
	
	public Activity findById(int id) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{
			conn = ConnectionDAO.getInstance().getConnection();
			stmt = conn.prepareStatement(
				"SELECT activity.*, activityunit.description AS unit, activitygroup.description AS groupDescription, activitygroup.sequence AS groupSequence " +
				"FROM activity INNER JOIN activityunit ON activityunit.idActivityUnit=activity.idActivityUnit " +
				"INNER JOIN activitygroup ON activitygroup.idActivityGroup=activity.idActivityGroup " +
				"WHERE activity.idActivity=?");
		
			stmt.setInt(1, id);
			
			rs = stmt.executeQuery();
			
			if(rs.next()){
				return this.loadObject(rs);
			}else{
				return null;
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
	
	super.save();
	super.Campo loadObject();
	
	
	
	public void moveUp(int idActivity) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{
			conn = ConnectionDAO.getInstance().getConnection();
			stmt = conn.prepareStatement("SELECT sequence, idActivityGroup FROM activity WHERE idActivity=?");
			stmt.setInt(1, idActivity);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				int sequence = rs.getInt("sequence");
				int idActivityGroup = rs.getInt("idActivityGroup");
				
				rs.close();
				stmt.close();
				stmt = conn.prepareStatement("SELECT idActivity FROM activity WHERE idActivityGroup=? AND sequence < ? ORDER BY sequence DESC");
				stmt.setInt(1, idActivityGroup);
				stmt.setInt(2, sequence);
				rs = stmt.executeQuery();
				
				if(rs.next()){
					int idActivity2 = rs.getInt("idActivity");
					
					try{
						conn.setAutoCommit(false);
						
						stmt = conn.prepareStatement("UPDATE activity SET sequence=? WHERE idActivity=?");
						stmt.setInt(1, sequence);
						stmt.setInt(2, idActivity2);
						stmt.execute();
						
						stmt = conn.prepareStatement("UPDATE activity SET sequence=? WHERE idActivity=?");
						stmt.setInt(1, sequence - 1);
						stmt.setInt(2, idActivity);
						stmt.execute();
						
						conn.commit();
					}catch(SQLException e){
						conn.rollback();
						
						throw e;
					}finally{
						conn.setAutoCommit(true);
					}
				}
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
	
	public void moveDown(int idActivity) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{
			conn = ConnectionDAO.getInstance().getConnection();
			stmt = conn.prepareStatement("SELECT sequence, idActivityGroup FROM activity WHERE idActivity=?");
			stmt.setInt(1, idActivity);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				int sequence = rs.getInt("sequence");
				int idActivityGroup = rs.getInt("idActivityGroup");
				
				rs.close();
				stmt.close();
				stmt = ConnectionDAO.getInstance().getConnection().prepareStatement("SELECT idActivity FROM activity WHERE idActivityGroup=? AND sequence > ? ORDER BY sequence");
				stmt.setInt(1, idActivityGroup);
				stmt.setInt(2, sequence);
				rs = stmt.executeQuery();
				
				if(rs.next()){
					int idActivity2 = rs.getInt("idActivity");
					
					try{
						conn.setAutoCommit(false);
						
						stmt = conn.prepareStatement("UPDATE activity SET sequence=? WHERE idActivity=?");
						stmt.setInt(1, sequence);
						stmt.setInt(2, idActivity2);
						stmt.execute();
						
						stmt = conn.prepareStatement("UPDATE activity SET sequence=? WHERE idActivity=?");
						stmt.setInt(1, sequence + 1);
						stmt.setInt(2, idActivity);
						stmt.execute();
						
						conn.commit();
					}catch(SQLException e){
						conn.rollback();
						
						throw e;
					}finally{
						conn.setAutoCommit(true);
					}
				}
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

}
