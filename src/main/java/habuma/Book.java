package habuma;

import org.springframework.data.annotation.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force=true, access=AccessLevel.PRIVATE)
public class Book {
	
	@Id
	private Long id;
	private String isbn;
	private String title;
	private String author;
	
}
