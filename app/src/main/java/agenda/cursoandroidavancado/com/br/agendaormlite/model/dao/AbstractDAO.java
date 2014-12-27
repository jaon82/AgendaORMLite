package agenda.cursoandroidavancado.com.br.agendaormlite.model.dao;

import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import agenda.cursoandroidavancado.com.br.agendaormlite.model.bean.IEntidade;

/**
 * Created by marciopalheta on 27/12/14.
 * Classe que possui métodos comuns as DAOs
 */
public abstract class AbstractDAO<T extends IEntidade> {
    private static final String TAG = AbstractDAO.class.getSimpleName();

    protected DataBaseHelper getHelper() {
        return DataBaseManager.getInstance().getHelper();
    }

    protected Dao<T, Object> getConnection() {
        return (Dao<T, Object>) getHelper().getRuntimeExceptionDao(getEntityClass());
    }

    private Class getEntityClass() {
        ParameterizedType t = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class) t.getActualTypeArguments()[0];
    }

    /**
     * Método para Cadastrar ou Alterar entidades
     *
     * @param entidade
     * @return
     */
    public boolean createOrUpdate(IEntidade entidade) {
        try {
            //Criação do objeto DAO
            Dao<IEntidade, Object> dao = getHelper().getDAO(getEntityClass());
            //Executa a operação e retorna o total de linhas que foram alteradas
            int qtdeLinhasAlteradas = dao.createOrUpdate(entidade).getNumLinesChanged();
            return (qtdeLinhasAlteradas > 0);
        } catch (SQLException e) {
            Log.e(TAG, "Erro metodo createOrUpdate AbstractDAO", e);
            return false;
        }
    }

    /**
     * Método usado para Exclusão de entidades
     *
     * @param entidade
     * @return
     */
    public boolean delete(IEntidade entidade) {
        try {
            return (getHelper().getDAO(getEntityClass()).delete(entidade) > 0);
        } catch (SQLException e) {
            Log.e(TAG, "Erro metodo delete AbstractDAO", e);
            return false;
        }
    }

    /**
     * Listar todas as entidades
     *
     * @return
     */
    public List<T> findAll() {
        try {
            return (List<T>) getHelper().getDao(getEntityClass()).queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, "Erro metodo findAll AbstractDAO", e);
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * Consultar entidades por chave primária
     *
     * @param id
     * @return
     */
    public T findByPK(Object id) {
        try {
            return (T) getHelper().getDAO(getEntityClass()).queryForId(id);

        } catch (SQLException e) {
            Log.e(TAG, "Erro metodo findByPK AbstractDAO", e);
            return null;
        }
    }

    /**
     * Método para realizar filtros por atributos da classe
     *
     * @param attributeName
     * @param value
     * @return
     */
    public List<T> findForAttribute(String attributeName, Object value) {
        try {
            return (List<T>) getHelper().getDao(getEntityClass()).
                    queryForEq(attributeName, value);
        } catch (SQLException e) {
            Log.e(TAG, "Erro metodo findForAttribute AbstractDAO", e);
            return null;
        }
    }
}
