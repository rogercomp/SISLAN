package sislan.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class CriarBancoDeDados {
	
	private static String sql_criaBanco = "create database db_contexto";
    private static String sql_criaTabelaPessoa = "CREATE TABLE tb_pessoa(nome varchar(100), endereco varchar(255), usuario varchar(20), senha char(8), email varchar(50), id int(11)) ENGINE=InnoDB DEFAULT CHARSET=utf8;" + "/ ALTER TABLE tb_pessoa ADD PRIMARY KEY (id);"
                                               + "/ ALTER TABLE tb_pessoa MODIFY COLUMN id INT AUTO_INCREMENT;" + "/ COMMIT;";

    public static void criaBase(String url, String usuario, String senha) {
            Connection con;
            Statement stmt;
            try {
                    String urlSemNomeDaBase = url.replace("/db_contexto", "");
                    con = DriverManager.getConnection(urlSemNomeDaBase, usuario, senha);
                    stmt = con.createStatement();
                    stmt.execute(sql_criaBanco);
                    stmt.close();
                    con.close();
            } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
            }
    }

    public static void criaTabelaPessoa(String url, String usuario, String senha) {
            Connection con;
            Statement stmt;
            String comandos[] = sql_criaTabelaPessoa.split("/");
            try {
                    con = DriverManager.getConnection(url, usuario, senha);
                    stmt = con.createStatement();
                    for (String sql : comandos) {
                            stmt.execute(sql);
                    }
                    stmt.close();
                    con.close();
            } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
            }
    }

    public static void main(String[] args) {
            try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                    e.printStackTrace();
            }
            CriarBancoDeDados.criaBase("jdbc:mysql://localhost:3306/db_contexto?useTimezone=true&serverTimezone=UTC", "root", "root");
            CriarBancoDeDados.criaTabelaPessoa("jdbc:mysql://localhost:3306/db_contexto?useTimezone=true&serverTimezone=UTC", "root", "root");
    }

}
