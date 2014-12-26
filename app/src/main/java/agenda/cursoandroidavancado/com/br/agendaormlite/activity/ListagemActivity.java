package agenda.cursoandroidavancado.com.br.agendaormlite.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import agenda.cursoandroidavancado.com.br.agendaormlite.R;
import agenda.cursoandroidavancado.com.br.agendaormlite.model.bean.Contato;
import agenda.cursoandroidavancado.com.br.agendaormlite.model.dao.ContatoDAO;


public class ListagemActivity extends ActionBarActivity {

    //Coleção de strings que serão exibidas
    private List<String> listaDeContatos = new ArrayList<>();
    //Tag usada para controle no LogCat
    private final String TAG = ActionBarActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagemlayout);

        //Layout de exibição da listview
        int adapterLayout = android.R.layout.simple_list_item_1;
        //Adapter usado para exibir as Strings na ListView
        ArrayAdapter<String> adapter = null;
        //ListView que exibirá os dados do Contato
        ListView lvListagem = (ListView) findViewById(R.id.lvListagem);
        carregarLista();
        adapter = new ArrayAdapter<String>(this, adapterLayout, listaDeContatos);
        lvListagem.setAdapter(adapter);
    }

    /**
     * Metodo que solicita servico da camada de modelo
     * e atualiza a lista de contatos exibida
     */
    private void carregarLista() {
        //limpa a coleção de contatos exibidos
        listaDeContatos.clear();
        //Criacao do objeto de persistencia
        ContatoDAO dao = new ContatoDAO(this);
        List<Contato> lista = null;
        try {
            //Solicitacao de servico da camada model
            lista = dao.listar();
        } catch (SQLException e) {
            //tratamento da exceção lançada pela model
            Log.e(TAG, "falha ao carregar lista");
        }
        //preenchimento da lista de contatos
        for (Contato contato : lista) {
            listaDeContatos.add(contato.toString());
        }
        //Encerramento da conexão
        dao.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.litagemmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
