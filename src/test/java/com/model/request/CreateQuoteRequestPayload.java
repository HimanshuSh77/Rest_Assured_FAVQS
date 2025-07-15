package com.model.request;

public class CreateQuoteRequestPayload {
	
	Quote quote;

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}
	
	public CreateQuoteRequestPayload(String author, String body) {
		this.quote = new Quote(author, body);
	}
	
	public static class Quote {
		
		String author;
		String body;
		
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
		public Quote(String author, String body) {
			
			this.author = author;
			this.body = body;
		}
		
		
		
	}

}
