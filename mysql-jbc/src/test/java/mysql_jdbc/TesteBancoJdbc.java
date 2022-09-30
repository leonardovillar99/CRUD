package mysql_jdbc;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import dao.UserMysqlDAO;
import model.BeanUserFone;
import model.Telefone;
import model.UserMysqlJava;

public class TesteBancoJdbc {
	
	// Teste com JUnite para função de insert do banco de dados
	@Test
	public void dataInit() {
		UserMysqlDAO userMysqlDAO = new UserMysqlDAO();
		UserMysqlJava userMysqlJava = new UserMysqlJava();
		
		userMysqlDAO.salvar(userMysqlJava);
	}
	
	// Teste com JUnit para função de select de todas as colunas e linhas do banco de dados
	@Test
	public void initListar() {
		UserMysqlDAO dao = new UserMysqlDAO();
		try {
			List<UserMysqlJava> list = dao.listar();
			
			for (UserMysqlJava userMysqlJava : list) {
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(userMysqlJava);
				System.out.println("--------------------------------------------------------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Teste com JUnit para função de select buscando por id no banco de dados
	@Test
	public void buscarId() {
		UserMysqlDAO userMysqlDAO = new UserMysqlDAO();
		
		try {
			UserMysqlJava userMysqlJava = userMysqlDAO.buscar(3L);
			
			System.out.println(userMysqlJava);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Teste com JUnit para função de update do banco de dados
	@Test
	public void update() {		
		try {
			UserMysqlDAO userMysqlDAO = new UserMysqlDAO();

			UserMysqlJava objetoBanco = userMysqlDAO.buscar(1L);
			
			objetoBanco.setNome("Leonardo Villar");
			userMysqlDAO.atualizar(objetoBanco);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Teste com JUnit para função de delete do banco de dados
	@Test
	public void delete() {
		try {
			UserMysqlDAO userMysqlDAO = new UserMysqlDAO();
			userMysqlDAO.deletar(3L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Teste com JUnit para função de insert na tabela telefones
	@Test
	public void insertTelefone() {
		Telefone telefone = new Telefone();
		telefone.setId(14L);
		telefone.setNumero("11991717658");
		telefone.setTipo("Celular");
		telefone.setUsuario(4L);
		
		UserMysqlDAO dao = new UserMysqlDAO();
		dao.salvarTelefone(telefone);
	}
	
	// Teste com JUnit para função de inner join trazendo nome, número e email.
	@Test
	public void retornaLista() {
		UserMysqlDAO dao = new UserMysqlDAO();
		
		List<BeanUserFone> beanUserFones = dao.listaUserFone(1L);
		
		for (BeanUserFone beanUserFone : beanUserFones) {
			System.out.println(beanUserFone);
			System.out.println("--------------------------------------------------------");
		}
	}
	
	// Teste com JUnit para função de delete em cascata de elementos filhos e pais
	@Test
	public void deleteUserFone() {
		UserMysqlDAO dao = new UserMysqlDAO();
		dao.deleteFonesUser(2L);
	}
}
