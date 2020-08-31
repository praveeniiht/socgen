package com.example.notesservice.utils;

import java.util.ArrayList;
import java.util.List;

import com.example.notesservice.dto.NotesDto;
import com.example.notesservice.model.Note;


public class NotesUtilities {
	public static List<NotesDto> convertToNotesDtoList(List<Note> list){
		List<NotesDto> dtolist = new ArrayList<NotesDto>();
		for(Note note : list) 
			dtolist.add(convertToNoteDto(note));
		return dtolist;
	}
	
	public static Note convertToNote(NotesDto notedto) {
		Note note = new Note();
		note.setId(notedto.getId());
		note.setAuthor(notedto.getAuthor());
		note.setTitle(notedto.getTitle());
		note.setDescription(notedto.getDescription());
		note.setStatus(notedto.getStatus());
		return note;
	}
	
	public static NotesDto convertToNoteDto(Note note) {
		NotesDto notedto = new NotesDto();
		notedto.setId(note.getId());
		notedto.setAuthor(note.getAuthor());
		notedto.setDescription(note.getDescription());
		notedto.setTitle(note.getTitle());
		notedto.setStatus(note.getStatus());
		return notedto;
	}
	public static NotesDto getNotesDto() {

		NotesDto notesdto = new NotesDto();
		notesdto.setId("1");
		notesdto.setAuthor("Praveen");
		notesdto.setTitle("Jenkins");
		notesdto.setDescription("This is the best CI/CD tool");
		notesdto.setStatus("Done");
		
		return notesdto;
	}
	
	
}
	