package dao.jdbc;

import dao.ClienteDAO;
import entidades.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

public class ClienteDAOImpl implements ClienteDAO {

	@Override
	public void insert(Connection conn, Cliente cliente) throws Exception {

		PreparedStatement myStmt = conn.prepareStatement("insert into en_cliente (id_cliente, nome) values (?, ?)");

		Integer idCliente = this.getNextId(conn);

		myStmt.setInt(1, idCliente);
		myStmt.setString(2, cliente.getNome());

		myStmt.execute();
		conn.commit();

		cliente.setIdCliente(idCliente);
	}

	@Override
	public void edit(Connection conn, Cliente cliente) throws Exception {

		PreparedStatement myStmt = conn.prepareStatement("update en_cliente set nome = (?) where id_cliente = (?)");

		myStmt.setString(1, cliente.getNome());
		myStmt.setInt(2, cliente.getIdCliente());

		myStmt.execute();
		conn.commit();
	}

	@Override
	public void delete(Connection conn, Integer idCliente) throws Exception {

		PreparedStatement myStmt = conn.prepareStatement("delete from en_cliente where id_cliente = ?");

		myStmt.setInt(1, idCliente);

		myStmt.execute();
		conn.commit();

	}

	@Override
	public Collection<Cliente> list(Connection conn) throws Exception {

		PreparedStatement myStmt = conn.prepareStatement("select * from en_cliente order by nome");
		ResultSet myRs = myStmt.executeQuery();

		Collection<Cliente> items = new ArrayList<>();

		while (myRs.next()) {
			Integer idCliente = myRs.getInt("id_cliente");
			String nome = myRs.getString("nome");

			items.add(new Cliente(idCliente, nome));
		}

		return items;
	}

	@Override
	public Cliente find(Connection conn, Integer idCliente) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select * from en_cliente where id_cliente = ?");

		myStmt.setInt(1, idCliente);

		ResultSet myRs = myStmt.executeQuery();

		if (!myRs.next()) {
			return null;
		}

		String nome = myRs.getString("nome");
		return new Cliente(idCliente, nome);
	}

	@Override
	public Integer getNextId(Connection conn) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select nextval('seq_en_cliente')");
		ResultSet rs = myStmt.executeQuery();
		rs.next();
		return rs.getInt(1);
	}

}
