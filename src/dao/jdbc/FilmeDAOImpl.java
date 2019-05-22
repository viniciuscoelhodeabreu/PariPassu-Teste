package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import dao.FilmeDAO;
import entidades.Filme;

public class FilmeDAOImpl implements FilmeDAO {

	@Override
	public void insert(Connection conn, Filme filme) throws Exception {
		PreparedStatement ps = conn
				.prepareStatement("INSERT INTO en_filme(id_filme,data_lancamento,nome,descricao) VALUES(?,?,?,?)");

		Integer idFilme = this.getNextId(conn);

		ps.setInt(1, idFilme);
		ps.setDate(2, new java.sql.Date(filme.getDataLancamento().getTime()));
		ps.setString(3, filme.getNome());
		ps.setString(4, filme.getDescricao());

		ps.executeUpdate();
		ps.close();
		conn.commit();

		filme.setIdFilme(idFilme);
	}

	@Override
	public Integer getNextId(Connection conn) throws Exception {
		PreparedStatement ps = conn.prepareStatement("SELECT nextval('seq_en_filme')");
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt(1);
	}

	@Override
	public void edit(Connection conn, Filme filme) throws Exception {
		PreparedStatement ps = conn
				.prepareStatement("UPDATE en_filme SET data_lancamento=?, nome=?, descricao=? WHERE id_filme=?");
		ps.setDate(1, new java.sql.Date(filme.getDataLancamento().getTime()));
		ps.setString(2, filme.getNome());
		ps.setString(3, filme.getDescricao());
		ps.setInt(4, filme.getIdFilme());
		ps.executeUpdate();
		ps.close();

		conn.commit();
	}

	@Override
	public void delete(Connection conn, Integer idFilme) throws Exception {
		PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM re_aluguel_filme WHERE id_filme=?;" + "DELETE FROM en_filme WHERE id_filme=?;");

		ps.setInt(1, idFilme);
		ps.setInt(2, idFilme);
		ps.execute();
		ps.close();

		conn.commit();
	}

	@Override
	public Filme find(Connection conn, Integer idFilme) throws Exception {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM en_filme WHERE id_filme=?");
		ps.setInt(1, idFilme);
		ResultSet rs = ps.executeQuery();

		Filme filme = null;

		while (rs.next()) {
			filme = new Filme(rs.getInt("id_filme"), new Date(rs.getDate("data_lancamento").getTime()),
					rs.getString("nome"), rs.getString("descricao"));
		}
		rs.close();
		ps.close();

		return filme;
	}

	@Override
	public Collection<Filme> list(Connection conn) throws Exception {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM en_filme");
		ResultSet rs = ps.executeQuery();

		List<Filme> filmes = new ArrayList<>();

		while (rs.next()) {
			filmes.add(new Filme(rs.getInt("id_filme"), new Date(rs.getDate("data_lancamento").getTime()),
					rs.getString("nome"), rs.getString("descricao")));
		}
		rs.close();
		ps.close();
		return filmes;
	}

	@Override
	public List<Filme> findBy(Connection conn, Integer idAluguel) throws Exception {
		PreparedStatement ps = conn.prepareStatement(
				"SELECT DISTINCT en.* FROM en_filme en JOIN re_aluguel_filme re ON re.id_filme=en.id_filme JOIN en_aluguel ea ON ea.id_aluguel=re.id_aluguel WHERE ea.id_aluguel=?");
		ps.setInt(1, idAluguel);
		ResultSet rs = ps.executeQuery();

		List<Filme> filmes = new ArrayList<>();

		while (rs.next()) {
			filmes.add(new Filme(rs.getInt("id_filme"), new Date(rs.getDate("data_lancamento").getTime()),
					rs.getString("nome"), rs.getString("descricao")));
		}
		return filmes;
	}

}
