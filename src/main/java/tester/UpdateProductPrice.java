package tester;

import static utils.HibernateUtils.getFactory;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.SessionFactory;
import org.hibernate.internal.build.AllowSysOut;

import dao.ProductDaoImpl;
import pojos.Product;
import pojos.ProductCategory;

public class UpdateProductPrice {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory();
				Scanner sc = new Scanner(System.in)) {
			//create dao instance
			ProductDaoImpl dao=new ProductDaoImpl();
			System.out.println("Enter Product id and price offset");
			//invoke dao's method
			System.out.println(dao.updateProductPrice(sc.nextInt(),sc.nextDouble()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
