package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.http.converter.json.GsonFactoryBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.hamcrest.core.StringContains.containsString;

import com.controller.QuoteController;
import com.fasterxml.jackson.core.JsonFactory;
import com.google.gson.Gson;
import com.model.Quote;
import com.service.QuoteService;

class TestController {

	@Mock
	private QuoteService quoteService;

	@InjectMocks
	private QuoteController quoteController;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(quoteController).build();
	}


	@Test
	public void getAllQuotes() throws Exception {

		ArrayList<Quote> allQuotes = new ArrayList<Quote>();
		allQuotes.add(new Quote(1, "da"));
		allQuotes.add(new Quote(2, "bo"));

		when(quoteService.getAllQuotes()).thenReturn((List<Quote>) allQuotes);

		mockMvc.perform(get("/quotes")).andDo(print()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(containsString("da")))
				.andExpect(content().string(containsString("2")));
	}

	@Test
	public void getSingleQuote() throws Exception {
		int id = 1;

		when(quoteService.getQuote(id)).thenReturn(new Quote(id, "si"));

		mockMvc.perform(get("/quotes/{id}",id)).andDo(print()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(containsString("si")))
				.andExpect(content().string(containsString("1"))).andReturn();

	}

	@Test
	public void deleteSingleQuote() throws Exception {
		mockMvc.perform(delete("/quotes/{id}",1)).andExpect(status().isOk());

	}
	

	 @Test
	 public void postQuoteTest() throws Exception {
	 Quote quote = new Quote(6,"baobab");
	 
	 Gson gson = new Gson();
	 String json = gson.toJson(quote);

//	 mockMvc.perform(post("/quotes")
//			 .characterEncoding("UTF-8")
//			 .content(json).acce)
//	 .andDo(print())
////	 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//	 .andExpect(status().isOk());
	 
	 MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
	 MockHttpServletRequestBuilder request = post("/quotes");
	 request.content(json);
	 request.accept(MEDIA_TYPE_JSON_UTF8);
	 request.contentType(MEDIA_TYPE_JSON_UTF8);
	 mockMvc.perform(request)
	     .andDo(print())
	     .andExpect(status().isOk());
	 
	 }

}
