package agenda.cursoandroidavancado.com.br.agendaormlite.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import agenda.cursoandroidavancado.com.br.agendaormlite.model.bean.Contato;
import agenda.cursoandroidavancado.com.br.agendaormlite.model.bean.IEntidade;

/**
 * Created by marciopalheta on 25/12/14.
 * Classe helper para criação e atualização do BD
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
    //Nome do arquivo de banco de dados do SQLLite
    private static final String DATABASE_NAME = "agenda.db";
    // Versão atual do banco de dados
    private static final int DATABASE_VERSION = 1;

    // TAG para marcação no LogCat
    private final String TAG = DataBaseHelper.class.getSimpleName();

    // Mapa para controle do número de DAOs criados no projeto
    private Map<Class, Object> daos = new HashMap<Class, Object>();

    // Contexto da aplicação
    private Context context;

    public DataBaseHelper(Context context) {
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
            Log.i(TAG, "onCreate()");
            TableUtils.createTable(source, Contato.class);
        } catch (SQLException ex) {
            Log.e(TAG, "onCreate(): Falha ao criar tabelas", ex);
            throw new RuntimeException(ex);
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
            Log.i(TAG, "onUpgrade()");
            //Conexao, tabela e true para ignorar erros
            TableUtils.dropTable(source, Contato.class, true);
            onCreate(database, source);
        } catch (SQLException ex) {
            Log.e(TAG, "onUpgrade(): Falha na atualizacao", ex);
            throw new RuntimeException(ex);
        }
    }

    public <T> Dao<T, Object> getDAO(Class entidade) throws SQLException {
        Dao<IEntidade, Object> dao = null;
        //Consulta ao mapa de DAOs
        if (daos.get(entidade) == null) {
            dao = super.getDao(entidade);
            //Atualização do mapa de DAOs
            daos.put(entidade, dao);
        }
        return (Dao<T, Object>) daos.get(entidade);
    }

    @Override
    /**
     * Metodo para encerrar conexão com o BD
     */
    public void close() {
        super.close();
    }

    public Context getContext() {
        return context;
    }

}
