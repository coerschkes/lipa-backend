package com.coerschkes.lipabackend.adapter.mapper;

import com.coerschkes.lipabackend.model.ApiNote;
import com.coerschkes.lipabackend.model.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteApiMapper {
    public ApiNote toApiNote(final Note note) {
        return new ApiNote()
                .id(note.getId())
                .title(note.getTitle())
                .category(note.getCategory())
                .text(note.getText());
    }

    public Note toNote(final ApiNote apiNote) {
        return new Note(apiNote.getTitle(), apiNote.getCategory(), apiNote.getCategory());
    }
}
