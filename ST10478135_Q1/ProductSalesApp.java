/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salesreport;

/**
 *
 * @author lab_services_student
 */
public class ProductSalesApp {
     public static void main(String[] args) {

        // Product sales data for 2 years × 3 quarters
        int[][] productSales = {
            {300, 150, 700},  // Year 1: Q1, Q2, Q3
            {250, 200, 600}   // Year 2: Q1, Q2, Q3
        };

        // Create instance of ProductSales
        ProductSales ps = new ProductSales();

        // Display report
        System.out.println("PRODUCT SALES REPORT");
        System.out.println("----------------------");
        System.out.println("Total Sales: " + ps.TotalSales(productSales));
        System.out.printf("Average Sales: %.2f%n", ps.AverageSales(productSales));
        System.out.println("Maximum Sales: " + ps.MaxSales(productSales));
        System.out.println("Minimum Sales: " + ps.MinSales(productSales));
    }
}


  

