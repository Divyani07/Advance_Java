package tester;

import static utils.HibernateUtils.getFactory;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.ProductDaoImpl;
import pojos.Product;
import pojos.ProductCategory;

public class GetAllProductsByDate {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory();//tester is calling utils method classwill load and call session's method
				Scanner sc = new Scanner(System.in)) {
			System.out.println("Enter Manufacture date: ");
			//create dao instance
			ProductDaoImpl dao=new ProductDaoImpl();
			//invoke dao's method
			dao.getPartialDetailsByDate(LocalDate.parse(sc.next()))
					.forEach(p->System.out.println(p.getProductId()+" "+p.getName()+" "+p.getPrice()+" "+p.getStock()));
					
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
