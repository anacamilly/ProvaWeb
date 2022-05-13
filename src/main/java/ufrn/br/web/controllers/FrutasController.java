package ufrn.br.web.controllers;

import java.io.IOException;
import java.sql.PreparedStatement;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufrn.br.web.model.Frutas;
import ufrn.br.web.repository.Conexao;
import ufrn.br.web.repository.FrutasDAO;

@Controller
public class FrutasController {

    /////////////////////////////////////////// QUESTÃO 1 ///////////////////////////////////////////
    /*Crie um projeto com Spring Initializr incluindo Spring Boot Dev Tools, Spring Web, Spring JDBC API 
    e PostgreSQL. Crie a classe do modelo conforme o tema escolhido, lembre-se que você precisa 
    adicionar pelo menos 6 atributos (sendo um deles o ID). (0,0 ponto)*/



    /////////////////////////////////////////// QUESTÃO 2 ///////////////////////////////////////////
    /* Crie uma rota chamada “/config" que cria no banco de dados a tabela para receber os dados do 
    tema escolhido e já adiciona 5 linhas com dados na tabela. Se o procedimento funcionar retorne um 
    texto “OK” para o cliente. */
    @GetMapping(value= "/config")
    private void tableBD(HttpServletRequest request, HttpServletResponse response) throws IOException{
        var writer = response.getWriter();
        String CREATEFRUTAS = "CREATE TABLE tb_frutas( id serial, nome varchar(266), descricao varchar(50), cor varchar(50), quantidade integer, preco float);";
        String INSERTFRUTAS = "INSERT INTO tb_frutas( nome, descricao , cor, quantidade, preco) VALUES (?, ?, ?, ?, ?)";
        
        try{
            Conexao.getConnection();
            PreparedStatement create = Conexao.getConnection().prepareStatement(CREATEFRUTAS);
            create.execute();

            PreparedStatement insertTable1 = Conexao.getConnection().prepareStatement(INSERTFRUTAS);
            insertTable1.setString(1, "Uva");
            insertTable1.setString(2, "Uva");
            insertTable1.setString(3, "verde");
            insertTable1.setInt(4, 20);
            insertTable1.setFloat(5, 5);
            insertTable1.execute();

            PreparedStatement insertTable2 = Conexao.getConnection().prepareStatement(INSERTFRUTAS);
            insertTable2.setString(1, "Morango");
            insertTable2.setString(2, "Morango");
            insertTable2.setString(3, "vermelho");
            insertTable2.setInt(4, 10);
            insertTable2.setFloat(5, 12);
            insertTable2.execute();
            
            PreparedStatement insertTable3 = Conexao.getConnection().prepareStatement(INSERTFRUTAS);
            insertTable3.setString(1, "Cereja");
            insertTable3.setString(2, "Cereja");
            insertTable3.setString(3, "vermelho");
            insertTable3.setInt(4, 10);
            insertTable3.setFloat(5, 17);
            insertTable3.execute();

            PreparedStatement insertTable4 = Conexao.getConnection().prepareStatement(INSERTFRUTAS);
            insertTable4.setString(1, "Maça");
            insertTable4.setString(2, "Maça");
            insertTable4.setString(3, "vermelho");
            insertTable4.setInt(4, 5);
            insertTable4.setFloat(5, 6);
            insertTable4.execute();

            PreparedStatement insertTable5 = Conexao.getConnection().prepareStatement(INSERTFRUTAS);
            insertTable5.setString(1, "Limão");
            insertTable5.setString(2, "Limão");
            insertTable5.setString(3, "verde");
            insertTable5.setInt(4, 10);
            insertTable5.setFloat(5, 3);
            insertTable5.execute();

            writer.println("OK");
        } catch (Exception e){
            writer.println("Erro: " + e.getMessage());
        }
    }

    //////////////////////////////////////////// QUESTÃO 3 ////////////////////////////////////////////////
    /*Crie um arquivo chamado index.html que deverá conter 2 links, um para área de cliente (“/cliente”) e 
    outro para área de administração (“/admin”). (0,5 ponto)*/
    /*   static/index.html   */



