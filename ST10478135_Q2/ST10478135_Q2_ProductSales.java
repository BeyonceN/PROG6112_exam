/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author lab_services_student
 */
public class ProductSales {
   private int[][] productSales;
    public static final int SALES_LIMIT = 500; // sales limit used for over/under counts

    /**
     * Constructor expects a 2D int array. Each inner array is all years' sales for one product.
     * @param productSales 2D int array of sales values (rows=products, cols=years)
     */
    public ProductSales(int[][] productSales) {
        // defensive copy to avoid external modification (simple approach)
        if (productSales == null) {
            this.productSales = new int[0][0];
        } else {
            this.productSales = new int[productSales.length][];
            for (int i = 0; i < productSales.length; i++) {
                this.productSales[i] = productSales[i] == null ? new int[0] : productSales[i].clone();
            }
        }
    }

    
    public int[][] GetProductSales() {
        // return copy to avoid exposing internal array for modification
        int[][] copy = new int[productSales.length][];
        for (int i = 0; i < productSales.length; i++) {
            copy[i] = productSales[i].clone();
        }
        return copy;
    }

    
    public int GetTotalSales() {
        int total = 0;
        for (int[] product : productSales) {
            for (int val : product) {
                total += val;
            }
        }
        return total;
    }

   
    public int GetSalesOverLimit() {
        int count = 0;
        for (int[] product : productSales) {
            for (int val : product) {
                if (val > SALES_LIMIT) count++;
            }
        }
        return count;
    }

   
    public int GetSalesUnderLimit() {
        int count = 0;
        for (int[] product : productSales) {
            for (int val : product) {
                if (val <= SALES_LIMIT) count++;
            }
        }
        return count;
    }

   
    public int GetProductsProcessed() {
        return productSales.length;
    }


    public double GetAverageSales() {
        int total = GetTotalSales();
        int count = 0;
        for (int[] product : productSales) {
            count += product.length;
        }
        if (count == 0) return 0.0;
        return (double) total / count;
    }

    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductSales)) return false;
        ProductSales that = (ProductSales) o;
        // shallow comparison for equality (not required but handy for tests)
        if (this.productSales.length != that.productSales.length) return false;
        for (int i = 0; i < this.productSales.length; i++) {
            if (!java.util.Arrays.equals(this.productSales[i], that.productSales[i])) return false;
        }
        return true;
    }

    
    public int hashCode() {
        int result = 17;
        for (int[] row : productSales) {
            result = 31 * result + java.util.Arrays.hashCode(row);
        }
        return result;
    }
    
}
