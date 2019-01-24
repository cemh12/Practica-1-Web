package edu.pucmm.pw;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;
import static spark.Spark.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Digite la url:");
        Scanner scanner = new Scanner(System.in);
        String url = scanner.nextLine();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Titulo: "+doc.title());
        int lineas = doc.html().split("\n").length;
        System.out.println(lineas + " Lineas");
        Elements parrafos = doc.select("p");
        int parrafo = parrafos.size();
        System.out.println(parrafo+" Parrafos");
        Elements imagenes = doc.select("img");
        int imagen = imagenes.size();
        System.out.println(imagen+" Imagenes");
        Elements FormsPost = doc.select("form[method=POST]");
        int FormPost = FormsPost.size();
        System.out.println(FormPost+" Forms Post");
        Elements FormsGet = doc.select("form[method=GET]");
        int FormGet = FormsGet.size();
        System.out.println(FormGet+" Forms Get");

        for (Element post : FormsPost){
            System.out.println("Nombre del form: "+ post.id());
            System.out.println("Metodo = Post");
            Elements Input = doc.select("Input");
            System.out.println(Input.eachAttr("type"));

            Connection c = ((FormElement)post).submit();
            ((Connection) c).requestBody("('asignatura':'Practica 1')");
            c.header("Matricula", "2012-0127");
            Connection.Response r = c.execute();
            System.out.println("Estado del request: "+r.statusCode()+" "+ r.statusMessage());
            System.out.println("Respuesta: ");
            System.out.println(r.body());

        }
        for (Element get : FormsGet){
            System.out.println("Nombre del form: "+ get.id());
            System.out.println("Metodo = Get");
            Elements Input = doc.select("Input");
            System.out.println(Input.eachAttr("type"));
        }

    }
}
