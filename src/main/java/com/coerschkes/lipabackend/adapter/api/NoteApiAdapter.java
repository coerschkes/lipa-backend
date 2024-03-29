package com.coerschkes.lipabackend.adapter.api;

import com.coerschkes.lipabackend.adapter.mapper.NoteApiMapper;
import com.coerschkes.lipabackend.adapter.persistence.NoteRepository;
import com.coerschkes.lipabackend.api.NotesApi;
import com.coerschkes.lipabackend.model.ApiNote;
import com.coerschkes.lipabackend.model.Note;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@ApiAdapter
@RequiredArgsConstructor
public class NoteApiAdapter implements NotesApi {
    private final NoteRepository noteRepository;
    private final NoteApiMapper noteApiMapper;

    @Override
    public Mono<ResponseEntity<Flux<ApiNote>>> findAllNotes(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(Flux.fromStream(noteRepository
                .findAll()
                .stream()
                .map(noteApiMapper::toApiNote))));
    }

    @Override
    public Mono<ResponseEntity<ApiNote>> findNote(Long id, ServerWebExchange exchange) {
        return noteRepository
                .findById(id)
                .map(noteApiMapper::toApiNote)
                .map(ResponseEntity::ok)
                .map(Mono::just)
                .orElseThrow(() -> createEntityNotFoundException(id));
    }

    @Override
    public Mono<ResponseEntity<Void>> createNote(Mono<ApiNote> apiNote, ServerWebExchange exchange) {
        return apiNote
                .map(noteApiMapper::toNote)
                .map(noteRepository::save)
                .map(note -> ResponseEntity.created(URI.create(createResourceUrl(note.getId()))).build());
    }

    //todo test if working
    @Override
    public Mono<ResponseEntity<Void>> updateNote(Long id, Mono<ApiNote> apiNote, ServerWebExchange exchange) {
        final Note note = noteRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return apiNote
                .map(noteApiMapper::toNote)
                .filter(newNote -> !newNote.equals(note))
                .map(note::updateWith)
                .map(noteRepository::save)
                .map(updatedNote -> ResponseEntity.ok().build());
    }

    private static EntityNotFoundException createEntityNotFoundException(Long id) {
        return new EntityNotFoundException("Entity with id '" + id + "' not found.");
    }

    private String createResourceUrl(final Long id) {
        if (id == null) {
            throw new EntityNotFoundException("ID of note cannot be null");
        }
        return "/api/v1/notes/" + id;
    }
}
