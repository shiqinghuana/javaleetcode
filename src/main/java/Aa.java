import java.util.Deque;
import java.util.LinkedList;

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

}
