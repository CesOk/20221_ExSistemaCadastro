import java.sql.Connection;
import java.sql.PreparedStatement;

public class Pessoa {
    private int codigo;
    private String nome;
    private String fone;
    private String email;

    public void inserir() throws Exception{
        //1. Definir o comando SQL
        String sql = "INSERT INTO tb_pessoa (nome, fone, email) VALUES (?, ?, ?)";
        //2. Abrir uma conexão com o MySQL Server
        ConnectionFactory factory = new ConnectionFactory();
        Connection conexao = factory.getConnection();
        //3. Preparar o comando (solicitar ao MySQL Server que compile o comando SQL previamente)
        var ps = conexao.prepareStatement(sql);
        //4. Substituir os eventuais placeholders
        ps.setString(1, nome);
        ps.setString(2, fone);
        ps.setString(3, email);
        //5. Executar o comando
        ps.execute();
        //6. Fechar os recursos (conexão e o comando preparado)
        ps.close();
        conexao.close();
    }

    public void atualizar() throws Exception{
        //1. Especificar o comando SQL (UPDATE)
        String sql = "UPDATE tb_pessoa SET nome = ?, fone = ?, email = ? WHERE codigo = ?";
        //2. Abrir uma conexão com o MYSQL
        ConnectionFactory factory = new ConnectionFactory();
        //try-with-resources (desde a versão 7 do Java SE)
        try(Connection conexao = factory.getConnection();
                //3. Preparar o comando
                PreparedStatement ps = conexao.prepareStatement(sql)){

            Pessoa.listarAtualizar();
            
            //4. Substituir os placeholders
            ps.setString(1, nome);
            ps.setString(2, fone);
            ps.setString(3, email);
            ps.setInt(4, codigo);
            //5. Executar o comando
            ps.execute();
            //6. Fechar os recursos (Já feito pelo try-with-resources)
        }
    }

    //1.1. Mostre a lista de pessoas existentes, incluindo o seu código
    public static void listarPessoa() throws Exception{
        String sql = "SELECT * from tb_pessoa";

        try(
            Connection conexao = new ConnectionFactory().getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();
        ){
            while(rs.next()){
                rs.next();
                int codigo = rs.getInt("codigo");
                String nome = rs.getString("nome");

                System.out.printf(
                "código: %d, nome: %s\n",
                    codigo, nome
                );
            }
        }
    }

    /*1.2. A cada variável pedida (nome, fone e e-mail) mostrar para o usuário qual é o 
    valor existente atualmente*/
    public static void listarAtualizar() throws Exception{
        String sql = "SELECT * from tb_pessoa";

        try(
            Connection conexao = new ConnectionFactory().getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();
        ){
            String nome = rs.getString("nome");
            String fone = rs.getString("fone");
            String email = rs.getString("email");

            System.out.printf(
            "nome: %s, fone: %s, e-mail: %s\n",
                nome, fone, email
            );
        }
    }

    public void apagar() throws Exception{
        String sql = "DELETE tb_pessoa WHERE codigo = ?";

        try(
            Connection conexao = new ConnectionFactory().getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);
        ){
            ps.setInt(1, codigo);

            ps.execute();
        }
    }

    public static void listar() throws Exception{
        String sql = "SELECT * from tb_pessoa";

        try(
            Connection conexao = new ConnectionFactory().getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();
        ){
            while(rs.next()){
                rs.next();
                int codigo = rs.getInt("codigo");
                String nome = rs.getString("nome");
                String fone = rs.getString("fone");
                String email = rs.getString("email");

                System.out.printf(
                "código: %d, nome: %s, fone: %s, e-mail: %s\n",
                    codigo, nome, fone, email
                );
            }
        }
    }

    public Pessoa (String nome, String fone, String email){
        this(0, nome, fone, email);
    }

    public Pessoa(int codigo, String nome, String fone, String email){
        setCodigo(codigo);
        setNome(nome);
        setFone(fone);
        setEmail(email);
    }

    public Pessoa(int codigo, String nome){
        getCodigo();
        getNome();
    }

    Pessoa(int codigo){
        this(codigo, null, null, null);
    }

    public int getCodigo() {
        return codigo;
    }

    public String getEmail() {
        return email;
    }
    public String getFone() {
        return fone;
    }

    public String getNome() {
        return nome;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
