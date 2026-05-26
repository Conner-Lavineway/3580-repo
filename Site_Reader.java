package com.sitereader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Conner Lavineway March 2022
 *
 * This program will scrape The Roling Stones "500 greatest albums of all time" from 2020
 * and return a ordered text file to be read by other programs
 */
public class Site_Reader
{
    final int MAX_PAGES = 10;
    public void run() throws IOException
    {
        String mylink = "https://www.albumoftheyear.org/list/1500-rolling-stones-500-greatest-albums-of-all-time-2020/";
        File outputFile = new File("Output.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, StandardCharsets.UTF_8)); 
        /* 
            set writer to use UTF 8 Character encoding instead of ASCII
            ASCII wont properly write all text to file, spotify cant read ASCII unkown character
        */
        for(int i = 1; i <= MAX_PAGES; i++)
        {
            writer.write(checkSite(mylink, i));
        }
        writer.close();
    }

    private String checkSite(String site, int page) throws IOException
    {
        String mylink = site + page;

        Connection connection = Jsoup.connect(mylink); //connect to page
        connection.userAgent("Mozilla/5.0"); 
        /*
            without setting user agent the site will flag as a bot
            and deny access 
        */

        Document doc = connection.get(); //get page as a document
        
        Elements text = doc.select("h2[class=albumListTitle]"); //grab all elements with the h2 tag and the class albumListTitle

        Iterator<org.jsoup.nodes.Element> textChecker = text.iterator(); //iteratate through each element
        
        StringBuilder out = new StringBuilder(); //more effecient fro making large strings
        while(textChecker.hasNext())
        {
            String lineEditor = textChecker.next().text();
            out.append(lineEditor.substring(lineEditor.indexOf(" ") + 1));
            /*
                get first space, then move to next character
                add to buffered writer
            */

            if(textChecker.hasNext())
            {
                out.append("\n");
                //add newline fro easy reading
            }
            
        }
        if(page < MAX_PAGES)
        {
            out.append("\n");
            //add newline if this inst the last page
        }
        
       return out.toString();
    }

    //textChecker.next().text()  linkChecker.next().select("a[href][data-track-action=\"Spotify\"]").attr("href")
}
