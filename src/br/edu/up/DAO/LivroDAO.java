

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.edu.up.Models.Categoria;
import br.edu.up.Models.Livro;

public class LivroDAO {

    //TODO CRUD para manipulação do arquivo CSV
    private String header = "";
    private String arquivo = "C:\\Java-Project\\lista-05\\Biblioteca\\src\\br\\edu\\up\\DAO\\Livros.csv";

    // READ
    public List<Livro> listarLivros(){
        List<Livro> listaDeLivros = new ArrayList<>();

        try{
            File arquivoLivros = new File(arquivo);

            Scanner sc = new Scanner(arquivoLivros);

            header = sc.nextLine();

            while (sc.hasNextLine()) {
                String linha = sc.nextLine();
                String[] dados = linha.split(";");

                String codigo = dados[0];

                if (codigo != null && !codigo.equals("")) {
                    String titulo = dados[1];
                    String isbn = dados[2];
                    int ano = Integer.parseInt(dados[3]);
                    Categoria categoria = Categoria.descricaoCategoria(dados[4]);

                    Livro livro = new Livro(codigo, titulo, isbn, ano, categoria);
                    listaDeLivros.add(livro);
                }    
            }
            sc.close();


        }catch(FileNotFoundException e){
            System.out.println("Arquivo não encontrado! " + e.getMessage());
        }
        return listaDeLivros;
    }

    //CREATE
    public boolean adicionarLivro(List<Livro> livros){

        try{

            FileWriter salvaArquivo = new FileWriter(arquivo);
            PrintWriter salvar = new PrintWriter(salvaArquivo);

            salvar.println(header);

            for (Livro livro : livros) {
                salvar.println(livro.toCsv());
            }
            salvar.close();

            return true;


        }catch(IOException e){
            System.out.println("Não foi possivel gravar o arquivo");
        }
        return false;
    }

    //UPDATE
    public boolean atualizarLivro(Livro livro){
        List<Livro> livros = listarLivros();
        boolean encontrado = false;

        for (int i = 0; i < livros.size(); i++) {
            Livro l = livros.get(i);  
            if (livro.getCodigo().equals(livro.getCodigo())) {
                livros.set(i, livro);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            return adicionarLivro(livros);
        }else{
            System.out.println("Codigo não encontrado: " + livro.getCodigo());
            return false;
        }
    }
    
}
