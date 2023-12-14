package dao;

import pojos.Product;
import pojos.ProductCategory;

import org.hibernate.*;
import static utils.HibernateUtils.getFactory;

import java.time.LocalDate;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

	@Override
	public String addNewProduct(Product product) {
		String mesg="Adding product failed!!!!";
		// 1. get Session from SessionFactory (SF)
		Session session = getFactory().openSession();
		// 2. Begin a Transaction
		Transaction tx = session.beginTransaction();
		// 3 . try-catch -finally
		try {
			session.save(product);
			// success : commit tx
			tx.commit();
			mesg="product added with ID "+product.getProductId();
		} catch (RuntimeException e) {
			// error : rollback tx
			if (tx != null)
				tx.rollback();
			// re throw the SAME exc to the caller : to inform about the error
			throw e;
		} finally {
			if (session != null)
				session.close();// L1 cache is destroyed , 
			//pooled out db cn rets back to the pool
		}
		return mesg;
	}

	@Override
	public String addNewProductWithGetCurrentSession(Product product) {
		String mesg="Adding product failed!!!!";
		//1. get Session from SessionFactory 
		Session session = getFactory().getCurrentSession();
		//2.begin a transaction
		Transaction tx = session.beginTransaction();
		//3.try-catch 
		try {
				session.save(product);
				//success : commit tx
				tx.commit();
				mesg="product added with ID "+product.getProductId();
			}catch(RuntimeException e) {
				//error : rollback tx
				if(tx != null)
					tx.rollback();
				// re throw the SAME exc to the caller : to inform about the error
				throw e;
			}
	
		return mesg;
	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> list = null;
		String jpql = "select p from Product p";
		// 1. get session from SF
				Session session=getFactory().getCurrentSession();
				//2. begin a tx
				Transaction tx=session.beginTransaction();
				try {
					list = session.createQuery(jpql, Product.class)
							.getResultList();
					tx.commit();
				} catch (RuntimeException e) {
					if(tx != null)
						tx.rollback();
					throw e;
				}
			
		return list;
	}

	@Override
	public List<Product> getAllProductsWithSpecificCategory(ProductCategory prodCategory) {
		List<Product> products = null;
		String jpql = "select p from Product p where p.category= :prodCat";
		// 1. get session from SF
		Session session=getFactory().getCurrentSession();
		//2. begin a tx
		Transaction tx=session.beginTransaction();
		try {
			products = session.createQuery(jpql, Product.class)
					.setParameter("prodCat", prodCategory)
					.getResultList();
			tx.commit();
		} catch (RuntimeException e) {
			if(tx != null)
				tx.rollback();
			throw e;
		}
	
		return products;
	}

	@Override
	public List<Product> getPartialDetailsByDate(LocalDate date) {
		List<Product> products = null;
		String jpql = "select new pojos.Product(productId,name,price,stock)from "
				+ "Product p where p.manufactureDate>:mfgDate";
		// 1. get session from SF
		Session session=getFactory().getCurrentSession();
		//2. begin a tx
		Transaction tx=session.beginTransaction();
		try {
			products=session.createQuery(jpql,Product.class)
					.setParameter("mfgDate",date)
					.getResultList();
			tx.commit();
		} catch (RuntimeException e) {
			if(tx != null)
				tx.rollback();
			throw e;
		}
	
		return products;
	}

	@Override
	public String updateProductPrice(Integer productId, double priceOffset) {
		Product product=null;
		String mesg="Product updation failed..";
		// 1. get session from SF
		Session session=getFactory().getCurrentSession();
		//2. begin a tx
		Transaction tx=session.beginTransaction();
		try {
			product=session.get(Product.class, productId);//select query fired
			if(product != null) {
				//product : persistent
				//simply modify state of the persistent pojo
				product.setPrice(product.getPrice()+priceOffset);
				mesg="product price updated...";
			}
			tx.commit();//session.flush --> auto dirty checking --> updated entity-->
			//DML : update query fired automatically--> session.close() -->L1 cache is destroyed and pooled
			//out db connection returns to the pool
		} catch (RuntimeException e) {
			if(tx != null)
				tx.rollback();
			throw e;
		}
		//product : detached from L1 cache
		//modify state of the persistent pojo
		product.setPrice(product.getPrice() + priceOffset);
		return mesg;
	}	

}










