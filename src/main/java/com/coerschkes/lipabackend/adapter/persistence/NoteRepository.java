package com.coerschkes.lipabackend.adapter.persistence;

import com.coerschkes.lipabackend.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
}