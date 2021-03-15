import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;

import static java.util.Objects.hash;

public class Solution {

    /**
     * 227. 基本计算器 II
     * <link> https://leetcode-cn.com/problems/basic-calculator-ii/</>
     * */
    public int calculate(String s) {
        Deque<Integer> stack = new LinkedList<Integer>();
        char preSign = '+';
        int num = 0;
        int n = s.length();
        for (int i = 0; i < n; ++i) {
            if (Character.isDigit(s.charAt(i))) {
                num = num * 10 + s.charAt(i) - '0';
            }
            if (!Character.isDigit(s.charAt(i)) && s.charAt(i) != ' ' || i == n - 1) {
                switch (preSign) {
                    case '+':
                        stack.push(num);
                        break;
                    case '-':
                        stack.push(-num);
                        break;
                    case '*':
                        stack.push(stack.pop() * num);
                        break;
                    default:
                        stack.push(stack.pop() / num);
                }
                preSign = s.charAt(i);
                num = 0;
            }
        }
        int ans = 0;
        while (!stack.isEmpty()) {
            ans += stack.pop();
        }
        return ans;
    }


    /**
     *  @link calculate
    * */
    public int calculate1(String s) {
        ScriptEngineManager sc = new ScriptEngineManager();

        ScriptEngine js = sc.getEngineByName("js");
        Object eval = null;
        try {
            eval = js.eval(s);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        String split = String.valueOf(eval).split("\\.")[0];
        return Integer.parseInt(split);
    }

    /**
     * @link isValidSerialization
     * */
    public boolean isValidSerialization1(String preorder) {
        if (preorder.equals("#")){return true;} //处理空树
        Deque<String> stack = new LinkedList<>(); //村好树节点
        String[] split = preorder.split(",");
        int length = split.length;
        if (length %2 ==0 || split[0].equals("#") || !split[length-1].equals("#")){
            return false; //二叉树比然是基数且第一个字符不为空，且已空字符结尾
        }
        stack.add(split[0]);
        for (int i = 1; i < length-1; i++) {
            if (split[i].equals("#")){
                if (stack.isEmpty() && i<length-1){  // 如果没有父节点了但是后面还有子节点，失败
                    return false;
                }
                stack.pop();
            }else {
                stack.push(split[i]);
            }
        }
        return true;
    }


    /**
     * 331. 验证二叉树的前序序列化
     * <link> https://leetcode-cn.com/problems/verify-preorder-serialization-of-a-binary-tree/ </>
    * */
    public boolean isValidSerialization(String preorder) {
        if (preorder.equals("#")){return true;} //处理空树
        Deque<String> stack = new LinkedList<>(); //村好树节点
        String[] split = preorder.split(",");
        int length = split.length;
        if (length %2 ==0 || split[0].equals("#") || !split[length-1].equals("#")){
            return false; //二叉树比然是基数且第一个字符不为空，且已空字符结尾
        }
        stack.push(split[0]);
        return dfs(stack,split,1);
    }
    public boolean dfs(Deque<String> stack, String[] preorder, int i) {
        if (i == preorder.length-1){
            return true;
        }
        if (!preorder[i].equals("#")){
            stack.push(preorder[i]);
        }else {
            if (stack.isEmpty() && i<preorder.length-1){  // 如果没有父节点了但是后面还有子节点，失败
                return false;
            }
            stack.pop();
        }
        return dfs(stack,preorder,i+1);


    }

    /**
    * 54. 螺旋矩阵
     * <Link>https://leetcode-cn.com/problems/spiral-matrix/</Link>
    * */
    public List<Integer> spiralOrder(int[][] matrix) {
        int xMax = matrix.length, yMax = matrix[0].length,xMin = 0,yMin =0,flag=0;
        //flag 控制方向，0 向右 1向下 2 向左 3 向上 循环
        // xMax 最大行数  注意取值的时候要 -1  xMin 最小行数
        // yMax 最大列数，，注意取值的时候要 -1  yMin 最小列数
        List<Integer> list = new ArrayList<>();
        while (xMin<xMax && yMax >yMin){
            switch (flag){
                case 0: // [x,y++]
                    for (int i = yMin; i < yMax; i++) {
                        list.add(matrix[xMin][i]);
                    }
                    xMin ++;
                    flag++;
                    break;
                case 1:// [x++,y]
                    for (int i = xMin; i < xMax; i++) {
                        list.add(matrix[i][yMax-1]);
                    }
                    yMax--;
                    flag++;
                    break;
                case 2:// [x,y--]
                    for (int i = yMax-1; i >yMin-1; i--) {
                        list.add(matrix[xMax-1][i]);
                    }
                    xMax--;
                    flag++;
                    break;
                default: //[x--,y]
                    for (int i = xMax-1; i >xMin-1 ; i--) {
                        list.add(matrix[i][yMin]);
                    }
                    yMin++;
                    flag=0;
                    break;
            }
        }
        return list;
    }


    /**
     * 867. 转置矩阵
     * <Link>https://leetcode-cn.com/problems/transpose-matrix/</>
     * */
    public int[][] transpose(int[][] matrix) {
        int m = matrix.length,n = matrix[0].length;
        int[][] ints = new int[n][m];
        for (int i = 0; i <m ; i++) {
            for (int j = 0; j < n; j++) {
                ints[j][i] = matrix[i][j];
            }
        }
        return ints;
    }

    /**
    * 832. 翻转图像
    * <Link>  https://leetcode-cn.com/problems/flipping-an-image/ </>
    * */
    public int[][] flipAndInvertImage(int[][] A) {
        int m = A[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j <(m+1)/2 ; j++) {
                int a = A[i][j];
                // 交换ab ^1
                A[i][j] = A[i][m-j-1]^1;
                A[i][m-j-1] = a^1;
            }
        }
        return A;
    }

