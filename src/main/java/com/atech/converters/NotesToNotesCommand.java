package com.atech.converters;

import com.atech.commands.NotesCommand;
import com.atech.entity.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Nullable
    @Synchronized
    @Override
    public NotesCommand convert(Notes source) {

        if (source == null) {
            return null;
        }

        final NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(String.valueOf(source.getId()));
        notesCommand.setNotes(source.getNotes());
        return notesCommand;
    }
}
