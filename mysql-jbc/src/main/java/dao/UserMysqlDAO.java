package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

import conexao_jdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.UserMysqlJava;

public class UserMysqlDAO {

	private Connection connection;
	
	public UserMysqlDAO() {
		connection = SingleConnection.getConnection();
	}
	
	// INSERT
	public void salvar(UserMysqlJava userMysqlJava) {
		try {
			String sql = "insert into usuario values (?,?,?)";
			
			PreparedStatement insert = connection.prepareStatement(sql);
			
			insert.setLong(1, 3);
			insert.setString(2, "Luiz Augusto");
			insert.setString(3, "luisaugusto98@gmail.com");
			
			insert.execute();
			connection.commit();
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	//SELECT * FROM
	public List<UserMysqlJava> listar() throws SQLException{
		List<UserMysqlJava> list = new ArrayList<UserMysqlJava>();
		String sql = "select * from usuario";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			UserMysqlJava userMysqlJava = new UserMysqlJava();
			userMysqlJava.setId(resultado.getLong("id"));
			userMysqlJava.setNome(resultado.getString("nome"));
			userMysqlJava.setEmail(resultado.getString("email"));
			
			list.add(userMysqlJava);
		}
		
		return list;
	}
	
	// SELECT ID
	public UserMysqlJava buscar(Long id) throws SQLException{
		UserMysqlJava retorno = new UserMysqlJava();
		
		String sql = "select * from usuario where id = " + id;
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			retorno.setId(resultado.getLong("id"));
			retorno.setNome(resultado.getString("nome"));
			retorno.setEmail(resultado.getString("email"));
			
		}
		
		return retorno;
	}
	
	// UPDATE
	public void atualizar(UserMysqlJava userMysqlJava) throws SQLException {
		
		try {
		String sql = "update usuario set nome = ? where id = " + userMysqlJava.getId();
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, userMysqlJava.getNome());
		
		statement.execute();
		connection.commit();
		}catch(Exception e) {
			try {
			connection.rollback();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	// DELETE
	public void deletar(Long id) {
		try {
			String sql = "delete from usuario where id = " + id;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();
			
			connection.commit();
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	// INSERT NA TABELA TELEFONE
	public void salvarTelefone(Telefone telefone) {
		try {
			String sql = "insert into telefoneusuario values (?, ?, ?, ?)";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, telefone.getId());
			statement.setString(2, telefone.getNumero());
			statement.setString(3, telefone.getTipo());
			statement.setLong(4, telefone.getUsuario());
			
			statement.execute();
			connection.commit();
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	// INNER JOIN
	public List<BeanUserFone> listaUserFone(Long id){
		List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();
		
		String sql = " select nome, numero, email from telefoneusuario as fone "; 
			   sql += " inner join usuario as userp ";
			   sql += " on fone.usuariopessoa = userp.id ";
			   sql	+= " where userp.id = 1 ";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				BeanUserFone userfone = new BeanUserFone();
				userfone.setNome(resultSet.getString("nome"));
				userfone.setEmail(resultSet.getString("email"));
				userfone.setNumero(resultSet.getString("numero"));
				
				beanUserFones.add(userfone);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return beanUserFones;
	}
	
	// DELETE EM CASCATA
	public void deleteFonesUser(Long idUser) {
		try {
			String sqlFone = " delete from telefoneusuario where usuariopessoa = " + idUser;
			String sqlUser = " delete from usuario where id = " + idUser;
			
			/* DELETANDO PRIMEIRO O ELEMENTO FILHO */
			PreparedStatement preparedStatement = connection.prepareStatement(sqlFone);
			preparedStatement.executeUpdate();
			
			connection.commit();
			
			/* DELETANDO O ELEMENTO PAI */
			preparedStatement = connection.prepareStatement(sqlUser);
			preparedStatement.executeUpdate();
			
			connection.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
