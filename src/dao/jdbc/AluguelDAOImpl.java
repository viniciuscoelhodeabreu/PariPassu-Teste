package dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dao.AluguelDAO;
import entidades.Aluguel;
import entidades.Filme;

public class AluguelDAOImpl implements AluguelDAO {

	@Override
	public void insert(Connection conn, Aluguel aluguel) throws Exception {
		Integer idAluguel = this.getNextId(conn);

		PreparedStatement ps = conn
				.prepareStatement("INSERT INTO en_aluguel(id_aluguel,id_cliente,data_aluguel,valor) VALUES(?,?,?,?)");

		ps.setInt(1, idAluguel);
		ps.setInt(2, aluguel.getCliente().getIdCliente());
		ps.setDate(3, new Date(aluguel.getDataAluguel().getTime()));
		ps.setFloat(4, aluguel.getValor());

		ps.executeUpdate();
		ps.close();

		PreparedStatement psf = conn.prepareStatement("INSERT INTO re_aluguel_filme(id_filme,id_aluguel) VALUES(?,?)");
		for (Filme filme : aluguel.getFilmes()) {
			psf.setInt(1, filme.getIdFilme());
			psf.setInt(2, idAluguel);
			psf.addBatch();
		}
		
		psf.executeBatch();
		psf.close();
		conn.commit();

		aluguel.setIdAluguel(idAluguel);
	}

	@Override
	public Integer getNextId(Connection conn) throws Exception {
		PreparedStatement ps = conn.prepareStatement("SELECT nextval('seq_en_aluguel')");
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt(1);
	}

	@Override
	public void edit(Connection conn, Aluguel aluguel) throws Exception {
		PreparedStatement ps = conn
				.prepareStatement("UPDATE en_aluguel SET data_aluguel=?, valor=? WHERE id_aluguel=?;");
		ps.setDate(1, new Date(aluguel.getDataAluguel().getTime()));
		ps.setFloat(2, aluguel.getValor());
		ps.setInt(3, aluguel.getIdAluguel());
		ps.executeUpdate();
		ps.close();

		PreparedStatement psf = conn.prepareStatement("UPDATE re_aluguel_filme SET id_filme=? WHERE id_aluguel=?");
		for (Filme filme : aluguel.getFilmes()) {
			psf.setInt(1, filme.getIdFilme());
			psf.setInt(2, aluguel.getIdAluguel());
			psf.addBatch();
		}
		psf.executeBatch();
		psf.close();

		conn.commit();
	}

	@Override
	public void delete(Connection conn, Aluguel aluguel) throws Exception {

		PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM re_aluguel_filme WHERE id_aluguel=?;DELETE FROM en_aluguel WHERE id_aluguel=?");
		ps.setInt(1, aluguel.getIdAluguel());
		ps.setInt(2, aluguel.getIdAluguel());
		ps.execute();
		ps.close();

		conn.commit();
	}

	@Override
	public Aluguel find(Connection conn, Integer idAluguel) throws Exception {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM en_aluguel WHERE id_aluguel=?");
		ps.setInt(1, idAluguel);
		ResultSet rs = ps.executeQuery();

		Aluguel aluguel = null;

		while (rs.next()) {
			Integer idCliente = rs.getInt("id_cliente");
			java.util.Date data = new java.util.Date(rs.getDate("data_aluguel").getTime());
			float valor = rs.getFloat("valor");

			aluguel = new Aluguel(idAluguel, new FilmeDAOImpl().findBy(conn, idAluguel),
					new ClienteDAOImpl().find(conn, idCliente), data, valor);
		}
		rs.close();
		ps.close();

		return aluguel;
	}

	@Override
	public Collection<Aluguel> list(Connection conn) throws Exception {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM en_aluguel");
		ResultSet rs = ps.executeQuery();

		List<Aluguel> alugueis = new ArrayList<>();

		while (rs.next()) {
			alugueis.add(new Aluguel(rs.getInt("id_aluguel"), new FilmeDAOImpl().findBy(conn, rs.getInt("id_aluguel")),
					new ClienteDAOImpl().find(conn, rs.getInt("id_cliente")),
					new java.util.Date(rs.getDate("data_aluguel").getTime()), rs.getFloat("valor")));
		}
		return alugueis;
	}

}