    /**
     *  1052. 爱生气的书店老板
     *  <link> https://leetcode-cn.com/problems/grumpy-bookstore-owner </link>
     */
    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        int tol = 0;
        for (int i = 0; i < customers.length; i++) {
            tol += customers[i] * (grumpy[i] ^ 1);
        }
        System.out.println(tol);
        int now = 0;
        for (int i = 0; i < X; i++) {
            now += customers[i] * grumpy[i];
        }
        int max = now;
        for (int i = 1; i <= customers.length - X; i++) {
            now += customers[i + X - 1] * grumpy[i + X - 1] - customers[i - 1] * grumpy[i - 1];
            max = Math.max(max, now);
        }
        System.out.println(max);
        return tol + max;
    }



    /**
     * 978. 最长湍流子数组
     * <link>https://leetcode-cn.com/problems/longest-turbulent-subarray/</>
     * */
    public int maxTurbulenceSize(int[] arr) {
        int n = arr.length, i = 0, k = 0, ret = 1;
        while (i<n-1){
            if (k ==i){
                if (arr[i] == arr[i+1]){
                    k++;
                }
                i++;
            }else if (arr[i]>arr[i+1]&& arr[i]>arr[i-1] || arr[i]<arr[i+1]&& arr[i]<arr[i-1]){
                i++;
            }else {
                k=i;
            }
            ret = Math.max(ret, i - k +1);
        }
        return ret;
    }
/**
 * 可获得的最大点数
 * <link> https://leetcode-cn.com/submissions/detail/144477658/ </>
* */
    public int maxScore(int[] cardPoints, int k) {

        int sum = Arrays.stream(cardPoints, 0, k).sum(),l = cardPoints.length-1;;
        int res =sum;
        for (int i = k-1; i >=0 ; i--) {
            sum = sum - cardPoints[i] + cardPoints[l];
            res = Math.max(res,sum);
            l--;
        }
        return res;
    }

/**
 * <link> https://leetcode-cn.com/problems/non-decreasing-array/</>
 * 665. 非递减数列*/

    public boolean checkPossibility(int[] nums) {
        int n =0,l = nums.length;

        for (int i = 0; i <l-1 ; i++) {
            int x =nums[i],y=nums[i+1];
            if (x>y){
                /**
                 * 递减。判断改变 x 还是y
                 * 如果  nums[i]> nums[i+1] >= nums[i-1]
                 * 则调整nums[i] =nums[i+1] 能保证 0..i+1 序列单调 但是 i值后面不会用了，所以不调整，只计数
                 *
                 * 如果 nums[i] >  nums[i-1] > nums[i+1]
                 * 则调整 nums[i+1] = nums[i] 能保证 0..i+1 序列单调
                 * 同时计算调整次数
                 * */
                n++;
                if (n>1){return false;}
                if (i!=0 && y< nums[i-1]){
                    nums[i+1] = x;
                }
            }
        }
        return true;
    }


    public static void main(String[] args) throws ScriptException {
        HashMap<Integer,Integer> objectMyHashSet = new HashMap<>();
        objectMyHashSet.put(1,2);
    }
}
