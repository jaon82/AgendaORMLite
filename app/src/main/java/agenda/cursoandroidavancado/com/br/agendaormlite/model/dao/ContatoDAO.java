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

    //Atributo que devolve uma instância de ContatoDAO
    private static final String DATABASE_NAME="agenda.db";

    private static final int DATABASE_VERSION=1;

    private Dao<Contato,Long> dao=null;

    //Construtor que garante que não sejam criados
    //outras instâncias de ContatoDAO
    public ContatoDAO(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database,ConnectionSource connectionSource){
        try{
            Log.i(ContatoDAO.class.getSimpleName(),"onCreate()");
            TableUtils.createTable(connectionSource,Contato.class);
            Contato contato=new Contato();
            contato.setNome("Jean");
            contato.setEmail("jaon82@gmail.com");
            contato.setTelefone("9313-0223");
            cadastrar(contato);
        }catch (SQLException ex){
            Log.e(ContatoDAO.class.getSimpleName(),"onCreate(): Falha ao criar tabelas",ex);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,ConnectionSource connectionSource,int oldVersion,int newVersion){
        try{
            Log.i(ContatoDAO.class.getSimpleName(),"onUpgrade()");
            TableUtils.dropTable(connectionSource, Contato.class, true);
            onCreate(database,connectionSource);
        }catch (SQLException ex){
            Log.e(ContatoDAO.class.getSimpleName(),"onUpgrade(): Falha na atualização",ex);
        }
    }

    /**
     * Implementação do padrão de projeto Singleton
     * @return instância de ContatoDAO
     */
    /*
    public static ContatoDAO getInstance() {
        if (instance == null) {
            instance = new ContatoDAO();
        }
        return instance;
    }*/
    public Dao<Contato,Long> getDao(){
        if(dao==null){
            try{
                dao=getDao(Contato.class);
            }catch (SQLException e){
                Log.e(ContatoDAO.class.getSimpleName(),"getDao(): Falha ao criar DAO",e);
            }
        }
        return dao;
    }

    @Override
    public void close(){
        super.close();
        dao=null;
    }

    public void cadastrar(Contato contato) throws SQLException{
        getDao().create(contato);
    }

    public void excluir(Contato contato)throws SQLException{
        getDao().delete(contato);
    }

    public void alterar(Contato contato) throws SQLException{
        getDao().update(contato);
    }

    public List<Contato> listar() throws SQLException{
        return getDao().queryForAll();
    }

}
