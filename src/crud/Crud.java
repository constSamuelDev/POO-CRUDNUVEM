
package crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Crud {

    public static void main(String[] args) {

        String url = "jdbc:mysql://root:fGgjICYJPtyTPFvhaheyBfEeZWpMtPWO@trolley.proxy.rlwy.net:27872/railway";
        String usuariobanco = "mysql.railway.internal";
        String bancosenha = "fGgjICYJPtyTPFvhaheyBfEeZWpMtPWO";

        Scanner sc = new Scanner(System.in);

        try {

            Connection conexao = DriverManager.getConnection(url, usuariobanco, bancosenha);

            int opcao = -1;

            while (opcao != 0) {

                System.out.println("\n1 - Inserir | 2 - Listar | 3 - Atualizar | 4 - Deletar | 0 - Sair");
                opcao = sc.nextInt();

                switch (opcao) {

                    case 1:

                        System.out.println("Codigo do produto: ");
                        int codigo = sc.nextInt();

                        System.out.println("Nome do produto: ");
                        String nome = sc.next();

                        System.out.println("Preco do produto: ");
                        float preco = sc.nextFloat();

                        String sql = "INSERT INTO PRODUTO(CODIGO, NOME, PRECO) VALUES(?,?,?)";

                        PreparedStatement stmt = conexao.prepareStatement(sql);

                        stmt.setInt(1, codigo);
                        stmt.setString(2, nome);
                        stmt.setFloat(3, preco);

                        stmt.executeUpdate();

                        System.out.println("Inserido com sucesso");

                        stmt.close();

                        break;

                    case 2:

                        String sqlLista = "SELECT * FROM PRODUTO";

                        PreparedStatement stmtLista = conexao.prepareStatement(sqlLista);

                        ResultSet resultado = stmtLista.executeQuery();

                        while (resultado.next()) {

                            System.out.println("Codigo: " + resultado.getInt("CODIGO"));
                            System.out.println("Nome: " + resultado.getString("NOME"));
                            System.out.println("Preco: " + resultado.getFloat("PRECO"));
                            System.out.println("--------------------------");

                        }

                        resultado.close();
                        stmtLista.close();

                        break;

                    case 3:

                        System.out.println("Codigo do produto que deseja atualizar: ");
                        int codigoAtualizar = sc.nextInt();

                        System.out.println("Novo nome do produto: ");
                        String novoNome = sc.next();

                        System.out.println("Novo preco do produto: ");
                        float novoPreco = sc.nextFloat();

                        String sqlAtualizar = "UPDATE PRODUTO SET NOME = ?, PRECO = ? WHERE CODIGO = ?";

                        PreparedStatement stmtAtualizar = conexao.prepareStatement(sqlAtualizar);

                        stmtAtualizar.setString(1, novoNome);
                        stmtAtualizar.setFloat(2, novoPreco);
                        stmtAtualizar.setInt(3, codigoAtualizar);

                        int linhasAtualizadas = stmtAtualizar.executeUpdate();

                        if (linhasAtualizadas > 0) {
                            System.out.println("Produto atualizado com sucesso");
                        } else {
                            System.out.println("Produto nao encontrado");
                        }

                        stmtAtualizar.close();

                        break;

                    case 4:

                        System.out.println("Codigo do produto que deseja deletar: ");
                        int codigoDeletar = sc.nextInt();

                        String sqlDeletar = "DELETE FROM PRODUTO WHERE CODIGO = ?";

                        PreparedStatement stmtDeletar = conexao.prepareStatement(sqlDeletar);

                        stmtDeletar.setInt(1, codigoDeletar);

                        int linhasDeletadas = stmtDeletar.executeUpdate();

                        if (linhasDeletadas > 0) {
                            System.out.println("Produto deletado com sucesso");
                        } else {
                            System.out.println("Produto nao encontrado");
                        }

                        stmtDeletar.close();

                        break;

                    case 0:

                        System.out.println("Programa encerrado");

                        break;

                    default:

                        System.out.println("Opcao invalida");

                        break;
                }

            }

            conexao.close();

        } catch (Exception e) {

            System.out.println("Erro no db: " + e.getMessage());

        }

    }

}
