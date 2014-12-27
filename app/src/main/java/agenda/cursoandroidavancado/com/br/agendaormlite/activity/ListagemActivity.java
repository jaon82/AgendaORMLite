package agenda.cursoandroidavancado.com.br.agendaormlite.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import agenda.cursoandroidavancado.com.br.agendaormlite.R;
import agenda.cursoandroidavancado.com.br.agendaormlite.helper.FormularioHelper;
import agenda.cursoandroidavancado.com.br.agendaormlite.model.bean.Contato;
import agenda.cursoandroidavancado.com.br.agendaormlite.model.dao.ContatoDAO;

public class ListagemActivity extends ActionBarActivity {

    //Coleção de strings que serão exibidas
    private List<String> listaDeContatos = new ArrayList<>();
    //Tag usada para controle no LogCat
    private final String TAG = ActionBarActivity.class.getSimpleName();

    //Adapter usado para exibir as Strings na ListView
    private ArrayAdapter<String> adapter = null;
    //Objeto Helper do formulário
    private FormularioHelper formularioHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagemlayout);
        //Layout de exibição da listview
        int adapterLayout = android.R.layout.simple_list_item_1;

        //ListView que exibirá os dados do Contato
        ListView lvListagem = (ListView) findViewById(R.id.lvListagem);
        carregarLista();
        adapter = new ArrayAdapter<String>(this, adapterLayout, listaDeContatos);
        lvListagem.setAdapter(adapter);

        formularioHelper = new FormularioHelper(this);

        Button btSalvar = (Button) findViewById(R.id.btSalvar);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContatoDAO dao = ContatoDAO.getInstance();
                dao.createOrUpdate(formularioHelper.getContato());
                carregarLista();
                adapter.notifyDataSetChanged();
                formularioHelper.setContato(new Contato());
            }
        });

    }
    /**
     * Metodo que solicita servico da camada de modelo
     * e atualiza a lista de contatos exibida
     */
    private void carregarLista() {
        //limpa a coleção de contatos exibidos
        listaDeContatos.clear();
        //Criacao do objeto de persistencia
        ContatoDAO dao = ContatoDAO.getInstance();
        List<Contato> lista = null;
            //Solicitacao de servico da camada model
        lista = dao.findAll();
        //preenchimento da lista de contatos
        for (Contato contato : lista) {
            listaDeContatos.add(contato.toString());
        }
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
