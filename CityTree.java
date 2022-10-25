
import java.util.*;
import java.util.Scanner; 

 final class CityNode {
    String name;
    ArrayList<CityNode> sub_cities;
    CityNode(String name){
         this.name = name;
         this.sub_cities = new ArrayList<CityNode>();
    }
}

 public class CityTree{
    public static void main(String[] args){
        CityNode root = new CityNode("root");
        CityNode city1 = new CityNode("city1");
        CityNode city2 = new CityNode("city2");
        CityNode city3 = new CityNode("city3");
        CityNode city4 = new CityNode("city4");
        CityNode city5 = new CityNode("city5");
        CityNode city6 = new CityNode("city6");
        CityNode city7 = new CityNode("city7");
        CityNode city8 = new CityNode("city8");

        root.sub_cities.add(city1);
        root.sub_cities.add(city2);
        city1.sub_cities.add(city3);
        city1.sub_cities.add(city4);
        city2.sub_cities.add(city5);
        city2.sub_cities.add(city6);
        city4.sub_cities.add(city7);
        city4.sub_cities.add(city8);
        
        Scanner ScannerObj = new Scanner(System.in);
        while (true){
            System.out.printf("Enter a city name:");
            String target_city_name = ScannerObj.nextLine(); 

            int[] target_city_depth = {0};
            int[] target_city_height = {0};

            CityNode target_city = find_target_city(root, target_city_name);
            if(target_city == null){
                System.out.println("city not exist");
                continue;
            } 

            get_depth(root, target_city, target_city_depth, 0);
            get_height(target_city,target_city_height, 0);

            System.out.printf("The depth of target node: %d, the height of current node: %d\n", 
                                target_city_depth[0],target_city_height[0]);
        }
    }

    public static void get_depth(CityNode current_city, CityNode target_city,
                                     int[] target_city_depth, int current_depth){
        if (current_city==target_city){
            target_city_depth[0] = current_depth;
            return;
        }
        else{
            for (int i=0;i<current_city.sub_cities.size();i++){
                get_depth(current_city.sub_cities.get(i),target_city,target_city_depth,current_depth+1);
            }
        }

    }

    public static void get_height(CityNode current_city, int[] max_height, int current_height){
        if(current_height>max_height[0]){
            max_height[0] = current_height;
        }
        for (int i=0;i<current_city.sub_cities.size();i++){
            get_height(current_city.sub_cities.get(i),max_height,current_height+1);
        }

    }

    public static CityNode find_target_city(CityNode current_city, String target_city_name){
        if(current_city.name.equals(target_city_name) ){
            return current_city;
        }
        else{
            for (int i=0;i<current_city.sub_cities.size();i++){
                CityNode tmp = find_target_city(current_city.sub_cities.get(i), target_city_name);
                if(tmp!=null){
                    return tmp;
                }
            }
        }
        return null;
    }

}
