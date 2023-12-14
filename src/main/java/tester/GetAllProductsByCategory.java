package tester;

import static utils.HibernateUtils.getFactory;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.ProductDaoImpl;
import pojos.Product;
import pojos.ProductCategory;

public class GetAllProductsByCategory {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory(); Scanner sc = new Scanner(System.in)) {
			System.out.println("Enter Product category: ");
			//create dao instance
			ProductDaoImpl dao=new ProductDaoImpl();
			//invoke dao's method
			(dao.getAllProductsWithSpecificCategory(ProductCategory.valueOf(sc.next().toUpperCase())))
				.forEach(System.out::println);
					
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
