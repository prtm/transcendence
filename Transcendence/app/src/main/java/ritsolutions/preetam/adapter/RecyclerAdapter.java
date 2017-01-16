package ritsolutions.preetam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

import ritsolutions.preetam.interfacer.OnClickHandler;
import ritsolutions.preetam.transcendence.R;

/**
 * Created by Ghost on 17-Sep-16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {
    public List<String> ls;
    ColorGenerator generator;
    private LayoutInflater inflater;
    private Context context;

    public RecyclerAdapter(Context context, List<String> ls) {
        inflater = LayoutInflater.from(context);
        this.ls = ls;
        this.context = context;
        generator = ColorGenerator.MATERIAL;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.custom_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        String s = ls.get(position);
        holder.textView.setText(s);

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(100)
                .height(100)
                .withBorder(2)
                .endConfig()
                .buildRoundRect(s.substring(0, 1), generator.getRandomColor(), 8);

        holder.imageView.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return ls.size();

    }


    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvType);
            imageView = (ImageView) itemView.findViewById(R.id.textDrawable);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnClickHandler onClickHandler = (OnClickHandler) context;
                    String s = textView.getText().toString();
                    if (textView.getText().toString().equals("School Tutions")) {
                        onClickHandler.onClickEvent(s, false);
                    } else {
                        onClickHandler.onClickEvent(s, true);
                    }

                }
            });
        }
    }


}