    //////////////////////////////////////////// QUESTÃO 4 ////////////////////////////////////////////////
    /* Implemente a rota de (“/cliente”) para, a partir de uma solicitação do tipo GET, gerar uma resposta 
    contendo no corpo um HTML que contém uma tabela (html) de todos os itens (linhas) que estão 
    presentes no banco de dados. Para cada item listado adicione um link para a rota “/adicionarCarrinho” 
    passando como parâmetro para tal rota o ID do item escolhido. Por fim, adicione na página gerada pela 
    rota “/cliente” um link para a rota “/verCarrinho”. (2,0 pontos)*/
    @RequestMapping(value = "/clientes", method = RequestMethod.GET)
    private void clientes(HttpServletRequest request, HttpServletResponse response) throws IOException{

        var writer = response.getWriter();

        var fDAO = new FrutasDAO();
        var listaFrutas = fDAO.listarFrutas();
        writer.println("<html><body>");
        writer.println("<h1>Lista de Frutas</h1>");
        writer.println("<table border=1>");
        for (var f : listaFrutas){
            writer.println("<tr>");
            writer.println("<td>");
            writer.println("<p>ID:</p>");
            writer.println(f.getId());
            writer.println("<td>");
            writer.println("<p>Nome:</p>");
            writer.println(f.getNome());
            writer.println("</td>");
            writer.println("<td>");
            writer.println("<p>Descrição:</p>");
            writer.println(f.getDescricao());
            writer.println("</td>");
            writer.println("<td>");
            writer.println("<p>Cor:</p>");
            writer.println(f.getCor());
            writer.println("</td>");
            writer.println("<td>");
            writer.println("<p>Preço:</p>");
            writer.println(f.getPreco());
            writer.println("</td>");
            writer.println("<td>");
            writer.println("<p>Quantidade:</p>");
            writer.println(f.getQuantidade());
            writer.println("</td>");
            writer.println("<td>");
            writer.println("<a href='/adicionarCarrinho?id="+f.getId()+"'>Adicionar</a>");
            writer.println("</td>");
            writer.println("</tr>");
        }
        writer.println("</table>");
        writer.println("<br/>");
        writer.println("<a href='/verCarrinho'>Ver Carrinho</a>");
        writer.println("</body></html>");
    
    }


    //////////////////////////////////////////// QUESTÃO 5 ////////////////////////////////////////////////
    /*Implemente a rota de (“/admin”) para, a partir de uma solicitação do tipo GET, gerar uma resposta 
    contendo no corpo um formulário HTML para cadastro de um item do seu tema. O formulário deve 
    enviar os dados da solicitação através do método POST para a rota “/cadastra”. Adicione um cookie na 
    resposta chamado “visita” com a data e hora do acesso ao site. Esse cookie deve ser permanente e 
    durar 24hs.(1,5 pontos)*/
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    private void admin(HttpServletRequest request, HttpServletResponse response) throws IOException{

        var writer = response.getWriter();

        writer.println("<html><body>");
        writer.println("<h1>Cadastro de Frutas</h1>");
        writer.println("<form method='POST' action='/cadastra'>");
        writer.println(" Nome: <input type='text' name='nome'/> </br>");
        writer.println("Descricao: <input type='text' name='descricao'/></br>");
        writer.println("Cor: <input type='text' name='cor'/></br>");
        writer.println("Quantidade: <input type='text' name='quantidade'/></br>");
        writer.println("Preço: <input type='text' name='preco'/></br>");
        writer.println("<button type='submit'>Cadastrar</button>");
        writer.println("</form>");
      
    }

    /////////////////////////////////////////// QUESTÃO 6 ///////////////////////////////////////////
    /*Implemente a rota de (“/cadastra”) que deve receber dados através de método POST e cadastrar um 
    novo item no banco de dados. Ao final do processo, a solicitação deve ser redirecionada para “/admin”.*/
    @RequestMapping(value = "/cadastra")
    private void cadastra(HttpServletRequest request, HttpServletResponse response) throws IOException{
        var writer = response.getWriter();
        
        Cookie cookie = new Cookie("visita","cookie-value");
        cookie.setMaxAge(60*60*24); //24 horas
        response.addCookie(cookie);
        writer.println("Cookies created");


        var nome = request.getParameter("nome");
        var descricao = request.getParameter("descricao");
        var cor = request.getParameter("cor");
        var quantidade = request.getParameter("quantidade");
        var preco = request.getParameter("preco");

        Frutas frutas = new Frutas();
        frutas.setNome(nome);
        frutas.setDescricao(descricao);
        frutas.setCor(cor);
        frutas.setQuantidade(Integer.parseInt(quantidade));
        frutas.setPreco(Float.parseFloat(preco));


        if(FrutasDAO.verificacao(frutas.getId())==null){
            FrutasDAO.cadastrar(frutas);
            response.sendRedirect("/admin");
        }else{
            writer.println("ERROR: Fruta já cadastrada");
        }

    }

    //////////////////////////////////////////// QUESTÃO 9 ////////////////////////////////////////////////
    /*Implemente a rota de (“/finalizarCompra”) que ao receber uma solicitação do tipo GET invalida a 
    Sessão existente e redireciona a resposta para “index.html”. (0,5 pontos)*/
    @RequestMapping(value = "/finalizarCompra", method = RequestMethod.GET)
    private void finalizarCompra(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        request.getSession().invalidate();
        response.sendRedirect("/index.html");
        
    }

}
