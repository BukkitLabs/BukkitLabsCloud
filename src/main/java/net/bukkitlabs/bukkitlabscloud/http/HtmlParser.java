package net.bukkitlabs.bukkitlabscloud.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class HtmlParser{

    private Document document;

    public HtmlParser(File htmlFile) throws IOException{
        document = Jsoup.parse(htmlFile, "UTF-8");
    }

    public Elements getAllClasses() {
        return document.getElementsByTag("class");
    }

    public void updateClass(Element classElement,String newClassName) {
        classElement.attr("name", newClassName);
    }

    public void saveChanges(File outputFile) throws IOException {
        document.outputSettings().charset("UTF-8");
        document.outputSettings().prettyPrint(true);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        Jsoup.connect(outputFile.getAbsolutePath()).data(document.toString());
    }
}
