import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;

import static java.util.Objects.hash;

public class Solution {

    /**
     * 227. 基本计算器 II
     * <link> https://leetcode-cn.com/problems/basic-calculator-ii/</>
     */
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
     * @link calculate
     */
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
     */
    public boolean isValidSerialization1(String preorder) {
        if (preorder.equals("#")) {
            return true;
        } //处理空树
        Deque<String> stack = new LinkedList<>(); //村好树节点
        String[] split = preorder.split(",");
        int length = split.length;
        if (length % 2 == 0 || split[0].equals("#") || !split[length - 1].equals("#")) {
            return false; //二叉树比然是基数且第一个字符不为空，且已空字符结尾
        }
        stack.add(split[0]);
        for (int i = 1; i < length - 1; i++) {
            if (split[i].equals("#")) {
                if (stack.isEmpty() && i < length - 1) {  // 如果没有父节点了但是后面还有子节点，失败
                    return false;
                }
                stack.pop();
            } else {
                stack.push(split[i]);
            }
        }
        return true;
    }


    /**
     * 331. 验证二叉树的前序序列化
     * <link> https://leetcode-cn.com/problems/verify-preorder-serialization-of-a-binary-tree/ </>
     */
    public boolean isValidSerialization(String preorder) {
        if (preorder.equals("#")) {
            return true;
        } //处理空树
        Deque<String> stack = new LinkedList<>(); //村好树节点
        String[] split = preorder.split(",");
        int length = split.length;
        if (length % 2 == 0 || split[0].equals("#") || !split[length - 1].equals("#")) {
            return false; //二叉树比然是基数且第一个字符不为空，且已空字符结尾
        }
        stack.push(split[0]);
        return dfs(stack, split, 1);
    }

    public boolean dfs(Deque<String> stack, String[] preorder, int i) {
        if (i == preorder.length - 1) {
            return true;
        }
        if (!preorder[i].equals("#")) {
            stack.push(preorder[i]);
        } else {
            if (stack.isEmpty() && i < preorder.length - 1) {  // 如果没有父节点了但是后面还有子节点，失败
                return false;
            }
            stack.pop();
        }
        return dfs(stack, preorder, i + 1);


    }

