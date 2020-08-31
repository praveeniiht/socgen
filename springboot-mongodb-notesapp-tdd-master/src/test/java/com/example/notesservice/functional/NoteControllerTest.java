package com.example.notesservice.functional;

import static com.example.utils.MasterData.toJson;
import static com.example.utils.TestUtils.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.internal.bytebuddy.pool.TypePool.Empty;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.notesservice.controller.NoteController;
import com.example.notesservice.dto.NotesDto;
import com.example.notesservice.service.NoteService;
import com.example.utils.MasterData;



@WebMvcTest(NoteController.class)
@RunWith(SpringRunner.class)
class NoteControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private NoteService noteService;
	
	
	@Test
	@Ignore
	void testFindAll() throws Exception {
		List<NotesDto> list = new ArrayList<NotesDto>();
		list.add(MasterData.getNotesDto());
		Mockito.when(noteService.findAll()).thenReturn(list);
				RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/noteservice/all")
				.content(MasterData.asJsonString(MasterData.getNotesDto()))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		 List<NotesDto> list1 = Mockito.verify(noteService).findAll(); // argument captors
		System.out.println(result.getResponse().getContentAsString());
		System.out.println(asJsonString(list));
		yakshaAssert(currentTest(), 
				(result.getResponse().getContentAsString().contentEquals(asJsonString(list))? "true" : "false"),	businessTestFile);
		
	}
	@Test
	public void testUpdateStatus() throws Exception{
		NotesDto notesdto = com.example.utils.MasterData.getNotesDto();
		String id = "10001";
		String status="pending";
		notesdto.setId(id);
		notesdto.setStatus(status);
		Mockito.when(noteService.updateStatus(id, status)).thenReturn(notesdto);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/noteservice/update/10001/pending")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(notesdto))
						.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		System.out.println(result.getResponse().getContentAsString().toString());
		yakshaAssert(currentTest(), 
				(result.getResponse().getContentAsString().contentEquals(asJsonString(notesdto))? "true" : "false"),	businessTestFile);
	}
	@Test
	void testUpdateStatusBDD() throws Exception {
		final int count[] = new int[1];
		String id = "10001";
		String status="pending";
		NotesDto notesdto = com.example.utils.MasterData.getNotesDto();
		notesdto.setId(id);
		notesdto.setStatus(status);
		Mockito.when(noteService.updateStatus(id, status)).then(new Answer<NotesDto>() {

			@Override
			public NotesDto answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub
				System.out.println("Called");
				count[0]++;
				return notesdto;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/noteservice/add")
				.content(com.example.utils.MasterData.asJsonString(notesdto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(count[0]);
	
		yakshaAssert(currentTest(),count[0] == 1? true : false,businessTestFile);
				
	}
	@Test
	void testAddNote() throws Exception {
		NotesDto notesdto = com.example.utils.MasterData.getNotesDto();
		Mockito.when(noteService.addNote(notesdto))
		.thenReturn(notesdto);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/noteservice/add")
				.content(com.example.utils.MasterData.asJsonString(notesdto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		System.out.println(notesdto);
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(notesdto))? true : false,
				businessTestFile);
		
	}
	
	@Test
	void testaddNoteBDD() throws Exception {
		final int count[] = new int[1];
		NotesDto notesdto = com.example.utils.MasterData.getNotesDto();
		Mockito.when(noteService.addNote(notesdto)).then(new Answer<NotesDto>() {

			@Override
			public NotesDto answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub
				System.out.println("Called");
				count[0]++;
				return notesdto;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/noteservice/add")
				.content(com.example.utils.MasterData.asJsonString(notesdto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(count[0]);
	
		yakshaAssert(currentTest(),count[0] == 1? true : false,businessTestFile);
				
	}
	
	
	@Test
	@Ignore
	void testDeleteNote() throws Exception{
		NotesDto notesdto = com.example.utils.MasterData.getNotesDto();
		Mockito.when(noteService.addNote(notesdto))
		.thenReturn(notesdto);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/noteserivce/delete/id")
				.content(com.example.utils.MasterData.asJsonString(notesdto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),result.getResponse().getStatus()==200? true : false,businessTestFile);
		
	}

	@Test
	void testdeleteNoteBDD() throws Exception {
		final int count[] = new int[1];
		NotesDto notesdto = com.example.utils.MasterData.getNotesDto();
		String id = notesdto.getId();
		Mockito.when(noteService.deleteNote(id)).then(new Answer<NotesDto>() {

			@Override
			public NotesDto answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub
				System.out.println("Called");
				count[0]++;
				return MasterData.getNotesDto();
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/noteservice/delete/id")
				.content(com.example.utils.MasterData.asJsonString(notesdto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(count[0]);
	
		yakshaAssert(currentTest(),count[0] == 1? true : false,businessTestFile);
				
	}
	
	// Exception
	@Test
	void testaddNoteValidation() throws Exception {
		NotesDto notesdto = com.example.utils.MasterData.getNotesDto();
		notesdto.setAuthor("pr");
		Mockito.when(noteService.addNote(notesdto))
		.thenReturn(notesdto);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/noteservice/add")
				.content(com.example.utils.MasterData.asJsonString(notesdto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		System.out.println(result.getResponse().getStatus());
		yakshaAssert(currentTest(),
				result.getResponse().getStatus() == 400? true : false,
						exceptionTestFile);
				
	}
	
	
	
	@Test
	@Ignore
	void testFindAllByStatus() throws Exception {
		NotesDto notesdto = com.example.utils.MasterData.getNotesDto();
		NotesDto note1 = com.example.utils.MasterData.getNotesDto();
		note1.setStatus("pending");
		NotesDto note2 = com.example.utils.MasterData.getNotesDto();
		note2.setStatus("pending");
		List<NotesDto> list = new ArrayList<NotesDto>();
		list.add(note1);list.add(note2);
		
		Mockito.when(noteService.findAllByStatus("pending")).thenReturn(list);
		
		MvcResult result = mockMvc.perform(get("/noteservice/findByStatus/pending").contentType(MediaType.APPLICATION_JSON)).andReturn();
  		yakshaAssert(currentTest(), result!=null? true : false,businessTestFile);
		
	}
	@Test
	void testfindAllStatusBDD() throws Exception {
		final int count[] = new int[1];
		NotesDto note1 = com.example.utils.MasterData.getNotesDto();
		NotesDto note2 = com.example.utils.MasterData.getNotesDto();
		note1.setStatus("pending");
		note2.setStatus("pending");
		List<NotesDto> list = new ArrayList();
		list.add(note1);
		list.add(note2);
		Mockito.when(noteService.findAllByStatus("pending")).then(new Answer<List<NotesDto>>() {

			@Override
			public List<NotesDto> answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub
				System.out.println("Called");
				count[0]++;
				return list;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/noteservice/delete/id")
				.content(com.example.utils.MasterData.asJsonString(list))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(count[0]);
	
		yakshaAssert(currentTest(),count[0] == 1? true : false,businessTestFile);
				
	}
	@Test
	@Ignore
	void testFindAllByAuthor() throws Exception {
		NotesDto notesdto = com.example.utils.MasterData.getNotesDto();
		NotesDto note1 = com.example.utils.MasterData.getNotesDto();
		note1.setAuthor("praveen");
		NotesDto note2 = com.example.utils.MasterData.getNotesDto();
		note2.setAuthor("praveen");
		List<NotesDto> list = new ArrayList<NotesDto>();
		list.add(note1);list.add(note2);
		
		Mockito.when(noteService.findAllByStatus("praveen")).thenReturn(list);
		
		MvcResult result = mockMvc.perform(get("/noteservice/find/praveen").contentType(MediaType.APPLICATION_JSON)).andReturn();
  		yakshaAssert(currentTest(), result!=null? true : false,businessTestFile);
		
	}
	@Test
	void testfindAllAuthorBDD() throws Exception {
		final int count[] = new int[1];
		NotesDto note1 = com.example.utils.MasterData.getNotesDto();
		NotesDto note2 = com.example.utils.MasterData.getNotesDto();
		note1.setAuthor("praveen");
		note2.setAuthor("praveen");
		List<NotesDto> list = new ArrayList();
		list.add(note1);
		list.add(note2);
		Mockito.when(noteService.findAllByAuthor("praveen")).then(new Answer<List<NotesDto>>() {

			@Override
			public List<NotesDto> answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub
				System.out.println("Called");
				count[0]++;
				return list;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/noteservice/delete/id")
				.content(com.example.utils.MasterData.asJsonString(list))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(count[0]);
	
		yakshaAssert(currentTest(),count[0] == 1? true : false,businessTestFile);
				
	}

}
