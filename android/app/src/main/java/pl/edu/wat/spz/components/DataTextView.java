package pl.edu.wat.spz.components;

import android.content.Context;

import lombok.Getter;

@Getter
public class DataTextView<T> extends android.support.v7.widget.AppCompatTextView {

    private T item;

    public DataTextView(Context context, T item) {
        super(context);
        this.item = item;
    }

}
