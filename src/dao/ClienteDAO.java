package dao;

import entidades.Cliente;

import java.sql.Connection;
import java.util.Collection;

public interface ClienteDAO {

    void insert(Connection conn, Cliente cliente) throws Exception;

    Integer getNextId(Connection conn) throws Exception;

    void edit(Connection conn, Cliente cliente) throws Exception;

    void delete(Connection conn, Integer idCliente) throws Exception;

    Cliente find(Connection conn, Integer idCliente) throws Exception;

    Collection<Cliente> list(Connection conn) throws Exception;

}