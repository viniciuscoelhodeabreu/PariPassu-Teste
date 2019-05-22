import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

import dao.AluguelDAO;
import dao.ClienteDAO;
import dao.FilmeDAO;
import dao.jdbc.AluguelDAOImpl;
import dao.jdbc.ClienteDAOImpl;
import dao.jdbc.FilmeDAOImpl;
import entidades.Aluguel;
import entidades.Cliente;
import entidades.Filme;

public class Main {

	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/testes", "postgres", "root");
			conn.setAutoCommit(false);

			// Demonstrar o funcionamento aqui
			Cliente cliente = new Cliente("Pedrinho");

			/*
			 * ClienteDAO Area
			 */
			ClienteDAO clienteDAO = new ClienteDAOImpl();

			// Cadastro
			clienteDAO.insert(conn, cliente);
			// Listagem
			clienteDAO.list(conn).forEach(System.out::println);
			// Alteração
			cliente.setNome("PariPassu");
			clienteDAO.edit(conn, cliente);
			// Deleção
			clienteDAO.delete(conn, cliente.getIdCliente());

			/*
			 * FilmeDAO Area
			 */
			FilmeDAO filmeDAO = new FilmeDAOImpl();

			Filme filme = new Filme();
			filme.setDataLancamento(new Date(System.currentTimeMillis()));
			filme.setNome("PariPassu");
			filme.setDescricao("Soluções colaborativas, resultados coletivos");

			// Cadastro
			filmeDAO.insert(conn, filme);
			// Listagem
			filmeDAO.list(conn).forEach(System.out::println);
			// Alteração
			filme.setNome("PariPassu teste");
			filmeDAO.edit(conn, filme);
			// Deleção
			filmeDAO.delete(conn, filme.getIdFilme());

			/*
			 * AluguelDAO Area
			 */

			AluguelDAO aluguelDAO = new AluguelDAOImpl();

			Aluguel aluguel = new Aluguel();
			aluguel.setCliente(cliente);
			aluguel.setDataAluguel(new Date(System.currentTimeMillis()));
			aluguel.setFilmes(Arrays.asList(new FilmeDAOImpl().find(conn, 1)));
			aluguel.setValor(2000);

			// Cadastro
			aluguelDAO.insert(conn, aluguel);
			// Listagem
			aluguelDAO.list(conn).forEach(System.out::println);
			// Alteração
			aluguel.setValor(10);
			aluguelDAO.edit(conn, aluguel);
			// Deleção
			aluguelDAO.delete(conn, aluguel);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Fim do teste.");
	}
}