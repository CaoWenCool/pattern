package com.dmeo.composite.file;

/**
 * @author: Maniac Wen
 * @create: 2020/5/22
 * @update: 8:35
 * @version: V1.0
 * @detail:
 **/
public class Test {
    public static void main(String[] args) {
        File java = new File("java.exe");

        File idea  = new File("IDEA.exe");
        File eclipse = new File("eclipse.exe");
        Folder editor = new Folder("编辑器",2);
        editor.add(idea);
        editor.add(eclipse);

        File word  = new File("Word.exe");
        File excel = new File("Excel.exe");
        File ppt = new File("Ppt.exe");
        Folder office = new Folder("办公软件",3);
        office.add(word);
        office.add(excel);
        office.add(ppt);

        Folder root = new Folder("根目录",1);
        root.add(editor);
        root.add(office);
        root.add(java);

        root.show();
        System.out.println("----------------------------------------");
        root.list();
    }
}
