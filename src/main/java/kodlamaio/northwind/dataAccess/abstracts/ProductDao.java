package kodlamaio.northwind.dataAccess.abstracts;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kodlamaio.northwind.entities.concretes.Product;
import kodlamaio.northwind.entities.dtos.ProductWithCategoryDto;

public interface ProductDao extends JpaRepository<Product,Integer>{
	Product getByProductName(String productName);
	
	Product getByProductNameAndCategory_CategoryId(String productName, int categoryId);
	
	List<Product> getByProductNameOrCategory_CategoryId(String productName, int categoryId);
	
	@Query("From Product where category.categoryId in (:categories)") //hocaya göre bunun hazır bir fonksiyonu vardı sen kendin özel olarak yazdın fakat hoca söylerse bunu değiştir
	List<Product> getByCategoryIn(List<Integer> categories);
	
	List<Product> getByProductNameContains(String productName);
	
	List<Product> getByProductNameStartsWith(String productName);
	
	@Query("From Product where productName=:productName and category.categoryId=:categoryId") //burada Product büyük çünkü burası veri tabanıyla alakalı değil, Entity'mizle alakalı
	List<Product> getByNameAndCategory(String productName, int categoryId); //jpql özel olarak sorgu yazıcaz

	@Query("Select new kodlamaio.northwind.entities.dtos.ProductWithCategoryDto(p.id, p.productName, c.categoryName) From Category c Inner Join c.products p") //burası jpql özel sorgular yazdık
	List<ProductWithCategoryDto> getProductWithCategoryDetails();
	

}
