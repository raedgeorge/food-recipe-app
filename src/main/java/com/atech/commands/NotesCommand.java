package com.atech.commands;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class NotesCommand {

    private String id;
    private String notes;

    public String getId() {
        return this.id;
    }

    public String getNotes() {
        return this.notes;
    }
}
