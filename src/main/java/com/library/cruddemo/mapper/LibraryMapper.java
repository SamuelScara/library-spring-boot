package com.library.cruddemo.mapper;

import com.library.cruddemo.dto.*;
import com.library.cruddemo.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LibraryMapper {

    // ====== AUTHOR ======
    AuthorDTO toAuthorDTO(Author author);

    Author toAuthorEntity(AuthorDTO dto);

    // ====== BOOK ======
    BookDTO toBookDTO(Book book);

    Book toBookEntity(BookDTO dto);
    
    // ====== DIRECTOR ======
    @Mapping(source = "library.id", target = "libId")
    DirectorDTO toDirectorDTO(Director director);

    @Mapping(target = "library", ignore = true)
    Director toDirectorEntity(DirectorDTO dto);

    // ====== LIB ======
    LibDTO toLibDTO(Lib lib);

    @Mapping(target = "director", ignore = true)
    @Mapping(target = "books", ignore = true)
    Lib toLibEntity(LibDTO dto);

    // ====== EXHIBITION SIMULATION ======
    @Mapping(source = "lib.id", target = "libId")
    @Mapping(target = "bookIds", ignore = true)
    ExhibitionSimulationDTO toExhibitionSimulationDTO(ExhibitionSimulation simulation);

    @Mapping(target = "lib", ignore = true)
    @Mapping(target = "entries", ignore = true)
    ExhibitionSimulation toExhibitionSimulationEntity(ExhibitionSimulationDTO dto);

    // ====== SIMULATION ENTRY ======
    @Mapping(source = "book.title", target = "bookTitle")
    @Mapping(target = "authorName", expression = "java(entry.getBook().getAuthors().isEmpty() ? \"Unknown\" : entry.getBook().getAuthors().get(0).getFirstName() + \" \" + entry.getBook().getAuthors().get(0).getLastName())")
    SimulationEntryDTO toSimulationEntryDTO(SimulationEntry entry);

}
