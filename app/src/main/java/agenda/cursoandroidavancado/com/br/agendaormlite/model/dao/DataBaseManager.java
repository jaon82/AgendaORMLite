package agenda.cursoandroidavancado.com.br.agendaormlite.model.dao;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by marciopalheta on 27/12/14.
 * Classe responsável pelo gerenciamento do BD
 */
public class DataBaseManager {
    //Referência para implementação do Singleton
    private static DataBaseManager instance;
    //Referência para criação e atualização de BD
    private DataBaseHelper databaseHelper;

    /**
     * Construtor para criação do Helper e criação do BD
     *
     * @param context
     */
    private DataBaseManager(Context context) {
        //Criação do objeto helper
        databaseHelper = OpenHelperManager.getHelper(context, DataBaseHelper.class);
        //Criação ou abertura do banco de dados
        databaseHelper.getWritableDatabase();
    }

    /**
     * Método de inicialização do Singleton
     *
     * @param context
     */
    public static void init(Context context) {
        if (instance == null) {
            instance = new DataBaseManager(context);
        }
    }

    /**
     * Método para encerramento do Helper
     */
    public static void close() {
        OpenHelperManager.releaseHelper();
        instance.setHelper(null);
    }

    private void setHelper(Object object) {
        databaseHelper = (DataBaseHelper) object;
    }

    public static DataBaseManager getInstance() {
        return instance;
    }

    public DataBaseHelper getHelper() {
        return databaseHelper;
    }
}
