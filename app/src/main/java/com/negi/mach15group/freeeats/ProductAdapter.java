package com.negi.mach15group.freeeats;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Item> itemList;
    public ProductAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.card_layout,parent,false);
        ProductViewHolder holder=new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        //String event_name,String item, String start_time,String stop_time, String type, int images, Double lat, Double lon
        Item item=itemList.get(position);
        Double endlat,endlon;
        endlat=item.getLat();
        endlon=item.getLon();
         GPSTracker gps;
         gps=new GPSTracker(this.context);
   Location location=gps.getLocation();
        float result[]=new float[10];
        location.distanceBetween(gps.getLatitude(),gps.getLongitude(),endlat,endlon,result);

       //double dist=meterDistanceBetweenPoints(gps.getLatitude(),endlon,endlat,endlon);
        holder.event.setText(item.getEvent_name());
//      holder.distance.setText(""+dist);
        holder.items.setText(item.getItem());
        holder.start.setText(item.getStart_time()+"-"+item.getStop_time());
        holder.distance.setText(""+result[0]+" m");
        holder.type.setText(item.getType());
        holder.imageView.setImageDrawable(context.getResources().getDrawable(item.getImages()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{
            TextView event,items,start,distance,type;
            ImageView imageView;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.card_image);
            event=itemView.findViewById(R.id.event_name);
            items=itemView.findViewById(R.id.items_list);
            start=itemView.findViewById(R.id.start_stop_time);
            distance=itemView.findViewById(R.id.event_distance);
            type=itemView.findViewById(R.id.type);
        }
    }
/*private double meterDistanceBetweenPoints(double lat1,double lon1,double lat2,double lon2)
    {
        float pk=(float) (180.0f/Math.PI);
        float a1=(float)lat1/pk;
       float a2=(float)lon1/pk;
        float b1=(float)lat2/pk;
       float b2=(float)lon2/pk;
        double t1=Math.cos(a1)* Math.cos(a2)*Math.cos(b1)*Math.cos(b2);
        double t2=Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2);
        double t3= Math.sin(a1)*Math.sin(b1);
        double total=Math.acos(t1+t2+t3);
        return 6366000*total;
    }

 */
}
