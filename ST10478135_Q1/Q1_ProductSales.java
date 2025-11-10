/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salesreport;

/**
 *
 * @author lab_services_student       //ST10478135
 */
public class Q1_ProductSales {
   // Calculate total sales across all years and quarters
   
    public int TotalSales(int[][] productsales) {
        int total = 0;
        for (int[] year : productsales) {
            for (int quarter : year) {
                total += quarter;
            }
        }
        return total;
    }

    // Calculate average sales
   
    public double AverageSales(int[][] productsales) {
        int total = TotalSales(productsales);
        int count = 0;
        for (int[] year : productsales) {
            count += year.length;
        }
        return (double) total / count;
    }

    // Find maximum sales value
  
    public int MaxSales(int[][] productsales) {
        int max = productsales[0][0];
        for (int[] year : productsales) {
            for (int quarter : year) {
                if (quarter > max) {
                    max = quarter;
                }
            }
        }
        return max;
    }

    // Find minimum sales value
   
    public int MinSales(int[][] productsales) {
        int min = productsales[0][0];
        for (int[] year : productsales) {
            for (int quarter : year) {
                if (quarter < min) {
                    min = quarter;
                }
            }
        }
        return min;
    }
}

  

