package com.example.ale.findme;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by Ale on 31/01/2017.
 */

public class Ob {

    private int id;
    private String photoPath;
    private String tags;

    public Ob (int id,String photoPath, String tags)
    {
        this.id=id;
        this.photoPath=photoPath;
        this.tags=tags;
    }

    public Ob() {

    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * @return the photoPath
     */
    public String getPhotoPath() {
        return photoPath;
    }

    /**
     * @param photoPath the photoPath to set
     */
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    /**
     * @return the tags
     */
    public String getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(String tags) {
        this.tags = tags;
    }


    static void writeList(ArrayList<Ob> list) throws FileNotFoundException
    {
        String Enter = System.getProperty("line.separator");

        PrintWriter out=new PrintWriter(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"/data.txt");


        for (Ob o: list)
        {
            // out.write("id: "+o.getId());
            // out.write(Enter);
            out.write(o.getPhotoPath());
            out.write(Enter);
            out.write(o.getTags());
            out.write(Enter);
            out.write(Enter);
        }


        out.close();
    }
    static ArrayList<Ob> printList() throws FileNotFoundException
    {
        ArrayList<Ob> list= new ArrayList<Ob>();
        File inFile=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"/data.txt");
        if(inFile.exists())
        {
            Scanner in=new Scanner(inFile);
            int i=0;
            while(in.hasNextLine()){
                Ob o=new Ob();
                o.setId(i);
                o.setPhotoPath(in.nextLine());
                o.setTags(in.nextLine());
                String s4=in.nextLine();
                i++;
                list.add(o);
            }

            in.close();


        }
        return list;


    }

    static void addTagMethod(ArrayList<Ob> list, int id, String newTag) throws FileNotFoundException
    {
        for (Ob o: list)
        {
            if (id==o.getId())
            {
                o.setTags(o.getTags()+" @"+newTag);
            }
        }
        writeList(list);


    }

    static ArrayList<String> printTag(Ob o)
    {
        ArrayList<String> tags= new ArrayList<String>();
        int flag=0;
        String s=o.getTags();
        String sub;
        if (s.substring(0,1).equals(" "))//se per qualche motivo il tag inizia con uno spazio allora elimina lo spazio
            s=s.substring(1);
        int iend = s.indexOf(" @");
        while(iend!=-1)
        {
            iend = s.indexOf(" @");
            if (iend != -1)
            {   sub= s.substring(1 , iend);
                s=s.substring(iend+1);
                tags.add(sub);

            }
            flag=1;
        }
        if(flag==1)//per stampare l'ultimo tag
        {
            s=s.substring(1);
            tags.add(s);
        }

        return tags;
    }

    static ArrayList<String> removeTag(ArrayList<String> tag, String el)
    {
        ArrayList<String> listWithoutEl= new ArrayList<String>();

        for(String x:tag)
        {
            if(!x.equals(el))
                listWithoutEl.add(x);
        }

        return listWithoutEl;
    }

    static ArrayList<Ob> removeObject(ArrayList<Ob> l, int id)
    {
        ArrayList<Ob> listWithoutOb= new ArrayList<Ob>();

        for(Ob x: l)
        {
            if(x.getId()!=id)
                listWithoutOb.add(x);
        }

        return listWithoutOb;
    }











}
