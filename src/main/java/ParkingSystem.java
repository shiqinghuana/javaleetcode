
/**
 * <link> https://leetcode-cn.com/problems/design-parking-system/
 *1603. 设计停车系统
 *
 * */

public class ParkingSystem {


    private int[] a;

    public ParkingSystem(int big, int medium, int small) {
        this.a = new int[]{0, big, medium, small};
    }

    public boolean addCar(int carType) {
        return a[carType]-- > 0;
    }
}
