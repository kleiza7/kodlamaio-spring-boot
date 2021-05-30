package kodlamaio.northwind.api.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.northwind.business.abstracts.UserService;
import kodlamaio.northwind.core.entities.User;
import kodlamaio.northwind.core.utilities.results.ErrorDataResult;


@RestController
@RequestMapping("/api/users")
public class UsersController {
	
	private UserService userService;

	@Autowired
	public UsersController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@Valid @RequestBody User user) {
		return ResponseEntity.ok(this.userService.add(user));
	}
	
	//AOP bu operasyonda herhangi bir hata olursa diye
	//bütün operasyonların önüne global exception handler yazarız
	//yani bütün operasyonlara try-catch yazmak yerine 1 tane yazarız
	//ve bütün operasyonları oraya yollarız
	//bu yapıda hazır bir şekilde spring boot ile karşımıza çıkıyor
	
	//genel bir veri tipi olarak object yazıyoruz
	//bütün classların temeli object'tir.primitive tiplerinde(int, bool vs..) bunların base'i objecttir
	
	//sistemde şu türde bir hata olursa bu metodu devreye sok diyoruz, bunun ismi @ExceptionHandler
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) //bu metod default olarak 500 dönsün
	public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exceptions){
		Map<String, String> validationErrors = new HashMap<String, String>();
		for(FieldError fieldError : exceptions.getBindingResult().getFieldErrors()) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		ErrorDataResult<Object> errors = new ErrorDataResult<Object>(validationErrors, "Doğrulama hataları");
		
		return errors;
	}

	
}
