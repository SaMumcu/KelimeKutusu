package com.sabihamumcu.kelimekutusu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.sabihamumcu.kelimekutusu.R
import com.sabihamumcu.kelimekutusu.adapter.WordAdapter.SimpleViewHolder
import com.sabihamumcu.kelimekutusu.model.Word
import com.sabihamumcu.kelimekutusu.viewmodel.WordViewModel
import java.util.*

class WordAdapter(private val wordList: MutableList<Word>, private val context: Context,
                  wordViewModel: WordViewModel) : RecyclerView.Adapter<SimpleViewHolder>(), Filterable {

    private var filteredWordList: MutableList<Word>
    private val wordViewModel: WordViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.row, parent, false)
        return SimpleViewHolder(v)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.word.text = filteredWordList[position].word
        holder.description.text = filteredWordList[position].description
        holder.word_row.setOnLongClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.update_or_delete)).setMessage(context.getString(R.string.do_you_want_to_delete_this_word))
                    .setPositiveButton(context.getString(R.string.delete)) { dialogInterface, i ->
                        val deleted = filteredWordList[position]
                        wordViewModel.deleteWord(deleted)
                        Toast.makeText(context, context.getString(R.string.word_is_deleted_) + deleted.word, Toast.LENGTH_SHORT).show()
                        filteredWordList.clear()

                    }
                    .setNeutralButton(context.getString(R.string.close)) { dialogInterface, i ->
                        dialogInterface.dismiss()
                    }.show()
            false
        }
    }

    override fun getItemCount(): Int {
        return filteredWordList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                filteredWordList = if (charString.isEmpty()) {
                    wordList
                } else {
                    val filteredList: MutableList<Word> = ArrayList()
                    for (row in wordList) {
                        if (row.word!!.toLowerCase().contains(charString.toLowerCase()) ||
                                row.description!!.contains(charSequence)) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredWordList
                filterResults.count = filteredWordList.size
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredWordList = filterResults.values as MutableList<Word>
                notifyDataSetChanged()
            }
        }
    }

    class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var word: TextView
        var description: TextView
        var word_row: CardView

        init {
            word_row = itemView.findViewById(R.id.word_row)
            word = itemView.findViewById(R.id.word)
            description = itemView.findViewById(R.id.description)
        }
    }

    init {
        filteredWordList = wordList
        this.wordViewModel = wordViewModel
    }
}