    /**
     * 54. 螺旋矩阵
     * <Link>https://leetcode-cn.com/problems/spiral-matrix/</Link>
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        int xMax = matrix.length, yMax = matrix[0].length, xMin = 0, yMin = 0, flag = 0;
        //flag 控制方向，0 向右 1向下 2 向左 3 向上 循环
        // xMax 最大行数  注意取值的时候要 -1  xMin 最小行数
        // yMax 最大列数，，注意取值的时候要 -1  yMin 最小列数
        List<Integer> list = new ArrayList<>();
        while (xMin < xMax && yMax > yMin) {
            switch (flag) {
                case 0: // [x,y++]
                    for (int i = yMin; i < yMax; i++) {
                        list.add(matrix[xMin][i]);
                    }
                    xMin++;
                    flag++;
                    break;
                case 1:// [x++,y]
                    for (int i = xMin; i < xMax; i++) {
                        list.add(matrix[i][yMax - 1]);
                    }
                    yMax--;
                    flag++;
                    break;
                case 2:// [x,y--]
                    for (int i = yMax - 1; i > yMin - 1; i--) {
                        list.add(matrix[xMax - 1][i]);
                    }
                    xMax--;
                    flag++;
                    break;
                default: //[x--,y]
                    for (int i = xMax - 1; i > xMin - 1; i--) {
                        list.add(matrix[i][yMin]);
                    }
                    yMin++;
                    flag = 0;
                    break;
            }
        }
        return list;
    }


    /**
     * 867. 转置矩阵
     * <Link>https://leetcode-cn.com/problems/transpose-matrix/</>
     */
    public int[][] transpose(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] ints = new int[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                ints[j][i] = matrix[i][j];
            }
        }
        return ints;
    }

    /**
     * 832. 翻转图像
     * <Link>  https://leetcode-cn.com/problems/flipping-an-image/ </>
     */
    public int[][] flipAndInvertImage(int[][] A) {
        int m = A[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < (m + 1) / 2; j++) {
                int a = A[i][j];
                // 交换ab ^1
                A[i][j] = A[i][m - j - 1] ^ 1;
                A[i][m - j - 1] = a ^ 1;
            }
        }
        return A;
    }

    /**
     * 1052. 爱生气的书店老板
     * <link> https://leetcode-cn.com/problems/grumpy-bookstore-owner </link>
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
     */
    public int maxTurbulenceSize(int[] arr) {
        int n = arr.length, i = 0, k = 0, ret = 1;
        while (i < n - 1) {
            if (k == i) {
                if (arr[i] == arr[i + 1]) {
                    k++;
                }
                i++;
            } else if (arr[i] > arr[i + 1] && arr[i] > arr[i - 1] || arr[i] < arr[i + 1] && arr[i] < arr[i - 1]) {
                i++;
            } else {
                k = i;
            }
            ret = Math.max(ret, i - k + 1);
        }
        return ret;
    }

    /**
     * 可获得的最大点数
     * <link> https://leetcode-cn.com/submissions/detail/144477658/ </>
     */
    public int maxScore(int[] cardPoints, int k) {

        int sum = Arrays.stream(cardPoints, 0, k).sum(), l = cardPoints.length - 1;
        ;
        int res = sum;
        for (int i = k - 1; i >= 0; i--) {
            sum = sum - cardPoints[i] + cardPoints[l];
            res = Math.max(res, sum);
            l--;
        }
        return res;
    }

    /**
     * <link> https://leetcode-cn.com/problems/non-decreasing-array/</>
     * 665. 非递减数列
     */

    public boolean checkPossibility(int[] nums) {
        int n = 0, l = nums.length;
        for (int i = 0; i < l - 1; i++) {
            int x = nums[i], y = nums[i + 1];
            if (x > y) {

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
                if (n > 1) {
                    return false;
                }
                if (i != 0 && y < nums[i - 1]) {
                    nums[i + 1] = x;
                }
            }
        }
        return true;
    }


    private int dfs(String s, String t, int i, int j, int[][] a) {
        int now;
        if (j == t.length()) {
            return 1;
        }
        if (s.length() - i < t.length() - j) {
            return 0;
        }
        if ((now = a[i][j]) > -1) {
            return now;
        }
        int res = 0;
        for (; i < s.length(); i++) {
            if (s.charAt(i) == t.charAt(j)) {
                res += dfs(s, t, i + 1, j + 1, a);
            }
            a[i][j] = res;
        }
        return res;
    }


    public int numDistinct(String s, String t) {
        int[][] memo = new int[s.length()][t.length()];
        for (int i = 0; i < s.length(); i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs1(s, t, 0, 0, memo);
    }

    public int dfs1(String s, String t, int i, int j, int[][] memo) {
        if (j == t.length()) return 1;
        if (i == s.length() || (s.length() - i < t.length() - j)) return 0;
        if (memo[i][j] != -1) return memo[i][j];
        if (s.charAt(i) == t.charAt(j)) {
            memo[i][j] = dfs1(s, t, i + 1, j + 1, memo) + dfs(s, t, i + 1, j, memo);
        } else {
            memo[i][j] = dfs1(s, t, i + 1, j, memo);
        }
        return memo[i][j];
    }


    /**
     * <link> https://leetcode-cn.com/problems/reverse-linked-list-ii/
     * 92. 反转链表 II
     */

    public ListNode reverseBetween(ListNode head, int left, int right) {
        // 因为头节点有可能发生变化，使用虚拟头节点可以避免复杂的分类讨论
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;

        ListNode pre = dummyNode;
        // 第 1 步：从虚拟头节点走 left - 1 步，来到 left 节点的前一个节点
        // 建议写在 for 循环里，语义清晰
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }

        // 第 2 步：从 pre 再走 right - left + 1 步，来到 right 节点
        ListNode rightNode = pre;
        for (int i = 0; i < right - left + 1; i++) {
            rightNode = rightNode.next;
        }

        // 第 3 步：切断出一个子链表（截取链表）
        ListNode leftNode = pre.next;
        ListNode curr = rightNode.next;

        // 注意：切断链接
        pre.next = null;
        rightNode.next = null;

        // 第 4 步：同第 206 题，反转链表的子区间
        reverseLinkedList(leftNode);

        // 第 5 步：接回到原来的链表中
        pre.next = rightNode;
        leftNode.next = curr;
        return dummyNode.next;
    }

    private void reverseLinkedList(ListNode head) {
        // 也可以使用递归反转一个链表
        ListNode pre = null;
        ListNode cur = head;

        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
    }

    /**
     * <link>https://leetcode-cn.com/problems/evaluate-reverse-polish-notation/
     * 150. 逆波兰表达式求值
     */
    public int evalRPN(String[] tokens) {
        LinkedList<Integer> objects = new LinkedList<>();
        List a = Arrays.asList("+", "-", "*", "/");
        for (String chars : tokens) {
            Integer i;
            if (a.contains(chars)) {
                Integer pop = objects.pop();
                Integer pop1 = objects.pop();
                switch (chars) {
                    case "+":
                        i = pop1 + pop;
                        break;
                    case "-":
                        i = pop1 - pop;
                        break;
                    case "*":
                        i = pop1 * pop;
                        break;
                    default:
                        i = pop1 / pop;
                        break;
                }
            } else {
                i = Integer.valueOf(chars);
            }
            objects.push(i);
        }
        return objects.pop();
    }


    /**
     * <link> https://leetcode-cn.com/problems/set-matrix-zeroes/
     * 73. 矩阵置零
     */
    public void setZeroes
    (int[][] matrix) {
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        int m = matrix.length, n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    if (!map.containsKey(i)) {
                        map.put(i, new ArrayList<>());
                    }
                    map.get(i).add(j);
                }
            }
        }
        map.forEach((k, v) -> {
            Arrays.fill(matrix[k], 0);
            v.forEach(i -> {
                        for (int j = 0; j < m; j++) {
                            matrix[j][i] = 0;
                        }
                    }
            );
        });

    }

    /**
     * <link> https://leetcode-cn.com/problems/number-of-1-bits/
     * 191. 位1的个数
     */
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int ret = 0;
        for (int i = 0; i < 32; i++) {
            if ((n & (1 << i)) != 0) {
                ret++;
            }
        }
        return ret;
    }


    /**
     * <link> https://leetcode-cn.com/problems/132-pattern/
     * 456. 132 模式
     */
    public boolean find132pattern(int[] nums) {
        int n = nums.length, max = Integer.MIN_VALUE;
        if (n < 3) {
            return false;
        }
        LinkedList<Integer> dq = new LinkedList<>();
        dq.push(nums[n - 1]);
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] < max) {
                return true;
            }
            while (!dq.isEmpty() && nums[i] > dq.peek()) {
                max = dq.pop();
            }
            dq.push(nums[i]);
        }
        return false;
    }

    /**
     * <link> https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list-ii/
     * 82. 删除排序链表中的重复元素 II
     */
    public ListNode deleteDuplicates1(ListNode head) {
        ListNode dumpHead = new ListNode(Integer.MIN_VALUE, head);
        ListNode cur = dumpHead;
        while (cur.next != null && cur.next.next != null) {
            if (cur.next.val == cur.next.next.val) {
                int x = cur.next.val;
                while (cur.next != null && cur.next.val == x) {
                    cur.next = cur.next.next;
                }
            } else {
                cur = cur.next;
            }
        }
        return dumpHead.next;
    }

    //efinition for singly-linked list.
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * <link> https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/
     * 83. 删除排序链表中的重复元素
     */

    public ListNode deleteDuplicates(ListNode head) {
        ListNode cur = head;
        if (head == null) {
            return head;
        }
        while (head != null && head.next != null) {
            if (head.val == head.next.val) {
                head.next = head.next.next;
            } else {
                head = head.next;
            }
        }
        return cur;
    }

    /**
     * 74. 搜索二维矩阵
     * <link> https://leetcode-cn.com/problems/search-a-2d-matrix/solution/
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        if (target < matrix[0][0] || target > matrix[m - 1][n - 1]) {
            return false;
        }
        boolean flag = false;
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] <= target && target <= matrix[i][n - 1]) {
                flag = (Arrays.binarySearch(matrix[i], target) >= 0);
                break;
            }
        }
        return flag;
    }

    /**
     * <link>  https://leetcode-cn.com/problems/reverse-bits/
     * 190. 颠倒二进制位
     */

    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int rev = 0;
        for (int i = 0; i < 32 && n != 0; ++i) {
            rev |= (n & 1) << (31 - i);
            n >>>= 1;
        }
        return rev;
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        // 深度搜索，任意元素都可以选择插入或者不插入
        Arrays.sort(nums);
        ArrayList<List<Integer>> result = new ArrayList<>();
        dfsSubsetsWithDup(nums, 0, new ArrayList<>(), result);
        return result;
    }

    public void dfsSubsetsWithDup(int[] nums, int i, List<Integer> pre, ArrayList<List<Integer>> result1) {
        if (i == nums.length) {
            // 这里要想办法优化一下
            if (!result1.contains(pre)) {
                result1.add(pre);
            }
            return;
        }
        dfsSubsetsWithDup(nums, i + 1, pre, result1);
        pre = new ArrayList<>(pre);
        pre.add(nums[i]);
        dfsSubsetsWithDup(nums, i + 1, pre, result1);

    }

    public void dfsSubsetsWithDup(int[] nums, int i, Map<Integer, Integer> pre, List<Map<Integer, Integer>> result, ArrayList<List<Integer>> result1) {

        if (i == nums.length) {
            if (!result.contains(pre)) {
                result.add(pre);
                ArrayList<Integer> objects = new ArrayList<>();
                for (Map.Entry<Integer, Integer> a :
                        pre.entrySet()) {
                    for (int j = 0; j < a.getValue(); j++) {
                        objects.add(a.getKey());
                    }
                }
                result1.add(objects);
            }
            return;
        }

        dfsSubsetsWithDup(nums, i + 1, pre, result, result1);
        pre = new HashMap(pre);
        pre.merge(nums[i], 1, Integer::sum);
        dfsSubsetsWithDup(nums, i + 1, pre, result, result1);

    }

    /**
     * 面试题 17.21. 直方图的水量
     * https://leetcode-cn.com/problems/volume-of-histogram-lcci/
     * 思路，找坑
     * 坑的特点 1 >2<3 中间低两边高纪为子数组 subInt
     * <p>
     * 水量计算方式
     * maxtrap =  subInt.size()* maxHeige（最高水位 ）  //区间所能容纳最大水量
     * maxtrap -= （maxHeige-minHeige(起始水位) ）* subInt.size() 去掉最高层
     * maxtrap -= subInt.each + maxHeige-minHeige
     * 把所有区间水位相加就是最终容纳的水位
     * <p>
     * 需要有一个纪录水位线的过程，最高水位线只可能出现在左侧或者右侧，如果在右侧，结算一次，否则纪录一下
     */

    public int trap(int[] height) {
        if (height.length < 2) {
            return 0;
        } // 最少要
        int left = 0, right = 1, res = 0; // 起始水位，左右下标
        boolean flag = false; //递减 true 递增
        List<Integer> stack = new ArrayList<>();
        for (int i = 0; i < height.length; i++) {
            if (height[i] > 0) {
                left = i; // 先找打第一个不为0的下标
                break;
            }
        }
        stack.add(left);
        // 选出所有的坑
        for (int i = left; i < height.length-1; i++) {

            if (flag && height[i] > height[i + 1]) {
                stack.add(i);
                flag = !flag;
            } else if (height[i] < height[i + 1]) {
                flag = !flag;
            }
        }
        stack.add(height.length-1);
        System.out.println(stack);
        // 把小坑去掉
        if (stack.size() <2){return res;}
        int prt=1;
        while (prt <(stack.size()-1)){
            int now = stack.get(prt);
            int after = stack.get(prt+1);
            int before = stack.get(prt-1);
            if (height[now]<height[before] && height[now] <height[after] ){
                stack.remove(prt);
                continue;
            }
            prt ++;
        }
        // 计算综合

        for (int i = 0; i < stack.size()-1; i++) {
            res +=settlement(height,i,i+1);
        }

        return res;
    }

    private int settlement(int[] height, int left, int right) {
        int allTrap, minHeight, pre;//总数量，两侧最高水位，当前水位
        minHeight = Math.min(height[left], height[right]);
        allTrap = (right - left + 1) * minHeight;
        for (; left <= right; left++) {
            allTrap -= ((pre = height[left]) > minHeight) ? minHeight : pre;
        }
        System.out.println("当前水位" + allTrap);
        return allTrap;
    }




    /**
     * 1143. 最长公共子序列
     * https://leetcode-cn.com/problems/longest-common-subsequence/
     * */
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            char c1 = text1.charAt(i - 1);
            for (int j = 1; j <= n; j++) {
                char c2 = text2.charAt(j - 1);
                if (c1 == c2) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }



    /**
     *
     * 81. 搜索旋转排序数组 II
     *
     * https://leetcode-cn.com/problems/search-in-rotated-sorted-array-ii/
     * */
    public boolean search(int[] nums, int target) {

        return binSearch(nums,0,nums.length-1,target);
    }
    // 二分法找到旋转点
    private boolean binSearch(int[] nums, int left,int right, int target){
        int mid = (left+right)/2;
        if (right-left <2){ // 相邻或者重合了
            return nums[left] ==target || nums[right]==target;
        }
        if (nums[mid] == target){
            return true;
        }// 判断那边是有序的
        if (nums[left] >nums[mid] || nums[right] > nums[mid]){
            // 右侧有序
            // 如果右侧值 大于 target ，必然落于右区间，否则，落于左区间
            System.out.println("右侧有序");
            if (nums[right]>=target && nums[mid]<=target){
                System.out.println("在右区间");
                return binSearch(nums,mid,right,target);
            }
            System.out.println("在左区间");
            return binSearch(nums,left,mid,target);
        }else if (nums[left] <nums[mid] || nums[right] <nums[mid]){
            //左侧有序
            // 如果左侧值大于 target，必然落于左区间，否则，落于右区间
            System.out.println("左侧有序");
            if (nums[left]<=target && nums[mid]>=target){
                System.out.println("在左区间");
                return binSearch(nums,left,mid,target);
            }
            System.out.println("在右区间");
            return binSearch(nums,mid,right,target);
        }
        // 判断不了 左中右三个值都相等了
        System.out.println("没法判断");
        return binSearch(nums,left+1,right-1,target);
    }



    /**
    * 153. 寻找旋转排序数组中的最小值
     *
     * https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array/
    * */
    public int findMin(int[] nums) {
        // [3,4,1,2]
        int n;
        if ((n=nums.length-1)==0){return nums[0];}
        return beserch(nums,0,n);
    }
    private int beserch(int[] nums,int left,int right){
        int mid = (left+right)/2;
        if (left == right){return nums[left];}
        if (right - left ==1){return Math.min(nums[left],nums[right]);}
        if (nums[mid]>nums[right]){
            //说明最小值必然在右侧
            return beserch(nums,mid,right);
        }
        // 说明最小值要么是mid 要么在左侧
        return beserch(nums,left,mid);

    }

    /**
     * 154. 寻找旋转排序数组中的最小值 II
     * https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array-ii/
     * */
    public int findMin1(int[] nums) {
        return beserch1(nums,0,nums.length-1,nums[0]);
    }

    private int beserch1(int[] nums,int left,int right,int min){
        int mid = (left+right)/2;
        if (right - left <2){return Math.min(Math.min(nums[left],nums[right]),min);}

        /**
         * 能判断最小值在左右
         * if Mid < right    in mid. or left
         * if mid>right    in mid. or right
         * if mid > left  in left. or right  ???
         * if mid < left in mid. or left
         * 不能判断最小值在左右
         * else mid == left ==right  then
         *      left++ right--
         * */
        if (nums[mid]<nums[right] || nums[mid] <nums[left]){
            return beserch1(nums,left,mid,min);
        }
        if (nums[mid]>nums[right] ){
            return beserch1(nums,mid,right,min);
        }
        if (nums[mid] >nums[left]){
            // 可能是left本身或者在右区间
            min = Math.min(nums[left],min);
            return beserch1(nums,mid,right,min);
        }
        return beserch1(nums,left+1,right-1,min);
    }




    public static void main(String[] args) throws ScriptException {

        int[] a = {1, 2, 2};
        new Solution().subsetsWithDup(a);

    }
}
