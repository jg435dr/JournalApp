package com.example.tobibur.journalapp;

import com.example.tobibur.journalapp.database.JournalModel;

import java.util.ArrayList;
import java.util.List;

public final class TestHelper {
    public static List<JournalModel> getMockData() {
        return new ArrayList<JournalModel>() {{
            add(new JournalModel("Pocitac","","14/03/2018 04:16:15"));
            add(new JournalModel("Kubjek","/storage/emulated/0/Android/data/com.example.tobibur.journalapp/files/Pictures/JPEG_20181107_161358_2103868566.jpg","25/08/2018 12:16:33"));
            add(new JournalModel("Mlieko, Rozky","","13/09/2018 05:15:00"));
            add(new JournalModel("Yogi","","26/03/2018 09:19:23"));
            add(new JournalModel("Pohar","","13/09/2018 01:16:07"));
            add(new JournalModel("Android","/storage/emulated/0/Android/data/com.example.tobibur.journalapp/files/Pictures/JPEG_20181107_161358_2103868566.jpg","16/07/2018 15:25:13"));
            add(new JournalModel("Notebook","/storage/emulated/0/Android/data/com.example.tobibur.journalapp/files/Pictures/JPEG_20181107_161358_2103868566.jpg","23/09/2018 23:42:59"));
        }};
    }
}
