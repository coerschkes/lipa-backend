package com.coerschkes.lipabackend;

import com.coerschkes.lipabackend.api.TodosApi;
import com.coerschkes.lipabackend.model.Todo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class TodosImpl implements TodosApi {

    @Override
    public Mono<ResponseEntity<Flux<Todo>>> getAllTodos(ServerWebExchange exchange) {
        return Mono.empty();
    }
}
