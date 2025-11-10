/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author lab_services_student
 */
public class ProductSalesTest {
   private ProductSales productSales;

    @BeforeEach
    void setUp() {
        // Data taken from the assignment example:
        // Microphone: 300, 250
        // Speakers: 150, 200
        // Mixing Desk: 700, 600
        int[][] data = {
            {300, 250}, // Microphone
            {150, 200}, // Speakers
            {700, 600}  // Mixing Desk
        };
        productSales = new ProductSales(data);
    }

    @Test
    void GetSalesOverLimit_ReturnsNumberOfSales() {
        // Sales > 500: 700 and 600 -> 2
        assertEquals(2, productSales.GetSalesOverLimit());
    
}
}
