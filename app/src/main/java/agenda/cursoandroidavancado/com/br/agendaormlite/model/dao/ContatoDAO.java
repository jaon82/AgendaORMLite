package agenda.cursoandroidavancado.com.br.agendaormlite.model.dao;
import agenda.cursoandroidavancado.com.br.agendaormlite.model.bean.Contato;
/**
 * Created by marciopalheta on 25/12/14.
 * Classe usada para persistência da tabela de Contatos
 */
public class ContatoDAO extends AbstractDAO<Contato> {

    //Atributo que devolve uma instância de ContatoDAO
    private static ContatoDAO instance;

    //Construtor que garante que não sejam criados
    //outras instâncias de ContatoDAO
    private ContatoDAO() {
    }

    /**
     * Implementação do padrão de projeto Singleton
     * @return instância de ContatoDAO
     */
    public static ContatoDAO getInstance() {
        if (instance == null) {
            instance = new ContatoDAO();
        }
        return instance;
    }
}
