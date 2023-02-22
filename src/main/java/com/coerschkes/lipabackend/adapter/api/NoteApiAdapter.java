package com.coerschkes.lipabackend.adapter.api;

import com.coerschkes.lipabackend.adapter.mapper.NoteApiMapper;
import com.coerschkes.lipabackend.adapter.persistence.NoteRepository;
import com.coerschkes.lipabackend.api.NotesApi;
import com.coerschkes.lipabackend.model.ApiNote;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<ResponseEntity<ApiNote>> createNote(Mono<ApiNote> apiNote, ServerWebExchange exchange) {
        return NotesApi.super.createNote(apiNote, exchange);
    }

    @Override
    public Mono<ResponseEntity<Void>> updateNote(Long id, Mono<ApiNote> apiNote, ServerWebExchange exchange) {
        return NotesApi.super.updateNote(id, apiNote, exchange);
    }

    private static EntityNotFoundException createEntityNotFoundException(Long id) {
        return new EntityNotFoundException("Entity with id '" + id + "' not found.");
    }
}
