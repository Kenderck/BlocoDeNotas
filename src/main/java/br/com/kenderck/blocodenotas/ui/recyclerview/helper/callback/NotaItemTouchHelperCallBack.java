package br.com.kenderck.blocodenotas.ui.recyclerview.helper.callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import br.com.kenderck.blocodenotas.dao.NotaDAO;
import br.com.kenderck.blocodenotas.ui.recyclerview.adapter.ListaNotasAdapter;

public class NotaItemTouchHelperCallBack extends ItemTouchHelper.Callback {

    private ListaNotasAdapter adapter;

    public NotaItemTouchHelperCallBack(ListaNotasAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int marcacaoDeDeslize = getMarcacaoDeDeslize();
        int marcacaoDeArrastar = getMarcacaoDeArrastar();
        return  makeMovementFlags(marcacaoDeArrastar, marcacaoDeDeslize);//para arrastar todas as direções, e apra deslizar esquerdo e direito
    }

    private int getMarcacaoDeArrastar() {
        return ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
    }

    private int getMarcacaoDeDeslize() {
        return ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int posicaoInicio = viewHolder.getAdapterPosition();
        int posicaoFim = target.getAdapterPosition();
        trocaNotas(posicaoInicio, posicaoFim);
        return true;//true para ter certeza que houve o movimento
    }

    private void trocaNotas(int posicaoInicio, int posicaoFim) {
        new NotaDAO().troca(posicaoInicio, posicaoFim);
        adapter.troca(posicaoInicio, posicaoFim);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {//ação a ser tomada ao deslizar pro lado
        int posicaoNotaDeslizada = viewHolder.getAdapterPosition();
        removeNota(posicaoNotaDeslizada);
    }

    private void removeNota(int posicao) {
        new NotaDAO().remove(posicao);
        adapter.remove(posicao);
    }
}
