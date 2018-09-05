
public class Item{

     private String raw_data;

     public Item(String raw_data){
          this.raw_data = raw_data;
     }

     @Override
     public String toString(){
          return raw_data;
     }
}
