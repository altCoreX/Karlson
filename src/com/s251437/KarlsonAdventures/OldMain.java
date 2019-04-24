package com.s251437.KarlsonAdventures;

import com.s251437.KarlsonAdventures.control.CollectionManager;
import com.s251437.KarlsonAdventures.control.CommandHandler;

import java.io.File;

public class OldMain {
    public static void main(String[] args){
        CommandHandler handler = new CommandHandler(new CollectionManager());
        handler.control();

    }
}
