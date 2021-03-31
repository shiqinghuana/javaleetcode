import java.util.*;

public class Aa {


    public boolean isValidSerialization(String preorder) {
        if (preorder.equals("#")){return true;}
        String[] split = preorder.split(",");
        int length = split.length;
        if (length %2 ==0 || split[0].equals("#") || !split[length-1].equals("#")){
            return false;
        }
        Deque<String> stack = new LinkedList<>();
        stack.push(split[0]);
        return dfs(stack,split,1);

    }


    public boolean dfs( Deque<String>  stack ,String[] preorder,int i){
        if (i == preorder.length-1){
            return true;
        }if (!preorder[i].equals("#")){
            stack.push(preorder[i]);
        }else {
            if (stack.isEmpty() && i<preorder.length-1){
                return false;
            }
            stack.pop();
        }
        return dfs(stack,preorder,i+1);


    }

    public static void main(String[] args) {
        ArrayList<List> objects = new ArrayList<>();
        List<Integer> integers = Arrays.asList(1, 2, 3);
        objects.add(integers);
        List<Integer> integers1 = Arrays.asList(1, 2, 3);
        System.out.println(objects.contains(integers1));




    }

}
