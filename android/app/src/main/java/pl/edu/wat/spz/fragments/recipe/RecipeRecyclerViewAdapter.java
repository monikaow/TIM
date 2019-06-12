package pl.edu.wat.spz.fragments.recipe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.edu.wat.spz.R;
import pl.edu.wat.spz.entity.RecipeHistory;

import static pl.edu.wat.spz.fragments.recipe.RecipeRecyclerViewAdapter.RecipeViewHolder;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private final List<RecipeHistory> recipeHistoryList;
    private final Context context;

    public RecipeRecyclerViewAdapter(List<RecipeHistory> items, Context context) {
        this.recipeHistoryList = items;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder viewHolder, int position) {
        final RecipeHistory recipeHistory = recipeHistoryList.get(position);
        viewHolder.item = recipeHistory;
        viewHolder.receiptDate.setText(recipeHistory.getReceiptDate());
        viewHolder.comment.setText(recipeHistory.getComment());
        viewHolder.medicineName.setText(recipeHistory.getMedicineName());

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(viewHolder.item);
            }
        });
    }

    private void openDialog(RecipeHistory item) {
        new AlertDialog.Builder(this.context)
                .setTitle(item.getMedicineName())
                .setMessage(item.getDetails())
                .setPositiveButton("Zamknij", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create().show();
    }

    @Override
    public int getItemCount() {
        return recipeHistoryList.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView receiptDate;
        final TextView comment;
        final TextView medicineName;
        RecipeHistory item;

        RecipeViewHolder(View view) {
            super(view);
            this.view = view;
            this.receiptDate = view.findViewById(R.id.receiptDate);
            this.comment = view.findViewById(R.id.comment);
            this.medicineName = view.findViewById(R.id.medicineName);
        }
    }
}
