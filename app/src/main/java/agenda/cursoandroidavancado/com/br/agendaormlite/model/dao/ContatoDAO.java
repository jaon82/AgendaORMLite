package agenda.cursoandroidavancado.com.br.agendaormlite.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import agenda.cursoandroidavancado.com.br.agendaormlite.model.bean.Contato;

/**
 * Created by marciopalheta on 25/12/14.
 * Classe usada para persistência da tabela de Contatos
 */
public class ContatoDAO extends OrmLiteSqliteOpenHelper {
    //Nome do arquivo de banco de dados do SQLLite
    private static final String DATABASE_NAME = "agenda.db";
    // Versão atual do banco de dados
    private static final int DATABASE_VERSION = 1;

    // Atributo utilizado para a persistencia de Contatos
    private Dao<Contato, Long> dao = null;

    public ContatoDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Metodo chamado quando a base de dados eh criada
     *
     * @param database
     * @param source
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource source) {
        try {
            Log.i(ContatoDAO.class.getSimpleName(), "onCreate()");
            TableUtils.createTable(source, Contato.class);
            Contato contato = new Contato();
            contato.setNome("Administrador");
            contato.setEmail("teste@androidavancado.com.br");
            contato.setTelefone("9900-8877");
            cadastrar(contato);
        } catch (SQLException ex) {
            Log.e(ContatoDAO.class.getSimpleName(),
                    "onCreate(): Falha ao criar tabelas", ex);
        }
    }

    /**
     * Metodo usado para atualiza da estrutura das tabelas
     *
     * @param database
     * @param source
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource source, int oldVersion,
                          int newVersion) {
        try {
            Log.i(ContatoDAO.class.getSimpleName(), "onUpgrade()");
            //Conexao, tabela e true para ignorar erros
            TableUtils.dropTable(source, Contato.class, true);
            onCreate(database, source);
        } catch (SQLException ex) {
            Log.e(ContatoDAO.class.getSimpleName(), "onUpgrade(): Falha na atualizacao", ex);
        }
    }

    /**
     * Metodo para criacao ou retorno de DAO
     *
     * @return
     */
    public Dao<Contato, Long> getDao() {
        if (dao == null) {
            try {
                dao = getDao(Contato.class);
            } catch (SQLException e) {
                Log.e(ContatoDAO.class.getSimpleName(),
                        "getDao(): Falha ao criar DAO", e);
            }
        }
        return dao;
    }

    @Override
    /**
     * Metodo para encerrar conexão com o BD
     */
    public void close() {
        super.close();
        dao = null;
    }

    /**
     * Metodo para cadastro de contatos
     *
     * @param contato
     * @throws SQLException
     */
    public void cadastrar(Contato contato) throws SQLException {
        getDao().create(contato);
    }

    /**
     * Metodo para exclusao de contatos
     *
     * @param contato
     * @throws SQLException
     */
    public void excluir(Contato contato) throws SQLException {
        getDao().delete(contato);
    }

    /**
     * Metodo para alteracao dos dados do contato
     *
     * @param contato
     * @throws SQLException
     */
    public void alterar(Contato contato) throws SQLException {
        getDao().update(contato);
    }

    /**
     * Metodo para listagem de contatos
     *
     * @return
     * @throws SQLException
     */
    public List<Contato> listar() throws SQLException {
        return getDao().queryForAll();
    }
}
