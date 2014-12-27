package agenda.cursoandroidavancado.com.br.agendaormlite.helper;

import android.widget.EditText;

import agenda.cursoandroidavancado.com.br.agendaormlite.R;
import agenda.cursoandroidavancado.com.br.agendaormlite.activity.ListagemActivity;
import agenda.cursoandroidavancado.com.br.agendaormlite.model.bean.Contato;

public class FormularioHelper {

    //Campos do formulário
    private EditText nome;
    private EditText telefone;
    private EditText email;

    //Contato gerado pelo Helper
    private Contato contato;

    public FormularioHelper(ListagemActivity activity) {

        // Bind dos componentes da tela com atributos do Helper
        nome = (EditText) activity.findViewById(R.id.edNome);
        telefone = (EditText) activity.findViewById(R.id.edTelefone);
        email = (EditText) activity.findViewById(R.id.edEmail);

        // Criacao do objeto Aluno
        contato = new Contato();
    }

    /**
     * Atualiza o Contato com os dados da tela
     *
     * @return contato
     */
    public Contato getContato() {
        contato.setNome(nome.getText().toString());
        contato.setTelefone(telefone.getText().toString());
        contato.setEmail(email.getText().toString());
        return contato;
    }

    /**
     * Atualiza os campos da tela com os dados do contato
     *
     * @param contato
     */
    public void setContato(Contato contato) {
        nome.setText(contato.getNome());
        telefone.setText(contato.getTelefone());
        email.setText(contato.getEmail());
        this.contato = contato;
    }
}
