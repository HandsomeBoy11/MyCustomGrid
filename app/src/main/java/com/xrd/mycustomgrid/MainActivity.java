package com.xrd.mycustomgrid;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    private List<List<String>> datasList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        List<String> mList= Arrays.asList("http://img5.imgtn.bdimg.com/it/u=530090170,1803130651&fm=26&gp=0.jpg",
                "http://pic29.nipic.com/20130601/12122227_123051482000_2.jpg",
                "http://sc.jb51.net/uploads/allimg/150716/14-150G6093925932.jpg",
                "http://pic29.nipic.com/20130601/12122227_123051482000_2.jpg",
                "http://pic29.nipic.com/20130601/12122227_123051482000_2.jpg",
                "http://sc.jb51.net/uploads/allimg/150716/14-150G6093925932.jpg",
                "http://t8.baidu.com/it/u=3660968530,985748925&fm=191&app=48&wm=1,17,90,45,20,7&wmo=0,0&n=0&g=0n&f=JPEG?sec=1853310920&t=9b4f100f0eedfe853fad24a58a4e1ad7");
        for (int i = 0; i < mList.size(); i++) {
            List<String> list=new ArrayList<>();
            for (int j = 0; j <=i; j++) {
                list.add(mList.get(j));
            }
            datasList.add(list);
        }

        rv.setLayoutManager(new LinearLayoutManager(this));

        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if(position==0){
                    outRect.top=dp2px(10);
                }
                outRect.bottom=dp2px(10);
            }
        });
        rv.setAdapter(new MyAdapter(this));
    }

    private int  dp2px(float dp){
        return (int) (getResources().getDisplayMetrics().density+dp+0.5f);
    }
     class MyAdapter extends RecyclerView.Adapter{

        private  LayoutInflater inflater;
        private Context context;

        public MyAdapter(Context context){
            this.context=context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder holder1 = (ViewHolder) holder;
            List<String> list = datasList.get(position);
            if(list!=null&&list.size()>0){
                holder1.view.removeAllViews();
                for (int i = 0; i < list.size(); i++) {
                    View view = View.inflate(context, R.layout.item2, null);
                    ImageView iv = (ImageView) view.findViewById(R.id.iv);
                    TextView tv = (TextView) view.findViewById(R.id.tv);
                    Glide.with(context).load(list.get(i)).into(iv);
                    tv.setText("索引："+i);
                    final int index=i;
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "数据的第"+index+"条", Toast.LENGTH_SHORT).show();
                        }
                    });
                    holder1.view.setColumnCount(4).setSpaceing(dp2px(6),dp2px(8)).addItemView(view);
                }
            }
        }

        @Override
        public int getItemCount() {
            return datasList.size();
        }
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        private MyCustomGridView view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView.findViewById(R.id.view);
        }
    }
}
