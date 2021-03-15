

/**
 * 用hashmap的思路来解决该问题
 * 705. 设计哈希集合
 * <link> https://leetcode-cn.com/problems/design-hashset /</>
 * */

public class MyHashSet<K>  {

//    static Integer Defaut = 16; //默认大小 写死
//    static double LoadFactor =1.00; //默认水位拉满，写死 也就是说当size== table.length 时扩容，省掉计算水位过程
//    static int size; // table存在部位null 的熟练，既数组实际长度
    transient Node<K>[] table; //当前数组结构

    static int hash(Integer key){
        int h;
        return (h=key) ^(h>>>16);
    }
    // 当前节点
//    @Data
//    @AllArgsConstructor
    static class Node<K>{
         Node<K> next;
        final K val;

        Node(K val){
            this.val = val;
        }

        public Node<K> getNext() {
            return next;
        }

        public K getVal() {
            return val;
        }

    }


    /** Initialize your data structure here. */
    public MyHashSet() {
        //写死
        table = new Node[1<<10];
    }

    public void add(int key) {
        int i;
        if (table[i=(table.length-1)& hash(key)] ==null){
            //当前没有值，直接设置
            table[i] = new Node(key);
            // ++size; 模仿hashmap
        }else {
            //当前有值，判断是不是相等
            // 不相等
            Node p = table[i];
            for (int j = 0; ; j++) {
                if (p.val.equals(key)){
                    break; //已经存在了
                }
                if (p.next == null){
                    p.next = new Node(key);
                    break;
                }
                p = p.next;
            }

        }
    }


    public void remove(int key) {
        int i;
        if (table[i=(table.length-1)& hash(key)] !=null){
            if (table[i].val .equals(key)){
                table[i] = table[i].next;
            }
            else {
                // 迭代链表 //要在链表中删除节点，由于我们是当向链表，所以要纪录两个指针
                Node p = table[i],q=p.next;//上面if不成立，q必然存在
                while (q !=null){//
                    if (q.val .equals(key) ){
                        //找到了删除
                        p.next = q.next;
                    }
                    p=p.next;  q=q.next;
                }
                // 否则就是没有
            }
        }

    }

    /** Returns true if this set contains the specified element */
    public boolean contains(int key) {
        // 同删除逻辑 不需要修改size
        int i;
        if (table[i=(table.length-1)& hash(key)] !=null){
            if (table[i].val .equals(key)){
                return true;
            }
            else {
                // 迭代链表
                Node p = table[i];//上面if不成立
                while (p !=null){
                    if (p.val .equals(key) ){
                        //找到了删除
                        return true;
                    }
                    p=p.next;
                }
                // 否则就是没有
            }
        }
        System.out.println("不存在"+key);
        return false;
    }


}
