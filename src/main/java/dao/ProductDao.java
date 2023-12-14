package dao;

import java.time.LocalDate;
import java.util.List;

import pojos.Product;
import pojos.ProductCategory;

public interface ProductDao {
//add a method to insert new product details
	String addNewProduct(Product product);
	//add a method to insert new product details:using getCurrentSession()
	String addNewProductWithGetCurrentSession(Product product);
	//add a method to get all products
	List<Product> getAllProducts();
	//add a method to get all products from a specific product category
	List<Product> getAllProductsWithSpecificCategory(ProductCategory prodCat);
	//Get Product id , name , price n stock for all the products manufactured after specific date.i/p : date
	List<Product> getPartialDetailsByDate(LocalDate date);
	//update product price
	String updateProductPrice(Integer productId,double priceOffset);
}
