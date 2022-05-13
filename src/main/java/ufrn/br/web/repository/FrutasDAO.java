package ufrn.br.web.repository;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ufrn.br.web.model.Frutas;

public class FrutasDAO {
    private final static String CADASTRO = "insert into tb_frutas ( nome, descricao, cor, quantidade, preco) values (?,?,?,?,?)";
    private final static String LISTAR =  "SELECT * FROM tb_frutas";
    private final static String VERIFICACAO = "select * from frutas where id=?";
    private final static String ID = "select * from tb_frutas where id=?";

    public static void cadastrar(Frutas frutas){
        try{
            Conexao.getConnection();
            PreparedStatement instrucao = Conexao.getConnection().prepareStatement(CADASTRO);
            instrucao.setString(1, frutas.getNome());
            instrucao.setString(2, frutas.getDescricao());
            instrucao.setString(3, frutas.getCor());
            instrucao.setInt(4, frutas.getQuantidade());
            instrucao.setFloat(5, frutas.getPreco());
  
            instrucao.execute();
        } catch (Exception e){
            System.out.println("Erro no cadastro: " + e.getMessage());
        }
    }

    public List<Frutas> listarFrutas() {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Frutas> listaDeFrutas = new ArrayList<>();
        try {
            connection = Conexao.getConnection();
            stmt = connection.prepareStatement(LISTAR);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Frutas f = new Frutas();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setDescricao(rs.getString("descricao"));
                f.setCor(rs.getString("cor"));
                f.setQuantidade(rs.getInt("quantidade"));
                f.setPreco(rs.getFloat("preco"));

                listaDeFrutas.add(f);
            }
            connection.close();
        } catch (SQLException | URISyntaxException ignored) {
        }

        return listaDeFrutas;
    }

    public static Frutas verificacao(Integer id){
        Frutas frutas = null;
        try{
            Conexao.getConnection();
            PreparedStatement instrucao = Conexao.getConnection().prepareStatement(VERIFICACAO);
            instrucao.setInt(1, id);
  
            ResultSet rs = instrucao.executeQuery();

            if(rs.next()){
                frutas = new Frutas(rs.getInt("id"));
            }

            instrucao.close();

        } catch (Exception e){
            System.out.println("Erro na verificação: " + e.getMessage());
        }

        return frutas;
    }


    public List<Frutas> buscarFrutaPorID(int id) {

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Frutas> listaDeProdutos = new ArrayList<>();

        try {
            connection = Conexao.getConnection();
            stmt = connection.prepareStatement(ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Frutas p = new Frutas();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getFloat("preco"));
                p.setQuantidade(rs.getInt("quantidade"));
                listaDeProdutos.add(p);
            }
            connection.close();
        } catch (SQLException | URISyntaxException ignored) {
        }
        return listaDeProdutos;
    }
}
