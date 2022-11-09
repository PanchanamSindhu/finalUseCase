package com.cog.user.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
	
		private int id;
		private String logo;
		private String title;
		@Enumerated(EnumType.STRING)
		private BookCategory category;
		private BigDecimal price;
		private int authorId;
		private String authorUserName;
		private String authorName;
		private String publisher;
		private String content;
		private Boolean active;
		private LocalDate publishedDate;

}
