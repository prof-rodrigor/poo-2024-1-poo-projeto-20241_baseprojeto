package br.ufpb.dcx.rodrigor.projetos.login.service;

import br.ufpb.dcx.rodrigor.projetos.AbstractService;
import br.ufpb.dcx.rodrigor.projetos.db.MongoDBConnector;
import br.ufpb.dcx.rodrigor.projetos.login.exceptions.InvalidEmailException;
import br.ufpb.dcx.rodrigor.projetos.login.exceptions.InvalidUsernameException;
import br.ufpb.dcx.rodrigor.projetos.login.model.Usuario;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class UsuarioService extends AbstractService {
    private static final Logger logger = LogManager.getLogger();

    private final MongoCollection<Document> repository;

    public UsuarioService(MongoDBConnector mongoDBConnector) {
        super(mongoDBConnector);
        MongoDatabase database = mongoDBConnector.getDatabase("usuarios");
        this.repository = database.getCollection("usuarios");
    }

    // TODO
    public void cadastrarNovoUsuario(Usuario usuario) throws InvalidUsernameException, InvalidEmailException {
        Document doc = userToVO(usuario);

        if (getUsuario(usuario.getUsername()) != null) {
            throw new InvalidUsernameException("Esse usuário já existe");
        }

        if (getUsuarioByEmail(usuario.getEmail()) != null) {
            throw new InvalidEmailException("Esse email já foi cadastrado");
        }

        repository.insertOne(doc);
    }

    public void removerUsuario(String titulo){
        repository.deleteOne(eq("titulo", new ObjectId(titulo)));
    }

    public List<Usuario> listarUsuarios(){
        List<Usuario> usuarios = new LinkedList<>();
        for (Document doc : repository.find()){
            usuarios.add(voToUser(doc));
        }
        return usuarios;
    }

    public Usuario getUsuario(String username) {
        Bson filter = eq("username", username);
        Document doc = repository.find(filter).first();
        if (doc == null) {
            return null;
        }
        return voToUser(doc);
    }

    public Usuario getUsuarioByEmail(String email) {
        Bson filter = eq("email", email);
        Document doc = repository.find(filter).first();
        if (doc == null) {
            return null;
        }
        return voToUser(doc);
    }

    public Usuario voToUser(Document doc) {
        Usuario usuario = new Usuario();
        usuario.setUsername(doc.getString("username"));
        usuario.setEmail(doc.getString("email"));
        usuario.setSenha(doc.getString("senha"));
        return usuario;
    }
    public Document userToVO(Usuario usuario){
        Document vo = new Document();
        vo.put("username", usuario.getUsername());
        vo.put("email", usuario.getEmail());
        vo.put("senha", usuario.getSenha());
        return vo;
    }
    public void atualizarUsuario(Usuario usuario) {
        // Filtrar o usuário pelo username, que é único
        Bson filter = eq("username", usuario.getUsername());

        // Criar o documento atualizado com os novos valores
        Document updatedDocument = new Document();
        updatedDocument.put("username", usuario.getUsername());
        updatedDocument.put("email", usuario.getEmail());
        updatedDocument.put("senha", usuario.getSenha());

        // Criar a atualização no formato necessário para o MongoDB
        Bson updateOperation = new Document("$set", updatedDocument);

        // Executar a atualização no banco de dados e verificar o resultado
        var result = repository.updateOne(filter, updateOperation);

        if (result.getMatchedCount() > 0) {
            logger.info("Usuário '{}' atualizado com sucesso", usuario.getUsername());
        } else {
            logger.warn("Nenhum usuário encontrado para o username '{}'", usuario.getUsername());
        }
    }




}



