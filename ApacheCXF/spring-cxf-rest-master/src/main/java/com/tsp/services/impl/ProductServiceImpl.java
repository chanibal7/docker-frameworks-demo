package com.tsp.services.impl;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.tsp.dao.ProductDao;
import com.tsp.model.Product;
import com.tsp.services.ProductService;

public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductDao prodcutDao;
	
public Product getProduct(String id) {
	System.out.println("product id="+id);
	test("http://localhost:8081/EmployerInfoService/EmployeeServlet");
	System.out.println("<<<<---------------------->>>>");
	return prodcutDao.getProduct(id);
}
	
	
	public Product saveProduct(Product product) {
		return prodcutDao.saveProduct(product);
		
	}
	
	public static String test(String address) {
		try {
			Thread.sleep(12000);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			URL serverAddress = new URL(address);
			HttpURLConnection connection = null;
			connection = (HttpURLConnection) serverAddress.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setReadTimeout(10000);
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			connection.disconnect();
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Success";
	}

}
