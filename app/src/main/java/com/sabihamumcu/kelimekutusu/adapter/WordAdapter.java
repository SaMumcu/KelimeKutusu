package com.sabihamumcu.kelimekutusu.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sabihamumcu.kelimekutusu.Listele;
import com.sabihamumcu.kelimekutusu.R;
import com.sabihamumcu.kelimekutusu.model.Word;
import com.sabihamumcu.kelimekutusu.viewmodel.WordViewModel;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.SimpleViewHolder> implements Filterable {

    private LayoutInflater mInflater;
    private List<Word> wordList;
    private List<Word> filteredWordList;
    private Context context;
    private WordViewModel wordViewModel;

    public WordAdapter(List<Word> wordList, Context context, WordViewModel wordViewModel) {
        this.wordList = wordList;
        this.context = context;
        this.filteredWordList = wordList;
        this.wordViewModel = wordViewModel;
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        SimpleViewHolder vh = new SimpleViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, final int position) {
        holder.word.setText(filteredWordList.get(position).getWord());
        holder.description.setText(filteredWordList.get(position).getDescription());
        holder.word_row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Değiştir yada Sil").setMessage("Bu kelimeyi silmek mi istiyorsun?")
                        .setPositiveButton("Sil", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Word deleted = filteredWordList.get(position);
                                wordViewModel.deleteWord(deleted);
                                Toast.makeText(context, "Word is deleted: " + deleted.getWord(), Toast.LENGTH_SHORT).show();

                                filteredWordList.clear();
                                //notifyDataSetChanged();
                            }
                        })
//                            .setNegativeButton("Değiştir", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Toast.makeText(Listele.this,"Databasede guncelleme yapilacak.",Toast.LENGTH_SHORT).show();
//                                }
//                            })
                        .setNeutralButton("Kapat", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                    Toast.makeText(Listele.this,"Dialog kapatilacak.",Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        }).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredWordList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredWordList = wordList;
                } else {
                    List<Word> filteredList = new ArrayList<>();
                    for (Word row : wordList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for title/desc/cat matchs
                        if (row.getWord().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getDescription().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    filteredWordList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredWordList;
                filterResults.count = filteredWordList.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredWordList = (List<Word>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public TextView word;
        public TextView description;
        public CardView word_row;

        public SimpleViewHolder(View itemView) {
            super(itemView);

            word_row = itemView.findViewById(R.id.word_row);
            word = itemView.findViewById(R.id.word);
            description = itemView.findViewById(R.id.description);


        }

    }
}
