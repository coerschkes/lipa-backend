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

import java.util.Optional;

@ApiAdapter
@RequiredArgsConstructor
public class NoteApiAdapter implements NotesApi {
    private final NoteRepository noteRepository;
    private final NoteApiMapper noteApiMapper;

    @Override
    public Mono<ResponseEntity<Flux<ApiNote>>> findAllNotes(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(Flux.fromIterable(noteRepository.findAll()).map(noteApiMapper::toApiNote)));
    }

    @Override
    public Mono<ResponseEntity<ApiNote>> findNote(Long id, ServerWebExchange exchange) {
        return Mono.create(sink -> {
            final Optional<Note> noteOptional = noteRepository.findById(id);
            noteOptional.ifPresentOrElse(note -> sink.success(ResponseEntity.ok(noteApiMapper.toApiNote(note))),
                    () -> sink.error(new EntityNotFoundException("Entity with id '" + id + "' not found.")));
        });
    }

    @Override
    public Mono<ResponseEntity<ApiNote>> createNote(Mono<ApiNote> apiNote, ServerWebExchange exchange) {
        return NotesApi.super.createNote(apiNote, exchange);
    }

    @Override
    public Mono<ResponseEntity<Void>> updateNote(Long id, Mono<ApiNote> apiNote, ServerWebExchange exchange) {
        return NotesApi.super.updateNote(id, apiNote, exchange);
    }
}
