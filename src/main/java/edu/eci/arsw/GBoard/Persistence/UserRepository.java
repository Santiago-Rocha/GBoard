package edu.eci.arsw.GBoard.Persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import edu.eci.arsw.GBoard.Persistence.Repositories.IUserRepository;
import edu.eci.arsw.GBoard.config.DataBaseConfiguration;
import edu.eci.arsw.GBoard.model.User;

@Component
public class UserRepository implements IUserRepository {

	@Autowired
	private DataBaseConfiguration database;

	@Override
	public List<User> findAll() throws GBoardException {
		String query = "select * from users";
		List<User> users = new ArrayList<>();
		Connection connection = null;
		try {
			connection = database.getDataSource().getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getLong("id"));
				user.setName(rs.getString("name"));
				user.setLastName(rs.getString("lastname"));
				user.setNickName(rs.getString("nickname"));
				user.setPassword(rs.getString("password"));
				user.setInitialDate(rs.getDate("initialdate"));
				user.setLastDate(rs.getDate("lastdate"));
				user.setGender(rs.getString("gender"));
				user.setWebPage(rs.getString("webpage"));
				user.setEmail(rs.getString("email"));
				user.setCountry(rs.getString("country"));
				user.setProfile(rs.getString("profile"));
				users.add(user);
			}
			connection.close();
			return users;
		} catch (SQLException e) {
			throw new GBoardException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new GBoardException("Failed to close connection");
			}
		}
	}

	@Override
	public User getCredentianls(String nickname, String pass) throws GBoardException {
		String query = "select * from users where nickname = '" + nickname + "' and password = '" + pass + "'";
		User user = new User();
		Connection connection= null;
		try {
			connection= database.getDataSource().getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				user.setId(rs.getLong("id"));
				user.setName(rs.getString("name"));
				user.setLastName(rs.getString("lastname"));
				user.setNickName(rs.getString("nickname"));
				user.setPassword(rs.getString("password"));
				user.setInitialDate(rs.getDate("initialdate"));
				user.setLastDate(rs.getDate("lastdate"));
				user.setGender(rs.getString("gender"));
				user.setWebPage(rs.getString("webpage"));
				user.setEmail(rs.getString("email"));
				user.setCountry(rs.getString("country"));
				user.setProfile(rs.getString("profile"));
				updateLastDate(nickname);
				
			}
			connection.close();
			return user;
		} catch (Exception e) {
			throw new GBoardException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new GBoardException("Failed to close connection");
			}
		}

	}

	private void updateLastDate(String nickname) throws GBoardException {
		String date = getActualDate();
		String query = "UPDATE \"users\" SET lastdate = '"+date+"' where nickname = '" + nickname+"'";
		Connection connection = null;
		try {
			connection = database.getDataSource().getConnection();
			Statement stmt = connection.createStatement();
			stmt.execute(query);
		} catch (SQLException e) {
			throw new GBoardException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new GBoardException("Failed to close connection");
			}
		}
	}

	@Override
	public User find(String nickname) throws GBoardException {

		String query = "select * from users where nickname = '" + nickname+"'";
		User user = new User();
		Connection connection = null;
		try {
			connection= database.getDataSource().getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				user.setId(rs.getLong("id"));
				user.setName(rs.getString("name"));
				user.setLastName(rs.getString("lastname"));
				user.setNickName(rs.getString("nickname"));
				user.setPassword(rs.getString("password"));
				user.setInitialDate(rs.getDate("initialdate"));
				user.setLastDate(rs.getDate("lastdate"));
				user.setGender(rs.getString("gender"));
				user.setWebPage(rs.getString("webpage"));
				user.setEmail(rs.getString("email"));
				user.setCountry(rs.getString("country"));
				user.setProfile(rs.getString("profile"));
				
			}
			connection.close();
			return user;
		} catch (Exception e) {
			throw new GBoardException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new GBoardException("Failed to close connection");
			}
		}
	}

	@Override
	public String save(User entity) throws GBoardException {
		String date = getActualDate();
		String query = "INSERT INTO \"users\" VALUES ((SELECT COUNT(*)+1 FROM \"users\"),'"+entity.getName()+"','"+entity.getLastName()+"','"+entity.getNickName()+"','"+entity.getPassword()+"','"+date+"','"+date+"')";
		Connection connection = null;
		try {
			connection = database.getDataSource().getConnection();
			Statement stmt = connection.createStatement();
			stmt.execute(query);
			connection.close();
			return entity.getNickName();
		} catch (SQLException e) {
			throw new GBoardException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new GBoardException("Failed to close connection");
			}
		}

	}

	@Override
	public void upadate(User entity) throws GBoardException {

		Connection connection = null;
		try {
			connection = database.getDataSource().getConnection();
			Statement stmt = connection.createStatement();
			if(entity.getGender()!=null) {
				String query = "update users  set gender='"+entity.getGender()+"' where nickname='"+entity.getNickName()+"'";
				stmt.executeUpdate(query);
			}if(entity.getLastDate()!=null) {
				String query = "update users  set lastdate='"+entity.getLastDate()+"' where nickname='"+entity.getNickName()+"'";
				stmt.executeUpdate(query);
			}if(entity.getPassword()!=null) {
				String query = "update users  set password='"+entity.getPassword()+"' where nickname='"+entity.getNickName()+"'";
				stmt.executeUpdate(query);
			}if(entity.getWebPage()!=null) {
				String query = "update users  set webpage='"+entity.getWebPage()+"' where nickname='"+entity.getNickName()+"'";
				stmt.executeUpdate(query);
			}if(entity.getEmail()!=null) {
				String query = "update users  set email='"+entity.getEmail()+"' where nickname='"+entity.getNickName()+"'";
				stmt.executeUpdate(query);
			}if(entity.getCountry()!=null) {
				String query = "update users  set country='"+entity.getCountry()+"' where nickname='"+entity.getNickName()+"'";
				stmt.executeUpdate(query);
			}if(entity.getProfile()!=null) {
				String query = "update users  set profile='"+entity.getProfile()+"' where nickname='"+entity.getNickName()+"'";
				stmt.executeUpdate(query);
			}
			connection.close();
		} catch (SQLException e) {
			throw new GBoardException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new GBoardException("Failed to close connection");
			}
		}

	}

	@Override
	public void delete(User o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub

	}

	private String getActualDate() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String date = sdf.format(new Date());
		return date;
	}

}
