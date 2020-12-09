package br.com.kenderck.blocodenotas.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.kenderck.blocodenotas.R;
import br.com.kenderck.blocodenotas.dao.NotaDAO;
import br.com.kenderck.blocodenotas.model.Nota;
import br.com.kenderck.blocodenotas.ui.recyclerview.adapter.ListaNotasAdapter;
import br.com.kenderck.blocodenotas.ui.recyclerview.adapter.listener.OnItemClickListener;
import br.com.kenderck.blocodenotas.ui.recyclerview.adapter.listener.OnLongItemClickListener;
import br.com.kenderck.blocodenotas.ui.recyclerview.helper.callback.NotaItemTouchHelperCallBack;

import static br.com.kenderck.blocodenotas.ui.activity.ConstantesActivities.CHAVE_NOTA;
import static br.com.kenderck.blocodenotas.ui.activity.ConstantesActivities.CHAVE_POSITION;
import static br.com.kenderck.blocodenotas.ui.activity.ConstantesActivities.DEFAULT_VALUE;
import static br.com.kenderck.blocodenotas.ui.activity.ConstantesActivities.REQUEST_CODE_ALTERA_NOTA;
import static br.com.kenderck.blocodenotas.ui.activity.ConstantesActivities.REQUEST_CODE_INSERE_NOTA;
import static br.com.kenderck.blocodenotas.ui.activity.ConstantesActivities.TITULO_APP_BAR;

public class ListaNotasActivity extends AppCompatActivity {



    private ListaNotasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        setTitle(TITULO_APP_BAR);
        List<Nota> todasNotas = PegaTodasNotas();

        configuraRecyclerView(todasNotas);
        configuraBotaoInsere();
    }

    private List<Nota> PegaTodasNotas() {
        NotaDAO dao = new NotaDAO();
        return dao.todos();
    }

    private void configuraBotaoInsere() {
        TextView insereNota = findViewById(R.id.insere_nota);
        insereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaiParaFormularioSemNota();
            }
        });
    }

    private void vaiParaFormularioSemNota() {
        Intent intent = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        startActivityForResult(intent, REQUEST_CODE_INSERE_NOTA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        assert data != null;
        if (requestCode == REQUEST_CODE_INSERE_NOTA && resultCode == Activity.RESULT_OK && data.hasExtra(CHAVE_NOTA)) {// Activity.RESULT_OK padrão do android pra ver se o resultado da activity deu certo
            Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            adicionaNota(notaRecebida);
        }
        if (requestCode == REQUEST_CODE_ALTERA_NOTA && resultCode == Activity.RESULT_OK && data.hasExtra(CHAVE_NOTA) && data.hasExtra(CHAVE_POSITION)) {
            Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            int positionRecebida = data.getIntExtra(CHAVE_POSITION, DEFAULT_VALUE);
            assert notaRecebida != null;

            if (positionRecebida > DEFAULT_VALUE) {
                alteraNota(notaRecebida, positionRecebida);
            }else{
                Toast.makeText(this, "Ocorreu um problema na alteração da nota", Toast.LENGTH_SHORT).show();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void alteraNota(Nota nota, int position) {
        new NotaDAO().altera(position, nota);
        adapter.altera(position, nota);
    }

    private void adicionaNota(Nota nota) {
        new NotaDAO().insere(nota);
        adapter.adiciona(nota);
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.lista_nota);
        configuraAdapter(todasNotas, listaNotas);
//        configuraLayoutManager(listaNotas);
        configuraItemTouchHelper(listaNotas);

    }

    private void configuraItemTouchHelper(RecyclerView lista) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemTouchHelperCallBack(adapter));
        itemTouchHelper.attachToRecyclerView(lista);
    }

   /* private void configuraLayoutManager(RecyclerView listaNotas) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);//cria um layout personalizado para setar o recyclerview
        listaNotas.setLayoutManager(layoutManager);//seta o layout
    }*/

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(todasNotas, this);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota, int position) {
                abreFormularioComNota(nota, position);
            }
        });
        adapter.setOnLongItemClickListener(new OnLongItemClickListener() {
            @Override
            public void OnLongClick(Nota nota, int position) {
                Toast.makeText(ListaNotasActivity.this, "AE CARAIII, clique longooo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void abreFormularioComNota(Nota nota, int position) {
        Intent abreFormComNota = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        abreFormComNota.putExtra(CHAVE_NOTA, nota);
        abreFormComNota.putExtra(CHAVE_POSITION, position);
        startActivityForResult(abreFormComNota, REQUEST_CODE_ALTERA_NOTA);
    }
}
